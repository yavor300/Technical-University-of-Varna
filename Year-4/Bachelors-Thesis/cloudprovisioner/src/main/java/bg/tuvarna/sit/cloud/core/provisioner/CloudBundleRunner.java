package bg.tuvarna.sit.cloud.core.provisioner;

import bg.tuvarna.sit.cloud.utils.StepResultStateWriter;
import bg.tuvarna.sit.cloud.core.provisioner.model.CloudProvisionerSuccessfulResponse;
import bg.tuvarna.sit.cloud.core.provisioner.model.CloudResourceType;
import bg.tuvarna.sit.cloud.core.provisioner.model.ErrorCode;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudProvisioningTerminationException;
import bg.tuvarna.sit.cloud.exception.ConfigurationLoadException;
import bg.tuvarna.sit.cloud.exception.StepResultStateWriteException;
import bg.tuvarna.sit.cloud.utils.ConfigurationUtil;
import bg.tuvarna.sit.cloud.utils.Slf4jLoggingUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.inject.Injector;

import lombok.Builder;

import org.slf4j.Logger;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

// TODO [Maybe] Provider hierarchy e.g AWS/GCP
public abstract class CloudBundleRunner<K extends Enum<K>> {

  protected final Logger log;
  protected final ObjectMapper yaml;
  protected final BaseStoredStateLoader<K> storedState;
  protected final StateComparator<K> stateComparator;
  protected final ConfigurationUtil configUtil;
  protected final Slf4jLoggingUtil loggingUtil;

  private final StepResultStateWriter<K> stateWriter;

  protected CloudBundleRunner(org.slf4j.Logger log, ObjectMapper yaml, BaseStoredStateLoader<K> storedState,
                              StateComparator<K> stateComparator, ConfigurationUtil configUtil,
                              StepResultStateWriter<K> stateWriter, Slf4jLoggingUtil loggingUtil) {
    this.log = log;
    this.yaml = yaml;
    this.storedState = storedState;
    this.stateComparator = stateComparator;
    this.configUtil = configUtil;
    this.stateWriter = stateWriter;
    this.loggingUtil = loggingUtil;
  }

  protected abstract CloudResourceType getType();

  protected abstract String getServiceConfigurationPath();

  // TODO [Enhancement] Do not depend on Aws(Specific)BasicCredentials
  protected abstract void run(AwsBasicCredentials credentials);

  protected <C extends BaseCloudResourceConfiguration, L extends CloudResourceConfigList<C>> L loadConfig(String path,
                                                                                                          Class<L> configClass) {
    L configList = configUtil.load(path, configClass);
    if (configList.getItems() == null) {
      configList.setItems(new ArrayList<>());
    }
    return configList;
  }

  protected Set<String> findObsoleteStateFiles(File stateDir, Set<String> currentIds) {

    File[] stateFiles = Optional.ofNullable(stateDir.listFiles((dir, name) ->
        name.startsWith("state-") && name.endsWith(".json"))).orElse(new File[0]);

    return Arrays.stream(stateFiles)
        .map(File::getName)
        .filter(name -> {
          int hashIndex = name.indexOf('#');
          int jsonIndex = name.lastIndexOf(".json");
          if (hashIndex == -1 || jsonIndex == -1) return false;
          String id = name.substring(hashIndex + 1, jsonIndex);
          return !currentIds.contains(id);
        })
        .collect(Collectors.toSet());
  }

  protected <C extends BaseCloudResourceConfiguration, O extends Enum<O>> void executeProvisioningTasks(
      List<C> configs,
      int fixedThreadPoolMaxSize,
      Function<C, Callable<CloudProvisionerSuccessfulResponse<O>>> taskMapper,
      ErrorCode errorCode
  ) {

    try (ExecutorService executor = Executors.newFixedThreadPool(Math.min(configs.size(), fixedThreadPoolMaxSize))) {
      List<Future<CloudProvisionerSuccessfulResponse<O>>> futures = executor.invokeAll(
          configs.stream().map(taskMapper).toList());

      for (Future<CloudProvisionerSuccessfulResponse<O>> future : futures) {
        try {
          CloudProvisionerSuccessfulResponse<O> response = future.get();
          if (Slf4jLoggingUtil.isJsonLoggingEnabled()) {
            log.info("Provisioning completed", keyValue("response", response));
          } else {
            log.info("Provisioning completed\n{}", yaml.writeValueAsString(response));
          }
        } catch (JsonProcessingException e) {
          loggingUtil.logError(log, ErrorCode.SERIALIZATION_ERROR, e);
        } catch (ExecutionException | InterruptedException e) {
          loggingUtil.logError(log, errorCode, e.getCause());
        }
      }
    } catch (InterruptedException e) {
      loggingUtil.logError(log, ErrorCode.ASYNC_EXECUTION_ERROR, e.getCause());
    }
  }

