package bg.tuvarna.sit.cloud.core.provisioner;

import bg.tuvarna.sit.cloud.exception.CloudProvisioningTerminationException;
import bg.tuvarna.sit.cloud.exception.StepResultStateWriteException;

import bg.tuvarna.sit.cloud.utils.ConfigurationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

// TODO [Maybe] Provider hierarchy e.g AWS/GCP
@Slf4j
public abstract class CloudBundleRunner<K extends Enum<K>> {

  protected final ObjectMapper yaml;
  protected final BaseStoredStateLoader<K> storedState;
  protected final StateComparator<K> stateComparator;
  protected final ConfigurationUtil config;
  private final StepResultStateWriter<K> stateWriter;

  protected CloudBundleRunner(ObjectMapper yaml, BaseStoredStateLoader<K> storedState,
                              StateComparator<K> stateComparator, ConfigurationUtil config,
                              StepResultStateWriter<K> stateWriter) {
    this.yaml = yaml;
    this.storedState = storedState;
    this.stateComparator = stateComparator;
    this.config = config;
    this.stateWriter = stateWriter;
  }

  protected abstract CloudResourceType getType();

  protected abstract String getServiceConfigurationPath();

  protected abstract void run(AwsBasicCredentials credentials);

  protected List<StepResult<K>> generateLiveCloudState(LiveStateGenerator<K> liveState) throws CloudProvisioningTerminationException {

    log.info("Retrieving the current live state of resources from the cloud provider...");

    return liveState.generate();
  }

  protected void logProvisioningChanges(List<StepResult<K>> changed, List<StepResult<K>> currentState) {

    if (changed == null || changed.isEmpty()) {
      log.info("No changes detected. Skipping provisioning");
    } else {
      log.info("Differences detected in steps: {}", changed.stream().map(StepResult::getStepName).toList());
      logDifferences(currentState, changed);
    }
  }

  protected CloudProvisionerSuccessfulResponse<K> applyChanges(
      String profile,
      String bucket,
      String id,
      List<CloudProvisionStep<K>> allSteps,
      CloudResourceDestroyer<K> destroyer,
      CloudResourceProvisioner<K> provisioner,
      List<StepResult<K>> changes,
      List<StepResult<K>> referenceState,
      List<StepResult<K>> originState
  ) throws CloudProvisioningTerminationException {

    // Validate if changes are allowed
    verifyModificationRestrictions(changes, referenceState);

    // Determine which steps to apply
    List<CloudProvisionStep<K>> stepsToProvision = resolveStepsToProvision(allSteps, changes);

    // Determine which steps to execute
    List<CloudProvisionStep<K>> stepsToDelete = resolveStepsToDelete(allSteps, changes);

    CloudProvisionerSuccessfulResponse<K> destroyed = destroy(destroyer, stepsToDelete, true);

    if (isMainResourceDeleted(destroyed, profile, bucket, id)) {
      return destroyed;
    }

    CloudProvisionerSuccessfulResponse<K> response = provision(provisioner, stepsToProvision);
    mergeAndPersistState(profile, bucket, id, response, originState);

    return response;
  }

  protected List<StepResult<K>> generateDesiredState(DesiredStateGenerator<K> desiredState)
      throws CloudProvisioningTerminationException {

    log.info("Computing the desired infrastructure state based on the current configuration...");

    return desiredState.generate();
  }

  @SuppressWarnings("UnusedReturnValue")
  protected CloudProvisionerSuccessfulResponse<K> revertChanges(
      String profile,
      String bucket,
      String id,
      List<CloudProvisionStep<K>> allSteps,
      CloudResourceDestroyer<K> destroyer,
      CloudResourceReverter<K> reverter,
      List<StepResult<K>> changes,
      List<StepResult<K>> originState
  ) throws CloudProvisioningTerminationException {

    // Determine which steps to apply
    List<CloudProvisionStep<K>> stepsToProvision = resolveStepsToProvision(allSteps, changes);

    // Determine which steps to execute
    List<CloudProvisionStep<K>> stepsToDelete = resolveStepsToDelete(allSteps, changes);

    CloudProvisionerSuccessfulResponse<K> destroyed = destroy(destroyer, stepsToDelete, false);

    if (isMainResourceDeleted(destroyed, profile, bucket, id)) {
      return destroyed;
    }

    CloudProvisionerSuccessfulResponse<K> response = revert(reverter, stepsToProvision, originState);
    mergeAndPersistState(profile, bucket, id, response, originState);

    return response;
  }

