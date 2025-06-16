package bg.tuvarna.sit.cloud.core.aws.s3;

import bg.tuvarna.sit.cloud.core.aws.s3.config.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.config.S3BucketConfigList;
import bg.tuvarna.sit.cloud.core.aws.s3.state.S3DesiredStateGenerator;
import bg.tuvarna.sit.cloud.core.aws.s3.state.S3LiveStateGenerator;
import bg.tuvarna.sit.cloud.core.aws.s3.state.S3StateComparator;
import bg.tuvarna.sit.cloud.core.aws.s3.state.S3StoredStateLoader;
import bg.tuvarna.sit.cloud.core.aws.s3.step.S3PersistentMetadataStep;
import bg.tuvarna.sit.cloud.core.provisioner.CloudBundleRunner;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionerSuccessfulResponse;
import bg.tuvarna.sit.cloud.core.provisioner.CloudResourceType;
import bg.tuvarna.sit.cloud.core.provisioner.ErrorCode;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import bg.tuvarna.sit.cloud.core.provisioner.StepResultStateWriter;
import bg.tuvarna.sit.cloud.exception.CloudProvisioningTerminationException;
import bg.tuvarna.sit.cloud.exception.ConfigurationLoadException;
import bg.tuvarna.sit.cloud.utils.ConfigurationUtil;
import bg.tuvarna.sit.cloud.utils.EnvVar;
import bg.tuvarna.sit.cloud.utils.Slf4jLoggingUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import lombok.extern.slf4j.Slf4j;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

// TODO [Idea] Readability of objects and methods
@Singleton
@Slf4j
public class S3CloudBundleRunner extends CloudBundleRunner<S3Output> {

  private final Slf4jLoggingUtil loggingUtil;

  @Inject
  public S3CloudBundleRunner(@Named("yamlMapper") ObjectMapper yaml, S3StoredStateLoader storedState,
                             S3StateComparator stateComparator, StepResultStateWriter<S3Output> stateWriter,
                             ConfigurationUtil config, Slf4jLoggingUtil loggingUtil) {

    super(yaml, storedState, stateComparator, config, stateWriter);
    this.loggingUtil = loggingUtil;
  }

  @Override
  public CloudResourceType getType() {

    return CloudResourceType.S3;
  }

  @Override
  protected String getServiceConfigurationPath() {

    return "src/main/resources/cloud/%s/s3.yml".formatted(EnvVar.AWS_PROFILE.getValue());
  }

  @Override
  public void run(AwsBasicCredentials credentials) {

    String configurationPath = getServiceConfigurationPath();
    log.info("Loading S3 config from: {}", configurationPath);
    S3BucketConfigList configList;
    try {
      configList = config.load(configurationPath, S3BucketConfigList.class);
      if (configList.getBuckets() == null) {
        configList.setBuckets(new ArrayList<>());
      }
    } catch (ConfigurationLoadException e) {
      loggingUtil.logError(log, ErrorCode.S3_CONFIG_LOAD_ERROR, e);
      System.exit(1);
      return;
    }

    Set<String> identifiers = configList.getBuckets()
        .stream()
        .map(S3BucketConfig::getId)
        .collect(Collectors.toSet());

    String profile = EnvVar.AWS_PROFILE.getValue();

    File stateDir = new File(".cloudprovisioner/" + profile + "/" + getType().toString().toLowerCase());

    File[] stateFiles = Optional.ofNullable(stateDir.listFiles((dir, name)
        -> name.startsWith("state-") && name.endsWith(".json"))).orElse(new File[0]);

    Set<String> toDeleteWithId = Arrays.stream(stateFiles)
        .map(File::getName)
        .filter(name -> {
          int hashIndex = name.indexOf('#');
          int jsonIndex = name.lastIndexOf(".json");
          if (hashIndex == -1 || jsonIndex == -1) return false;

          String id = name.substring(hashIndex + 1, jsonIndex);
          return !identifiers.contains(id);
        })
        .collect(Collectors.toSet());

    for (String bucketKey : toDeleteWithId) {

      String[] nameWithIdentifier = bucketKey.split("#");

      S3BucketConfig delete = new S3BucketConfig();
      delete.setName(nameWithIdentifier[0]);
      delete.setId(nameWithIdentifier[1].replace(".json", ""));
      delete.setToDelete(true);
      configList.getBuckets().add(delete);

      log.info("Bucket with configKey '{}' is removed from config and is marked for deletion", bucketKey);
    }

    List<Callable<CloudProvisionerSuccessfulResponse<S3Output>>> tasks = configList.getBuckets().stream()
        .map(config -> (Callable<CloudProvisionerSuccessfulResponse<S3Output>>) () -> provisionS3(config, credentials, profile, stateDir))
        .toList();

    try(ExecutorService executor = Executors.newFixedThreadPool(Math.min(configList.getBuckets().size(), 10))) {

      List<Future<CloudProvisionerSuccessfulResponse<S3Output>>> futures = executor.invokeAll(tasks);
      for (Future<CloudProvisionerSuccessfulResponse<S3Output>> future : futures) {
        try {
          CloudProvisionerSuccessfulResponse<S3Output> response = future.get();
          if (Slf4jLoggingUtil.isJsonLoggingEnabled()) {
            log.info("Provisioning completed", keyValue("response", response));
          } else {
            log.info("Provisioning completed\n{}", yaml.writeValueAsString(response));
          }
          // cleanupStateFiles(bucket);
        } catch (JsonProcessingException e) {
          loggingUtil.logError(log, ErrorCode.SERIALIZATION_ERROR, e);
          // cleanupStateFiles(bucket);
        } catch (ExecutionException | InterruptedException e) {
          loggingUtil.logError(log, ErrorCode.S3_PROVISION_ERROR, e);
        }
      }
    } catch (InterruptedException e) {
      loggingUtil.logError(log, ErrorCode.ASYNC_EXECUTION_ERROR, e);
    }
  }

