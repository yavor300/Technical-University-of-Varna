package bg.tuvarna.sit.cloud.core.aws.eks.step;

import bg.tuvarna.sit.cloud.core.aws.eks.EksClusterOutput;
import bg.tuvarna.sit.cloud.core.aws.eks.client.EksSafeClient;
import bg.tuvarna.sit.cloud.core.aws.eks.config.EksClusterConfig;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterAddon;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterAddons;
import bg.tuvarna.sit.cloud.core.aws.eks.step.base.EksClusterProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionOrder;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import lombok.extern.slf4j.Slf4j;

import org.apache.hc.core5.http.HttpStatus;

import software.amazon.awssdk.services.eks.model.EksException;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@ProvisionOrder(4)
@Singleton
// TODO [Enhancement] Each add on different step
public class EksClusterAddonStep extends EksClusterProvisionStep {

  private final StepResult<EksClusterOutput> metadata;

  @Inject
  protected EksClusterAddonStep(EksSafeClient eks, EksClusterConfig config, StepResult<EksClusterOutput> metadata) {
    super(eks, config);
    this.metadata = metadata;
  }

  @Override
  public StepResult<EksClusterOutput> apply() {

    String step = this.getClass().getName();
    String clusterName = (String) metadata.getOutputs().get(EksClusterOutput.NAME);

    List<EksClusterConfig.EksAddonConfig> configuredAddons = config.getAddons() != null
        ? config.getAddons()
        : List.of();

    EksClusterAddons currentAddons = eks.getAddons(clusterName);
    Map<String, EksClusterAddon> currentMap = currentAddons.getAddons().stream()
        .collect(Collectors.toMap(EksClusterAddon::getName, a -> a));

    EksClusterAddons result = new EksClusterAddons();

    for (EksClusterConfig.EksAddonConfig configured : configuredAddons) {

      String name = configured.getName();
      String version = configured.getVersion();
      EksClusterAddon current = currentMap.get(name);

      if (current == null) {
        eks.createAddon(clusterName, name, version);
        log.info("Created addon '{}' (version '{}') in cluster '{}'", name, version, clusterName);
      } else if (!Objects.equals(current.getVersion(), version)) {
        eks.updateAddon(clusterName, name, version);
        log.info("Updated addon '{}' to version '{}' in cluster '{}'", name, version, clusterName);
      } else {
        log.info("Addon '{}' already present with version '{}' — skipping.", name, version);
      }

      result.getAddons().add(new EksClusterAddon(name, version));
    }

    eks.waitUntilAddonsActive(clusterName, result, Duration.ofMinutes(5));

    return StepResult.<EksClusterOutput>builder()
        .stepName(step)
        .put(EksClusterOutput.ADDONS, result)
        .build();
  }



  @Override
  public StepResult<EksClusterOutput> generateDesiredState() {

    String step = this.getClass().getName();

    List<EksClusterConfig.EksAddonConfig> configuredAddons =
        config.getAddons() != null ? config.getAddons() : List.of();

    if (configuredAddons.isEmpty()) {
      log.info("No cluster addons defined in configuration — skipping desired state generation.");
      return StepResult.<EksClusterOutput>builder().stepName(step).build();
    }

    List<EksClusterAddon> desired = configuredAddons.stream()
        .map(addon -> new EksClusterAddon(addon.getName(), addon.getVersion()))
        .toList();

    return StepResult.<EksClusterOutput>builder()
        .stepName(step)
        .put(EksClusterOutput.ADDONS, new EksClusterAddons(desired))
        .build();
  }