  private String formatOutputs(Map<K, Object> outputs) {

    return outputs.entrySet().stream()
        .map(e -> "  • " + e.getKey() + ": " + e.getValue())
        .collect(Collectors.joining("\n"));
  }

  private void logDifferences(List<StepResult<K>> loaded, List<StepResult<K>> changed) {

    Map<String, StepResult<K>> loadedMap = loaded.stream()
        .collect(Collectors.toMap(StepResult::getStepName, r -> r));

    Map<String, StepResult<K>> changedMap = changed.stream()
        .collect(Collectors.toMap(StepResult::getStepName, r -> r));

    for (String stepName : changedMap.keySet()) {
      StepResult<K> newResult = changedMap.get(stepName);
      StepResult<K> oldResult = loadedMap.get(stepName);

      if (oldResult == null) {
        log.info("🆕 Step '{}' is new:\n{}", stepName, formatOutputs(newResult.getOutputs()));
        continue;
      }

      Map<K, Object> oldOutputs = oldResult.getOutputs();
      Map<K, Object> newOutputs = newResult.getOutputs();

      if (!oldOutputs.isEmpty() && newOutputs.isEmpty()) {
        log.info("🗑 Step '{}' has been cleared and will be deleted.", stepName);
        continue;
      }

      List<String> diffs = new ArrayList<>();
      for (Map.Entry<K, Object> entry : newOutputs.entrySet()) {
        Object oldVal = oldOutputs.get(entry.getKey());
        Object newVal = entry.getValue();

        if (!Objects.equals(oldVal, newVal)) {
          diffs.add("  ↪ " + entry.getKey() + ": '" + oldVal + "' → '" + newVal + "'");
        }
      }

      if (!diffs.isEmpty()) {
        log.info("🔁 Changes in step '{}':\n{}", stepName, String.join("\n", diffs));
      }
    }
  }

  private void verifyModificationRestrictions(List<StepResult<K>> changes, List<StepResult<K>> referenceState)
      throws CloudProvisioningTerminationException {

    for (StepResult<K> change : changes) {
      String stepName = change.getStepName();
      try {
        Class<?> stepClass = Class.forName(stepName);
        if (stepClass.isAnnotationPresent(PreventModification.class) && !change.isVoid()) {

          StepResult<K> referenceResult = referenceState.stream()
              .filter(ref -> stepName.equals(ref.getStepName()))
              .findFirst()
              .orElse(null);

          if (referenceResult != null && !referenceResult.isVoid()) {
            throw new CloudProvisioningTerminationException(
                "Modification of step '%s' is prevented by @PreventModification".formatted(stepName)
            );
          }
        }
      } catch (ClassNotFoundException e) {
        throw new CloudProvisioningTerminationException(
            "Unable to verify modification restrictions for step '%s' (class not found)".formatted(stepName), e);
      }
    }
  }

