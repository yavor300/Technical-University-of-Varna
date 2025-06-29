package bg.tuvarna.sit.cloud.core.aws.eks.step;

import bg.tuvarna.sit.cloud.core.aws.eks.EksClusterOutput;
import bg.tuvarna.sit.cloud.core.aws.eks.client.EksSafeClient;
import bg.tuvarna.sit.cloud.core.aws.eks.config.EksClusterConfig;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterNodeGroup;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterNodeGroups;
import bg.tuvarna.sit.cloud.core.aws.eks.model.ProvisionedLabels;
import bg.tuvarna.sit.cloud.core.aws.eks.model.SubnetIdSet;
import bg.tuvarna.sit.cloud.core.aws.eks.step.base.EksClusterProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.common.model.ProvisionedTags;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionOrder;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import lombok.extern.slf4j.Slf4j;

import org.apache.hc.core5.http.HttpStatus;

import software.amazon.awssdk.services.eks.model.EksException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@ProvisionOrder(3)
@Singleton
public class EksClusterNodeGroupStep extends EksClusterProvisionStep {

  private final StepResult<EksClusterOutput> metadata;

  @Inject
  protected EksClusterNodeGroupStep(EksSafeClient eks, EksClusterConfig config, StepResult<EksClusterOutput> metadata) {
    super(eks, config);
    this.metadata = metadata;
  }

  @Override
  public StepResult<EksClusterOutput> apply() {

    String step = this.getClass().getName();
    String clusterName = (String) metadata.getOutputs().get(EksClusterOutput.NAME);

    List<EksClusterConfig.EksNodeGroupConfig> nodeGroups = config.getNodeGroups() != null
        ? config.getNodeGroups()
        : List.of();

    EksClusterNodeGroups existingGroups = eks.getNodeGroups(clusterName);
    Map<String, EksClusterNodeGroup> existingByName = existingGroups.getNodeGroups().stream()
        .collect(Collectors.toMap(EksClusterNodeGroup::getName, Function.identity()));

    List<EksClusterNodeGroup> finalProvisionedGroups = new ArrayList<>();

    for (EksClusterConfig.EksNodeGroupConfig ngConfig : nodeGroups) {
      EksClusterNodeGroup desired = new EksClusterNodeGroup(
          ngConfig.getName(),
          ngConfig.getInstanceType(),
          ngConfig.getDesiredSize(),
          ngConfig.getMinSize(),
          ngConfig.getMaxSize(),
          ngConfig.getNodeRoleArn(),
          new SubnetIdSet(ngConfig.getSubnets()),
          ngConfig.getAmiType(),
          ngConfig.getReleaseVersion(),
          ngConfig.getDiskSize(),
          new ProvisionedLabels(ngConfig.getLabels()),
          new ProvisionedTags(ngConfig.getTags())
      );

      EksClusterNodeGroup current = existingByName.get(ngConfig.getName());

      if (current == null) {
        eks.createNodeGroup(clusterName, ngConfig);
        log.info("Created node group '{}' in cluster '{}'", ngConfig.getName(), clusterName);
      } else if (!ngConfig.equals(toConfig(current))) {
        eks.updateNodeGroup(clusterName, desired);
        log.info("Updated node group '{}' in cluster '{}'", ngConfig.getName(), clusterName);
      } else {
        log.info("Node group '{}' is already up to date. Skipping.", ngConfig.getName());
      }

      finalProvisionedGroups.add(desired);
    }

    eks.waitUntilNodeGroupsActive(clusterName, finalProvisionedGroups, Duration.ofMinutes(10));

    EksClusterNodeGroups provisioned = eks.getNodeGroups(clusterName);

    return StepResult.<EksClusterOutput>builder()
        .stepName(step)
        .put(EksClusterOutput.NODE_GROUPS, provisioned)
        .build();
  }


  @Override
  public StepResult<EksClusterOutput> generateDesiredState() {

    String step = this.getClass().getName();

    List<EksClusterConfig.EksNodeGroupConfig> nodeGroups = config.getNodeGroups() != null
        ? config.getNodeGroups()
        : List.of();

    if (nodeGroups.isEmpty()) {
      log.info("No node groups defined in configuration — skipping desired state generation.");
      return StepResult.<EksClusterOutput>builder().stepName(step).build();
    }

    List<EksClusterNodeGroup> desired = new ArrayList<>();

    for (EksClusterConfig.EksNodeGroupConfig ng : nodeGroups) {
      desired.add(new EksClusterNodeGroup(
          ng.getName(),
          ng.getInstanceType(),
          ng.getDesiredSize(),
          ng.getMinSize(),
          ng.getMaxSize(),
          ng.getNodeRoleArn(),
          new SubnetIdSet(ng.getSubnets()),
          ng.getAmiType(),
          ng.getReleaseVersion(),
          ng.getDiskSize(),
          new ProvisionedLabels(ng.getLabels()),
          new ProvisionedTags(ng.getTags())
      ));
    }

    return StepResult.<EksClusterOutput>builder()
        .stepName(step)
        .put(EksClusterOutput.NODE_GROUPS, new EksClusterNodeGroups(desired))
        .build();
  }