  @Override
  public StepResult<EksClusterOutput> getCurrentState() {

    String step = this.getClass().getName();
    String clusterName = (String) metadata.getOutputs().get(EksClusterOutput.NAME);

    try {
      EksClusterAddons currentAddons = eks.getAddons(clusterName);
      log.info("Fetched current addons for EKS cluster '{}': {}", clusterName, currentAddons);

      return StepResult.<EksClusterOutput>builder()
          .stepName(step)
          .put(EksClusterOutput.ADDONS, currentAddons)
          .build();

    } catch (CloudResourceStepException e) {
      if (e.getCause() instanceof EksException eksException) {
        if (eksException.statusCode() == HttpStatus.SC_NOT_FOUND) {
          log.info("EKS cluster '{}' not found when retrieving addons.", clusterName);
          return StepResult.<EksClusterOutput>builder().stepName(step).build();
        }
      }

      throw e;
    }
  }

  @Override
  public StepResult<EksClusterOutput> destroy(boolean enforcePreventDestroy) {

    String step = this.getClass().getName();
    String clusterName = (String) metadata.getOutputs().get(EksClusterOutput.NAME);

    EksClusterAddons currentAddons = eks.getAddons(clusterName);
    for (EksClusterAddon addon : currentAddons.getAddons()) {
      eks.removeAddon(clusterName, addon.getName());
    }

    eks.waitUntilAddonsDeleted(clusterName, currentAddons, Duration.ofMinutes(5));

    log.info("Removed {} addon(s) from cluster '{}'", currentAddons.getAddons().size(), clusterName);

    return StepResult.<EksClusterOutput>builder()
        .stepName(step)
        .build();
  }

  @Override
  public StepResult<EksClusterOutput> revert(StepResult<EksClusterOutput> previous) {

    String step = this.getClass().getName();
    String clusterName = (String) metadata.getOutputs().get(EksClusterOutput.NAME);
    EksClusterAddons revert = (EksClusterAddons) previous.getOutputs().get(EksClusterOutput.ADDONS);

    EksClusterAddons currentAddons = eks.getAddons(clusterName);
    Set<EksClusterAddon> currentSet = new HashSet<>(currentAddons.getAddons());
    Set<EksClusterAddon> previousSet = revert != null ? new HashSet<>(revert.getAddons()) : Set.of();

    Set<String> currentNames = currentSet.stream()
        .map(EksClusterAddon::getName)
        .collect(Collectors.toSet());

    Set<String> previousNames = previousSet.stream()
        .map(EksClusterAddon::getName)
        .collect(Collectors.toSet());

    // Remove addons that are present now but not part of the previous state
    EksClusterAddons deleted = new EksClusterAddons();
    for (String addon : currentNames) {
      if (!previousNames.contains(addon)) {
        eks.removeAddon(clusterName, addon);
        log.info("Removed addon '{}' from cluster '{}'", addon, clusterName);
        deleted.getAddons().add(currentSet.stream()
            .filter(eksClusterAddon -> eksClusterAddon.getName().equals(addon))
            .findFirst().get());
      }
    }

    eks.waitUntilAddonsDeleted(clusterName, deleted, Duration.ofMinutes(5));

    // Add or update addons that are missing or have different versions
    EksClusterAddons updated = new EksClusterAddons();
    for (EksClusterAddon addon : previousSet) {
      Optional<EksClusterAddon> match = currentSet.stream()
          .filter(current -> current.getName().equals(addon.getName()))
          .findFirst();

      if (match.isEmpty()) {
        eks.createAddon(clusterName, addon.getName(), addon.getVersion());
        log.info("Restored addon '{}' (version {}) in cluster '{}'", addon.getName(), addon.getVersion(), clusterName);
        updated.getAddons().add(addon);
      } else if (!addon.equals(match.get())) {
        eks.updateAddon(clusterName, addon.getName(), addon.getVersion());
        log.info("Updated addon '{}' to version '{}' in cluster '{}'", addon.getName(), addon.getVersion(), clusterName);
        updated.getAddons().add(addon);
      }
    }
    eks.waitUntilAddonsActive(clusterName, updated, Duration.ofMinutes(5));

    EksClusterAddons finalState = eks.getAddons(clusterName);
    return StepResult.<EksClusterOutput>builder()
        .stepName(step)
        .put(EksClusterOutput.ADDONS, finalState)
        .build();
  }

}