  private List<CloudProvisionStep<K>> resolveStepsToProvision(List<CloudProvisionStep<K>> allSteps, List<StepResult<K>> changes) {

    Set<String> changedStepNames = changes.stream()
        .map(StepResult::getStepName)
        .collect(Collectors.toSet());

    return allSteps.stream()
        .filter(step -> changedStepNames.contains(step.getClass().getName()))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  private List<CloudProvisionStep<K>> resolveStepsToDelete(List<CloudProvisionStep<K>> allSteps, List<StepResult<K>> changes) {

    Set<String> stepNamesToDelete = changes.stream()
        .filter(StepResult::isVoid)
        .map(StepResult::getStepName)
        .collect(Collectors.toSet());

    return allSteps.stream()
        .filter(step -> stepNamesToDelete.contains(step.getClass().getName()))
        .toList();
  }

  private CloudProvisionerSuccessfulResponse<K> destroy(CloudResourceDestroyer<K> destroyer,
                                                        List<CloudProvisionStep<K>> resources,
                                                        boolean enforcePreventDestroy)
      throws CloudProvisioningTerminationException {

    if (resources.isEmpty()) {
      log.info("Nothing to destroy. Continuing with provisioning phase.");
    }

    return destroyer.destroy(resources, enforcePreventDestroy);
  }

  private boolean isMainResourceDeleted(CloudProvisionerSuccessfulResponse<K> destroyed,
                                               String profile, String bucket, String id) {

    for (StepResult<K> destroyedResource : destroyed.getResults()) {

      try {

        Class<?> stepClass = Class.forName(destroyedResource.getStepName());
        if (stepClass.isAnnotationPresent(MainResource.class)) {

          log.info("Main resource '{}' marked for deletion", stepClass.getSimpleName());
          File file = new File(".cloudprovisioner/" + profile + "/" + getType().toString().toLowerCase()
              + "/state-" + bucket + "#" + id + ".json");
          deleteFileIfExists(file, "Deleted state file for destroyed bucket: {}", "Failed to execute state file: {}");

          return true;
        }
      } catch (ClassNotFoundException e) {
        log.warn("Step class '{}' not found during main resource check", destroyedResource.getStepName(), e);
      }
    }

    return false;
  }

  @SuppressWarnings("SameParameterValue")
  private static void deleteFileIfExists(File file, String successful, String failed) {

    if (file.exists()) {
      if (file.delete()) {
        log.info(successful, file.getName());
      } else {
        log.warn(failed, file.getName());
      }
    }
  }

  private CloudProvisionerSuccessfulResponse<K> provision(CloudResourceProvisioner<K> provisioner,
                                                          List<CloudProvisionStep<K>> resources)
      throws CloudProvisioningTerminationException {

    if (resources.isEmpty()) {
      log.info("Nothing to dispatch. Infrastructure is up to date.");
    }

    return provisioner.provision(resources);
  }

  private List<StepResult<K>> mergeStates(List<StepResult<K>> originState, List<StepResult<K>> resultUpdates) {

    Map<String, StepResult<K>> mergedMap = new LinkedHashMap<>();
    originState.forEach(r -> mergedMap.put(r.getStepName(), r));
    resultUpdates.forEach(r -> mergedMap.put(r.getStepName(), r));

    return new ArrayList<>(mergedMap.values());
  }

  private void mergeAndPersistState(
      String profile,
      String bucket,
      String id,
      CloudProvisionerSuccessfulResponse<K> response,
      List<StepResult<K>> originState
  ) {
    List<StepResult<K>> mergedList = mergeStates(originState, response.getResults());
    response.setResults(mergedList);

    File targetFile = new File(".cloudprovisioner/" + profile + "/" + getType().toString().toLowerCase()
        + "/state-" + bucket + "#" + id + ".json");
    try {
      stateWriter.write(targetFile, mergedList);
    } catch (StepResultStateWriteException e) {
      log.warn("Failed to persist step results for bucket '{}'. "
          + "Provisioning changes were applied, but state was not saved. Reason: {}", bucket, e.getMessage());
    }
  }

  private CloudProvisionerSuccessfulResponse<K> revert(CloudResourceReverter<K> reverter,
                                                       List<CloudProvisionStep<K>> resources,
                                                       List<StepResult<K>> previous)
      throws CloudProvisioningTerminationException {

    if (resources.isEmpty()) {
      log.info("No changes detected. Skipping revert operation "
          + "as all resources are already in their previous state.");
    }

    return reverter.revert(resources, previous);
  }
}