  private CloudProvisionerSuccessfulResponse<S3Output> provisionS3(S3BucketConfig config,
                                                                   AwsBasicCredentials credentials, String profile,
                                                                   File stateDir)
      throws CloudProvisioningTerminationException {

    log.info("Loading the last persisted infrastructure state from the local library...");

    List<StepResult<S3Output>> loadedState = new ArrayList<>();
    try {
      // TODO [Documentation] state-<bucket_name>#<id>.json, will load by <id>, <bucket_name> is just for human eye
      File[] matchingFiles = Optional.ofNullable(stateDir.listFiles((dir, name) ->
              name.startsWith("state-") && name.endsWith("#" + config.getId() + ".json")))
          .orElse(new File[0]);

      if (matchingFiles.length > 1) {
        String message = "Found multiple state files matching id '%s': count=%d"
            .formatted(config.getId(), matchingFiles.length);
        log.error(message);
        throw new ConfigurationLoadException(message);
      } else if (matchingFiles.length == 0) {
        throw new FileNotFoundException("No state file found for identifier: " + config.getId());
      }

      File stateFile = matchingFiles[0];
      loadedState = storedState.load(stateFile, S3Output.class);
    } catch (FileNotFoundException ex) {
      log.info("Bucket '{}' is not yet provisioned. Will be created from scratch", config.getName());
    } catch (IOException ex) {
      loggingUtil.logError(log, ErrorCode.S3_STORED_STATE_ERROR, ex);
      String message = "Failed to load persisted state for bucket '%s'. File might be unreadable or corrupted."
          .formatted(config.getName());
      throw new CloudProvisioningTerminationException(message, ex);
    }

    StepResult<S3Output> metadata = loadedState.stream()
        .filter(result -> result.getStepName().equals(S3PersistentMetadataStep.class.getName()))
        .findFirst()
        .orElse(StepResult.<S3Output>builder().stepName(S3PersistentMetadataStep.class.getName())
            .put(S3Output.NAME, config.getName())
            .put(S3Output.REGION, config.getRegion())
            .put(S3Output.ARN, config.buildArn())
            .put(S3Output.PREVENT_DESTROY, config.preventDestroy())
            .build());

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);
    String region = (String) metadata.getOutputs().get(S3Output.REGION);

    // Required as config is passed to the steps and on deletion it would be empty otherwise
    if (config.getRegion() == null) {
      config.setRegion(region);
    }

    Injector injector = Guice.createInjector(new S3InjectionModule(
        new S3ProvisioningContext(credentials, EnvVar.S3_ENDPOINT_URL.getValue(), Region.of(region)), config, metadata));