  protected <C extends BaseCloudResourceConfiguration> CloudProvisionerSuccessfulResponse<K> provisionGeneric(
      ProvisionRequest<K, C> request
  ) throws CloudProvisioningTerminationException {

    log.info("Loading the last persisted infrastructure state from the local library...");

    List<StepResult<K>> loadedState = new ArrayList<>();
    try {
      File[] matchingFiles = Optional.ofNullable(request.stateDir.listFiles((dir, name) ->
          name.startsWith("state-") && name.endsWith("#" + request.config.getId() + ".json"))).orElse(new File[0]);

      if (matchingFiles.length > 1) {
        throw new ConfigurationLoadException("Found multiple state files for id: " + request.config.getId());
      } else if (matchingFiles.length == 0) {
        throw new FileNotFoundException("No state file found for id: " + request.config.getId());
      }

      File stateFile = matchingFiles[0];
      loadedState = storedState.load(stateFile, request.outputClass);
    } catch (FileNotFoundException e) {
      log.info("{} '{}' is not yet provisioned. Will be created from scratch", getType().getValue(), request.config.getName());
    } catch (IOException e) {
      loggingUtil.logError(log, ErrorCode.S3_STORED_STATE_ERROR, e);
      String message = "Failed to load persisted state for %s '%s'. File might be unreadable or corrupted."
          .formatted(getType().getValue().toLowerCase(), request.config.getName());
      throw new CloudProvisioningTerminationException(message, e);
    }

    StepResult<K> metadata = loadedState.stream()
        .filter(result -> result.getStepName().equals(request.metadataStepName))
        .findFirst()
        .orElseGet(() -> request.metadataSupplier.apply(request.config));

    String name = request.nameExtractor.apply(metadata);
    String region = request.regionExtractor.apply(metadata);

    Injector injector = request.injectorFactory.apply(region, metadata);

    List<CloudProvisionStep<K>> allSteps = request.stepProvider.apply(injector);
    if (loadedState.isEmpty()) {
      for (CloudProvisionStep<K> step : allSteps) {
        loadedState.add(step.getCurrentState());
      }
    }

    List<StepResult<K>> currentState = request.liveStateGenerator.apply(injector);
    log.info("Comparing live state with the last persisted state to detect configuration drift...");
    List<StepResult<K>> changed = stateComparator.diff(loadedState, currentState);
    logProvisioningChanges(changed, currentState);

    CloudResourceDestroyer<K> destroyer = request.destroyerProvider.apply(injector);
    CloudResourceProvisioner<K> provisioner = request.provisionerProvider.apply(injector);
    CloudProvisionerSuccessfulResponse<K> applied;

    try {
      if (!request.config.isEnableReconciliation()) {
        log.info("Reconciliation is disabled for {} '{}'. Skipping provisioning changes", getType().getValue().toLowerCase(), name);
        changed = new ArrayList<>();
      }

      applied = applyChanges(request.profile, name, request.config.getId(), allSteps, destroyer, provisioner, changed, currentState, loadedState);
    } catch (CloudProvisioningTerminationException e) {

      handleRollback(injector, destroyer, request.reverterProvider, name, request.profile, request.config.getId(), allSteps, loadedState, request.liveStateGenerator);
      throw e;
    }

    List<StepResult<K>> desiredState = request.desiredStateGenerator.apply(injector);
    log.info("Comparing desired configuration with the current live state to identify necessary changes...");
    changed = stateComparator.diff(desiredState, loadedState);
    logProvisioningChanges(changed, currentState);

    try {
      applied = applyChanges(request.profile, name, request.config.getId(), allSteps, destroyer, provisioner, changed, applied.getResults(), loadedState);
    } catch (CloudProvisioningTerminationException e) {
      handleRollback(injector, destroyer, request.reverterProvider, name, request.profile, request.config.getId(), allSteps, loadedState, request.liveStateGenerator);
      throw e;
    }

    return applied;
  }

  private void handleRollback(
      Injector injector,
      CloudResourceDestroyer<K> destroyer,
      Function<Injector, CloudResourceReverter<K>> reverterProvider,
      String name,
      String profile,
      String id,
      List<CloudProvisionStep<K>> allSteps,
      List<StepResult<K>> loadedState,
      Function<Injector, List<StepResult<K>>> liveStateGenerator
  ) {

    try {
      log.info("Initiating rollback to the initial state to maintain transactional consistency...");
      List<StepResult<K>> currentState = liveStateGenerator.apply(injector);
      List<StepResult<K>> changed = stateComparator.diff(loadedState, currentState);
      logProvisioningChanges(changed, currentState);

      CloudResourceReverter<K> reverter = reverterProvider.apply(injector);
      revertChanges(profile, name, id, allSteps, destroyer, reverter, changed, loadedState);
    } catch (CloudProvisioningTerminationException rollbackError) {
      loggingUtil.logError(log, ErrorCode.S3_PROVISION_ERROR, rollbackError);
    }
    log.warn("Provisioning failed for {} '{}', but all changes were rolled back to the previously "
        + "persisted state. No partial modifications remain.", getType().getValue().toLowerCase(), name);
  }