  @Override
  public StepResult<EksClusterOutput> getCurrentState() {

    String step = this.getClass().getName();
    String clusterName = (String) metadata.getOutputs().get(EksClusterOutput.NAME);

    try {

      EksClusterNodeGroups nodeGroups = eks.getNodeGroups(clusterName);

      return StepResult.<EksClusterOutput>builder()
          .stepName(step)
          .put(EksClusterOutput.NODE_GROUPS, nodeGroups)
          .build();

    } catch (CloudResourceStepException e) {
      if (e.getCause() instanceof EksException eksException &&
          eksException.statusCode() == HttpStatus.SC_NOT_FOUND) {
        log.info("EKS cluster '{}' not found when retrieving node groups.", clusterName);
        return StepResult.<EksClusterOutput>builder().stepName(step).build();
      }
      throw e;
    }
  }

  @Override
  public StepResult<EksClusterOutput> destroy(boolean enforcePreventDestroy) {

    String step = this.getClass().getName();
    String clusterName = (String) metadata.getOutputs().get(EksClusterOutput.NAME);

    EksClusterNodeGroups nodeGroups = eks.getNodeGroups(clusterName);

    if (nodeGroups == null || nodeGroups.getNodeGroups().isEmpty()) {
      log.info("No node groups recorded in metadata — nothing to delete.");
      return StepResult.<EksClusterOutput>builder().stepName(step).build();
    }

    for (EksClusterNodeGroup nodeGroup : nodeGroups.getNodeGroups()) {
      eks.deleteNodeGroup(clusterName, nodeGroup.getName());
    }

    eks.waitUntilNodeGroupsDeleted(clusterName, nodeGroups, Duration.ofMinutes(10));

    log.info("Deleted {} node group(s) from EKS cluster '{}'", nodeGroups.getNodeGroups().size(), clusterName);

    return StepResult.<EksClusterOutput>builder().stepName(step).build();
  }


  @Override
  public StepResult<EksClusterOutput> revert(StepResult<EksClusterOutput> previous) {

    String step = this.getClass().getName();
    String clusterName = (String) metadata.getOutputs().get(EksClusterOutput.NAME);
    EksClusterNodeGroups revert = (EksClusterNodeGroups) previous.getOutputs().get(EksClusterOutput.NODE_GROUPS);

    EksClusterNodeGroups current = eks.getNodeGroups(clusterName);
    Set<EksClusterNodeGroup> currentSet = new HashSet<>(current.getNodeGroups());
    Set<EksClusterNodeGroup> previousSet = revert != null ? new HashSet<>(revert.getNodeGroups()) : Set.of();

    Set<String> previousNames = previousSet.stream()
        .map(EksClusterNodeGroup::getName)
        .collect(Collectors.toSet());

    for (EksClusterNodeGroup ng : currentSet) {
      if (!previousNames.contains(ng.getName())) {
        eks.deleteNodeGroup(clusterName, ng.getName());
        log.info("Deleted node group '{}' from cluster '{}'", ng.getName(), clusterName);
      }
    }

    for (EksClusterNodeGroup desired : previousSet) {
      Optional<EksClusterNodeGroup> match = currentSet.stream()
          .filter(currentNg -> currentNg.getName().equals(desired.getName()))
          .findFirst();

      if (match.isEmpty()) {
        EksClusterConfig.EksNodeGroupConfig config = toConfig(desired);
        eks.createNodeGroup(clusterName, config);
        log.info("Restored node group '{}' in cluster '{}'", desired.getName(), clusterName);

      } else if (!desired.equals(match.get())) {
        eks.updateNodeGroup(clusterName, desired);
        log.info("Updated node group '{}' in cluster '{}'", desired.getName(), clusterName);
      }
    }

    EksClusterNodeGroups finalState = eks.getNodeGroups(clusterName);
    return StepResult.<EksClusterOutput>builder()
        .stepName(step)
        .put(EksClusterOutput.NODE_GROUPS, finalState)
        .build();
  }

  private EksClusterConfig.EksNodeGroupConfig toConfig(EksClusterNodeGroup ng) {

    EksClusterConfig.EksNodeGroupConfig config = new EksClusterConfig.EksNodeGroupConfig();
    config.setName(ng.getName());
    config.setInstanceType(ng.getInstanceType());
    config.setMinSize(ng.getMinSize());
    config.setMaxSize(ng.getMaxSize());
    config.setDesiredSize(ng.getDesiredSize());
    config.setNodeRoleArn(ng.getNodeRoleArn());
    config.setLabels(ng.getLabels().getLabels());
    config.setTags(ng.getTags().getTags());
    config.setSubnets(new ArrayList<>(ng.getSubnetIdSet().getSubnetIds()));
    config.setAmiType(ng.getAmiType());
    config.setReleaseVersion(ng.getReleaseVersion());
    config.setDiskSize(ng.getDiskSize());

    return config;
  }

}