    List<CloudProvisionStep<S3Output>> allSteps =
        injector.getInstance(Key.get(new TypeLiteral<>() {}, Names.named("s3Steps")));

    if (loadedState.isEmpty()) {
      for (CloudProvisionStep<S3Output> step : allSteps) {
        loadedState.add(step.getCurrentState());
      }
    }

    List<StepResult<S3Output>> currentState;
    try {
      currentState = generateLiveCloudState(injector.getInstance(S3LiveStateGenerator.class));
      // TODO [Implementation] We don't need to write current and desired states while provisioning resources
      // TODO [Maybe] Add option to export them if needed with separate 'export' job ?
      //  StepResultStateWriter<S3Output> writer = COMMON_INJECTOR.getInstance(Key.get(new TypeLiteral<>() {}));
      //  writer.write(new File(".cloudprovisioner/current-" + bucket + ".json"), currentState);
    } catch (CloudProvisioningTerminationException e) {
      loggingUtil.logError(log, ErrorCode.S3_LIVE_STATE_ERROR, e);
      throw e;
    }

    List<StepResult<S3Output>> changed;
    log.info("Comparing live state with the last persisted state to detect configuration drift...");

    changed = stateComparator.diff(loadedState, currentState);
    logProvisioningChanges(changed, currentState);

    S3BucketDestroyer destroyer = injector.getInstance(S3BucketDestroyer.class);
    S3BucketProvisioner provisioner = injector.getInstance(S3BucketProvisioner.class);
    CloudProvisionerSuccessfulResponse<S3Output> applied;
    try {
      if (!config.isEnableReconciliation()) {
        log.info("Reconciliation is disabled for bucket '{}'. Skipping provisioning changes", bucket);
        changed = new ArrayList<>();
      }
      applied = applyChanges(profile, bucket, config.getId(), allSteps, destroyer, provisioner, changed, currentState, loadedState);
    } catch (CloudProvisioningTerminationException e) {
      loggingUtil.logError(log, ErrorCode.S3_RECONCILIATION_ERROR, e);
      // cleanupStateFiles(bucket);
      throw e;
    }

    List<StepResult<S3Output>> desiredState;
    try {
      desiredState = generateDesiredState(injector.getInstance(S3DesiredStateGenerator.class));
      // TODO [Implementation] We don't need to write current and desired states while provisioning resources
      // TODO [Maybe] Add option to export them if needed with separate 'export' job ?
      //  StepResultStateWriter<S3Output> writer = COMMON_INJECTOR.getInstance(
      //      Key.get(new TypeLiteral<>() {}));
      //  writer.write(new File(".cloudprovisioner/desired-" + bucket + ".json"), desiredState);
    } catch (CloudProvisioningTerminationException e) {
      loggingUtil.logError(log, ErrorCode.S3_DESIRED_STATE_ERROR, e);
      // cleanupStateFiles(bucket);
      throw e;
    }

    log.info("Comparing desired configuration with the current live state to identify necessary changes...");
    changed = stateComparator.diff(desiredState, loadedState);
    logProvisioningChanges(changed, currentState);

    try {
      applied = applyChanges(profile, bucket, config.getId(), allSteps, destroyer, provisioner, changed, applied.getResults(),
          loadedState);
    } catch (CloudProvisioningTerminationException e) {
      // TODO [Documentation] transactional pattern. All steps have to succeed in order to see changes live
      loggingUtil.logError(log, ErrorCode.S3_PROVISION_ERROR, e);
      try {
        log.info("Initiating rollback to the initial state to maintain transactional consistency...");
        currentState = generateLiveCloudState(injector.getInstance(S3LiveStateGenerator.class));
        changed = stateComparator.diff(loadedState, currentState);
        logProvisioningChanges(changed, currentState);
        S3BucketReverter reverter = injector.getInstance(S3BucketReverter.class);
        revertChanges(profile, bucket, config.getId(), allSteps, destroyer, reverter, changed, loadedState);
      } catch (CloudProvisioningTerminationException ex) {
        loggingUtil.logError(log, ErrorCode.S3_PROVISION_ERROR, e);
        // cleanupStateFiles(bucket);
        throw ex;
      }
      log.warn("Provisioning failed for bucket '{}', but all changes were rolled back to the previously "
          + "persisted state. No partial modifications remain.", bucket);
      // cleanupStateFiles(bucket);
      throw e;
    }

    return applied;
  }

}