  protected List<StepResult<K>> generateLiveCloudState(LiveStateGenerator<K> liveState) throws CloudProvisioningTerminationException {

    log.info("Retrieving the current live state of resources from the cloud provider...");

    return liveState.generate();
  }

  private void logProvisioningChanges(List<StepResult<K>> changed, List<StepResult<K>> currentState) {

    if (changed == null || changed.isEmpty()) {
      log.info("No changes detected. Skipping provisioning");
    } else {
      log.info("Differences detected in steps: {}", changed.stream().map(StepResult::getStepName).toList());
      logDifferences(currentState, changed);
    }
  }

  private CloudProvisionerSuccessfulResponse<K> applyChanges(
      String profile,
      String resourceName,
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

    if (isMainResourceDeleted(destroyed, profile, resourceName, id)) {
      return destroyed;
    }

    CloudProvisionerSuccessfulResponse<K> response = provision(provisioner, stepsToProvision);
    mergeAndPersistState(profile, resourceName, id, response, originState);

    return response;
  }

  protected List<StepResult<K>> generateDesiredState(DesiredStateGenerator<K> desiredState)
      throws CloudProvisioningTerminationException {

    log.info("Computing the desired infrastructure state based on the current configuration...");

    return desiredState.generate();
  }

  @SuppressWarnings("UnusedReturnValue")
  private CloudProvisionerSuccessfulResponse<K> revertChanges(
      String profile,
      String resourceName,
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

    if (isMainResourceDeleted(destroyed, profile, resourceName, id)) {
      return destroyed;
    }

    CloudProvisionerSuccessfulResponse<K> response = revert(reverter, stepsToProvision, originState);
    mergeAndPersistState(profile, resourceName, id, response, originState);

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
                                               String profile, String resourceName, String id) {

    for (StepResult<K> destroyedResource : destroyed.getResults()) {

      try {

        Class<?> stepClass = Class.forName(destroyedResource.getStepName());
        if (stepClass.isAnnotationPresent(MainResource.class)) {

          log.info("Main resource '{}' marked for deletion", stepClass.getSimpleName());
          File file = new File(".cloudprovisioner/" + profile + "/" + getType().toString().toLowerCase()
              + "/state-" + resourceName + "#" + id + ".json");
          deleteFileIfExists(file, "Deleted state file for destroyed {}: {}", "Failed to execute state file: {}");

          return true;
        }
      } catch (ClassNotFoundException e) {
        log.warn("Step class '{}' not found during main resource check", destroyedResource.getStepName(), e);
      }
    }

    return false;
  }

  @SuppressWarnings("SameParameterValue")
  private void deleteFileIfExists(File file, String successful, String failed) {

    if (file.exists()) {
      if (file.delete()) {
        log.info(successful, getType().getValue().toLowerCase(), file.getName());
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
      String resourceName,
      String id,
      CloudProvisionerSuccessfulResponse<K> response,
      List<StepResult<K>> originState
  ) {
    List<StepResult<K>> mergedList = mergeStates(originState, response.getResults());
    response.setResults(mergedList);

    File targetFile = new File(".cloudprovisioner/" + profile + "/" + getType().toString().toLowerCase()
        + "/state-" + resourceName + "#" + id + ".json");
    File parentDir = targetFile.getParentFile();
    if (!parentDir.exists() && !parentDir.mkdirs()) {
      log.warn("Failed to create directories for persisting state: {}", parentDir.getAbsolutePath());
    }

    try {
      stateWriter.write(targetFile, mergedList);
    } catch (StepResultStateWriteException e) {
      log.warn("Failed to persist step results for {} '{}'. "
          + "Provisioning changes were applied, but state was not saved. Reason: {}",
          getType().getValue().toLowerCase(), resourceName, e.getMessage());
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

  @Builder
  public static class ProvisionRequest<K extends Enum<K>, C extends  BaseCloudResourceConfiguration> {

    private final C config;
    private final String profile;
    private final File stateDir;
    private final String metadataStepName;
    private final Function<Injector, List<CloudProvisionStep<K>>> stepProvider;
    private final Function<Injector, CloudResourceDestroyer<K>> destroyerProvider;
    private final Function<Injector, CloudResourceProvisioner<K>> provisionerProvider;
    private final Function<Injector, CloudResourceReverter<K>> reverterProvider;
    private final Function<Injector, List<StepResult<K>>> liveStateGenerator;
    private final Function<Injector, List<StepResult<K>>> desiredStateGenerator;
    private final BiFunction<String, StepResult<K>, Injector> injectorFactory;
    private final Function<C, StepResult<K>> metadataSupplier;
    private final Function<StepResult<K>, String> nameExtractor;
    private final Function<StepResult<K>, String> regionExtractor;
    private final Class<K> outputClass;
  }
}
