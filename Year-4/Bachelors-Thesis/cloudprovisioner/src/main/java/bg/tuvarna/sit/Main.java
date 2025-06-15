package bg.tuvarna.sit;

import bg.tuvarna.sit.cloud.config.S3InjectionModule;
import bg.tuvarna.sit.cloud.exception.ConfigurationLoadException;
import bg.tuvarna.sit.cloud.config.CommonInjectionModule;
import bg.tuvarna.sit.cloud.core.aws.s3.config.S3BucketConfigList;
import bg.tuvarna.sit.cloud.core.aws.s3.S3BucketReverter;
import bg.tuvarna.sit.cloud.core.aws.s3.state.S3LiveStateGenerator;
import bg.tuvarna.sit.cloud.core.aws.s3.S3BucketDestroyer;
import bg.tuvarna.sit.cloud.core.aws.s3.state.S3DesiredStateGenerator;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisioningContext;
import bg.tuvarna.sit.cloud.config.AuthenticationConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.config.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.state.S3StoredStateLoader;
import bg.tuvarna.sit.cloud.core.aws.s3.state.S3StateComparator;
import bg.tuvarna.sit.cloud.core.aws.s3.step.S3PersistentMetadataStep;
import bg.tuvarna.sit.cloud.core.provisioner.ActualStateGenerator;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionerSuccessfulResponse;
import bg.tuvarna.sit.cloud.core.provisioner.CloudResourceProvisioner;
import bg.tuvarna.sit.cloud.core.aws.s3.S3BucketProvisioner;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionerFailureResponse;
import bg.tuvarna.sit.cloud.core.provisioner.CloudResourceReverter;
import bg.tuvarna.sit.cloud.core.provisioner.DesiredStateGenerator;
import bg.tuvarna.sit.cloud.core.provisioner.MainResource;
import bg.tuvarna.sit.cloud.core.provisioner.PreventModification;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import bg.tuvarna.sit.cloud.core.provisioner.StepResultStateWriter;
import bg.tuvarna.sit.cloud.core.provisioner.BaseStoredStateLoader;
import bg.tuvarna.sit.cloud.exception.AuthenticationException;
import bg.tuvarna.sit.cloud.credentials.provider.vault.VaultClient;
import bg.tuvarna.sit.cloud.credentials.provider.vault.VaultAwsCredentialsProvider;
import bg.tuvarna.sit.cloud.core.provisioner.ErrorCode;
import bg.tuvarna.sit.cloud.exception.CloudProvisioningTerminationException;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;
import bg.tuvarna.sit.cloud.exception.StepResultStateWriteException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

import lombok.extern.slf4j.Slf4j;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Slf4j
public class Main {

  static {
    COMMON_INJECTOR = Guice.createInjector(new CommonInjectionModule());
  }

  private static final Injector COMMON_INJECTOR;

  private static final ObjectMapper JSON_MAPPER =
      COMMON_INJECTOR.getInstance(Key.get(ObjectMapper.class, Names.named("jsonMapper")));
  private static final ObjectMapper YAML_MAPPER =
      COMMON_INJECTOR.getInstance(Key.get(ObjectMapper.class, Names.named("yamlMapper")));

  public static void main(String[] args) {

    if (args.length < 2) {
      log.error("Invalid arguments. Usage: java -jar cloudprovisioner.jar <auth-config-path.yml> <s3-config-path.yml>");
      System.exit(1);
      return;
    }

    String authConfigPath = args[0];
    // TODO [Implementation] Think for a modularity per service
    String s3ConfigPath = args[1];

    log.info("Loading authentication configuration from: {}", authConfigPath);
    AuthenticationConfig authConfig;
    try {
      authConfig = loadConfig(authConfigPath, AuthenticationConfig.class);
    } catch (ConfigurationLoadException e) {
      logError(ErrorCode.AUTH_CONFIG_LOAD_ERROR, e);
      System.exit(1);
      return;
    }

    AwsBasicCredentials credentials;
    try {
      credentials = authenticate(authConfig);
    } catch (AuthenticationException e) {
      logError(ErrorCode.AUTH_ERROR, e);
      System.exit(1);
      return;
    }

    log.info("Loading S3 config from: {}", s3ConfigPath);
    S3BucketConfigList s3ConfigList;
    try {
      s3ConfigList = loadConfig(s3ConfigPath, S3BucketConfigList.class);
      if (s3ConfigList.getBuckets() == null) {
        s3ConfigList.setBuckets(new ArrayList<>());
      }
    } catch (ConfigurationLoadException e) {
      logError(ErrorCode.S3_CONFIG_LOAD_ERROR, e);
      System.exit(1);
      return;
    }

    Set<String> identifiers = s3ConfigList.getBuckets()
        .stream()
        .map(S3BucketConfig::getId)
        .collect(Collectors.toSet());

    String profile = System.getenv("AWS_PROFILE");

    // TODO [Implementation] Store state files in S3 system folder/bucket
    File stateDir = new File(".cloudprovisioner/" + profile);
    File[] stateFiles = Optional.ofNullable(stateDir.listFiles((dir, name)
        -> name.startsWith("state-") && name.endsWith(".json"))).orElse(new File[0]);

    Set<String> bucketsToDelete = Arrays.stream(stateFiles)
        .map(File::getName)
        .filter(name -> {
          int hashIndex = name.indexOf('#');
          int jsonIndex = name.lastIndexOf(".json");
          if (hashIndex == -1 || jsonIndex == -1) return false;

          String id = name.substring(hashIndex + 1, jsonIndex);
          return !identifiers.contains(id);
        })
        .collect(Collectors.toSet());

    for (String bucketKey : bucketsToDelete) {

      String[] nameWithIdentifier = bucketKey.split("#");

      S3BucketConfig delete = new S3BucketConfig();
      delete.setName(nameWithIdentifier[0]);
      delete.setId(nameWithIdentifier[1].replace(".json", ""));
      delete.setToDelete(true);
      s3ConfigList.getBuckets().add(delete);

      log.info("Bucket with configKey '{}' is removed from config and is marked for deletion", bucketKey);
    }


    List<Callable<CloudProvisionerSuccessfulResponse<S3Output>>> tasks = s3ConfigList.getBuckets().stream()
        .map(config -> (Callable<CloudProvisionerSuccessfulResponse<S3Output>>) () -> provisionS3(config, credentials, profile, stateDir))
        .toList();

    try(ExecutorService executor = Executors.newFixedThreadPool(Math.min(s3ConfigList.getBuckets().size(), 10))) {

      List<Future<CloudProvisionerSuccessfulResponse<S3Output>>> futures = executor.invokeAll(tasks);
      for (Future<CloudProvisionerSuccessfulResponse<S3Output>> future : futures) {
        try {
          CloudProvisionerSuccessfulResponse<S3Output> response = future.get();
          if (isJsonLoggingEnabled()) {
            log.info("Provisioning completed", keyValue("response", response));
          } else {
            log.info("Provisioning completed\n{}", YAML_MAPPER.writeValueAsString(response));
          }
          // cleanupStateFiles(bucket);
        } catch (JsonProcessingException e) {
          logError(ErrorCode.SERIALIZATION_ERROR, e);
          // cleanupStateFiles(bucket);
        } catch (ExecutionException | InterruptedException e) {
          logError(ErrorCode.S3_PROVISION_ERROR, e);
        }
      }
    } catch (InterruptedException e) {
      logError(ErrorCode.ASYNC_EXECUTION_ERROR, e);
    }
  }

  private static CloudProvisionerSuccessfulResponse<S3Output> provisionS3(S3BucketConfig config, AwsBasicCredentials credentials, String profile,
                                                                          File stateDir) throws CloudProvisioningTerminationException {

    log.info("Loading the last persisted infrastructure state from the local library...");

    BaseStoredStateLoader<S3Output> storedStateLoader = COMMON_INJECTOR.getInstance(S3StoredStateLoader.class);
    List<StepResult<S3Output>> loadedState = new ArrayList<>();
    try {
      // TODO [Documentation] state-<bucket_name>#<primary>.json, will load by <id>, <bucket_name> is just for human eye
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
      loadedState = storedStateLoader.load(stateFile, S3Output.class);
    } catch (FileNotFoundException ex) {
      log.info("Bucket '{}' is not yet provisioned. Will be created from scratch", config.getName());
    } catch (IOException ex) {
      logError(ErrorCode.S3_STORED_STATE_ERROR, ex);
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
        new S3ProvisioningContext(credentials, System.getenv("S3_ENDPOINT_URL"), Region.of(region)), config, metadata));

    List<CloudProvisionStep<S3Output>> allSteps =
        injector.getInstance(Key.get(new TypeLiteral<>() {
        }, Names.named("s3Steps")));

    if (loadedState.isEmpty()) {
      for (CloudProvisionStep<S3Output> step : allSteps) {
        loadedState.add(step.getCurrentState());
      }
    }

    List<StepResult<S3Output>> currentState;
    try {
      currentState = generateLiveCloudState(injector);
      // TODO [Implementation] We don't need to write current and desired states while provisioning resources
      // TODO [Maybe] Add option to export them if needed with separate 'export' job ?
      //  StepResultStateWriter<S3Output> writer = COMMON_INJECTOR.getInstance(Key.get(new TypeLiteral<>() {}));
      //  writer.write(new File(".cloudprovisioner/current-" + bucket + ".json"), currentState);
    } catch (CloudProvisioningTerminationException e) {
      logError(ErrorCode.S3_LIVE_STATE_ERROR, e);
      throw e;
    }

    S3StateComparator comparator = COMMON_INJECTOR.getInstance(S3StateComparator.class);
    List<StepResult<S3Output>> changed;
    log.info("Comparing live state with the last persisted state to detect configuration drift...");

    changed = comparator.diff(loadedState, currentState);
    logProvisioningChanges(changed, currentState);

    CloudProvisionerSuccessfulResponse<S3Output> applied;
    try {
      if (!config.isEnableReconciliation()) {
        log.info("Reconciliation is disabled for bucket '{}'. Skipping provisioning changes", bucket);
        changed = new ArrayList<>();
      }
      applied = applyChanges(profile, bucket, config.getId(), injector, changed, currentState, loadedState);
    } catch (CloudProvisioningTerminationException e) {
      logError(ErrorCode.S3_RECONCILIATION_ERROR, e);
      // cleanupStateFiles(bucket);
      throw e;
    }

    List<StepResult<S3Output>> desiredState;
    try {
      desiredState = generateDesiredState(injector);
      // TODO [Implementation] We don't need to write current and desired states while provisioning resources
      // TODO [Maybe] Add option to export them if needed with separate 'export' job ?
      //  StepResultStateWriter<S3Output> writer = COMMON_INJECTOR.getInstance(
      //      Key.get(new TypeLiteral<>() {}));
      //  writer.write(new File(".cloudprovisioner/desired-" + bucket + ".json"), desiredState);
    } catch (CloudProvisioningTerminationException e) {
      logError(ErrorCode.S3_DESIRED_STATE_ERROR, e);
      // cleanupStateFiles(bucket);
      throw e;
    }

    log.info("Comparing desired configuration with the current live state to identify necessary changes...");
    changed = comparator.diff(desiredState, loadedState);
    logProvisioningChanges(changed, currentState);

    try {
      applied = applyChanges(profile, bucket, config.getId(), injector, changed, applied.getResults(),
          loadedState);
    } catch (CloudProvisioningTerminationException e) {
      // TODO [Documentation] transactional pattern. All steps have to succeed in order to see changes live
      logError(ErrorCode.S3_PROVISION_ERROR, e);
      try {
        log.info("Initiating rollback to the initial state to maintain transactional consistency...");
        currentState = generateLiveCloudState(injector);
        changed = comparator.diff(loadedState, currentState);
        logProvisioningChanges(changed, currentState);
        revertChanges(profile, bucket, config.getId(), injector, changed, loadedState);
      } catch (CloudProvisioningTerminationException ex) {
        logError(ErrorCode.S3_PROVISION_ERROR, e);
        // cleanupStateFiles(bucket);
        throw ex;
      }
      log.warn("Provisioning failed for bucket '{}', but all changes were rolled back to the previously "
          + "persisted state. No partial modifications remain.", bucket);
      // cleanupStateFiles(bucket);
      throw e;
    }

    return applied;
    // TODO [Implementation] returned serialized response in the Executor job to avoid returning null here
    //  try {
    //    String json = JSON_MAPPER.writeValueAsString(applied);
    //    log.info("Provisioning completed", StructuredArguments.keyValue("provisioning", json));
    //      cleanupStateFiles(bucket);
    //  } catch (JsonProcessingException e) {
    //    logError(ErrorCode.SERIALIZATION_ERROR, e);
    //      cleanupStateFiles(bucket);
    //    System.exit(1);
    //  }
  }

  private static <T> T loadConfig(String path, Class<T> clazz) {

    File file = new File(path);

    if (!file.exists()) {
      throw new ConfigurationLoadException("Configuration file not found: " + path);
    }

    try {
      return YAML_MAPPER.readValue(file, clazz);
    } catch (IOException e) {
      throw new ConfigurationLoadException("Failed to parse configuration file at path: " + path, e);
    }
  }

  public static String resolveEnvPlaceholders(String input) {

    if (input == null) return null;

    Pattern pattern = Pattern.compile("\\$\\{(.+?)}");
    Matcher matcher = pattern.matcher(input);
    StringBuilder buffer = new StringBuilder();
    while (matcher.find()) {
      String envVar = matcher.group(1);
      String value = System.getenv(envVar);
      if (value == null) {
        log.warn("Environment variable '{}' not found. Replacing with empty value.", envVar);
        value = "";
      }
      matcher.appendReplacement(buffer, Matcher.quoteReplacement(value));
    }
    matcher.appendTail(buffer);

    return buffer.toString();
  }

  public static boolean isJsonLoggingEnabled() {
    return "json".equalsIgnoreCase(System.getenv("LOG_FORMAT"));
  }

  private static AwsBasicCredentials authenticate(AuthenticationConfig config) {

    for (AuthenticationConfig.ProviderConfig provider : config.getProviders()) {

      try {

        // Vault authentication
        if (provider.getVault() != null) {
          AuthenticationConfig.VaultConfig vault = provider.getVault();

          if (vault.getToken() != null) {
            String resolvedToken = resolveEnvPlaceholders(vault.getToken());
            vault.setToken(resolvedToken);

            log.info("Authenticating using Vault (host={}, port={}, secretPath={})",
                vault.getHost(), vault.getPort(), vault.getSecretPath());

            VaultAwsCredentialsProvider vaultProvider =
                new VaultAwsCredentialsProvider(new VaultClient(vault, JSON_MAPPER), YAML_MAPPER);

            return vaultProvider.fetchCredentials().toAwsBasicCredentials();
          }
        }

        // Static credentials authentication
        if (provider.getStaticCredentials() != null) {
          AuthenticationConfig.StaticConfig staticCfg = provider.getStaticCredentials();
          String accessKeyId = resolveEnvPlaceholders(staticCfg.getAccessKeyId());
          String secretAccessKey = resolveEnvPlaceholders(staticCfg.getSecretAccessKey());
          if (!accessKeyId.isBlank() && !secretAccessKey.isBlank()) {
            log.info("Authenticating using static credentials");
            return AwsBasicCredentials.create(accessKeyId, secretAccessKey);
          }
        }

      } catch (AuthenticationException e) {
        log.warn("Authentication attempt failed using available configuration: {}", e.getMessage());
      }
    }

    throw new AuthenticationException("No authentication method succeeded");
  }

  private static List<StepResult<S3Output>> generateDesiredState(Injector injector)
      throws CloudProvisioningTerminationException {

    log.info("Computing the desired infrastructure state based on the current configuration...");
    DesiredStateGenerator<S3Output> s3 = injector.getInstance(S3DesiredStateGenerator.class);

    return s3.generate();
  }

  private static List<StepResult<S3Output>> generateLiveCloudState(Injector injector)
      throws CloudProvisioningTerminationException {

    log.info("Retrieving the current live state of resources from the cloud provider...");
    ActualStateGenerator<S3Output> s3 = injector.getInstance(S3LiveStateGenerator.class);

    return s3.generate();
  }

  private static void logProvisioningChanges(List<StepResult<S3Output>> changed,
                                             List<StepResult<S3Output>> currentState) {

    if (changed == null || changed.isEmpty()) {
      log.info("No changes detected. Skipping provisioning");
    } else {
      log.info("Differences detected in steps: {}", changed.stream().map(StepResult::getStepName).toList());
      logDifferences(currentState, changed);
    }
  }


  private static void verifyModificationRestrictions(
      List<StepResult<S3Output>> changes,
      List<StepResult<S3Output>> referenceState
  ) throws CloudProvisioningTerminationException {

    for (StepResult<S3Output> change : changes) {
      String stepName = change.getStepName();
      try {
        Class<?> stepClass = Class.forName(stepName);
        if (stepClass.isAnnotationPresent(PreventModification.class) && !change.isVoid()) {

          StepResult<S3Output> referenceResult = referenceState.stream()
              .filter(ref -> stepName.equals(ref.getStepName()))
              .findFirst()
              .orElse(null);

          if (referenceResult != null && !referenceResult.isVoid())  {
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

  private static List<CloudProvisionStep<S3Output>> resolveStepsToProvision(
      List<CloudProvisionStep<S3Output>> allSteps,
      List<StepResult<S3Output>> changes
  ) {

    Set<String> changedStepNames = changes.stream()
        .map(StepResult::getStepName)
        .collect(Collectors.toSet());

    return allSteps.stream()
        .filter(step -> changedStepNames.contains(step.getClass().getName()))
        .collect(Collectors.toCollection(ArrayList::new));
  }

  private static List<CloudProvisionStep<S3Output>> resolveStepsToDelete(
      List<CloudProvisionStep<S3Output>> allSteps,
      List<StepResult<S3Output>> changes
  ) {

    Set<String> stepNamesToDelete = changes.stream()
        .filter(StepResult::isVoid)
        .map(StepResult::getStepName)
        .collect(Collectors.toSet());

    return allSteps.stream()
        .filter(step -> stepNamesToDelete.contains(step.getClass().getName()))
        .toList();
  }

  private static boolean isMainResourceDeleted(CloudProvisionerSuccessfulResponse<S3Output> destroyed,
                                               String profile, String bucket, String id) {

    for (StepResult<S3Output> destroyedResource : destroyed.getResults()) {

      try {

        Class<?> stepClass = Class.forName(destroyedResource.getStepName());
        if (stepClass.isAnnotationPresent(MainResource.class)) {

          log.info("Main resource '{}' marked for deletion", stepClass.getSimpleName());
          File file = new File(".cloudprovisioner/" + profile + "/state-" + bucket + "#" + id + ".json");
          deleteFileIfExists(file, "Deleted state file for destroyed bucket: {}", "Failed to execute state file: {}");

          return true;
        }
      } catch (ClassNotFoundException e) {
        log.warn("Step class '{}' not found during main resource check", destroyedResource.getStepName(), e);
      }
    }

    return false;
  }

  private static List<StepResult<S3Output>> mergeStates(
      List<StepResult<S3Output>> originState,
      List<StepResult<S3Output>> resultUpdates
  ) {

    Map<String, StepResult<S3Output>> mergedMap = new LinkedHashMap<>();
    originState.forEach(r -> mergedMap.put(r.getStepName(), r));
    resultUpdates.forEach(r -> mergedMap.put(r.getStepName(), r));

    return new ArrayList<>(mergedMap.values());
  }

  private static void mergeAndPersistState(
      String profile,
      String bucket,
      String id,
      CloudProvisionerSuccessfulResponse<S3Output> response,
      List<StepResult<S3Output>> originState
  ) {
    List<StepResult<S3Output>> mergedList = mergeStates(originState, response.getResults());
    response.setResults(mergedList);

    StepResultStateWriter<S3Output> writer = COMMON_INJECTOR.getInstance(Key.get(new TypeLiteral<>() {}));
    File targetFile = new File(".cloudprovisioner/" + profile + "/state-" + bucket + "#" + id + ".json");
    try {
      writer.write(targetFile, mergedList);
    } catch (StepResultStateWriteException e) {
      log.warn("Failed to persist step results for bucket '{}'. "
          + "Provisioning changes were applied, but state was not saved. Reason: {}", bucket, e.getMessage());
    }
  }

  private static CloudProvisionerSuccessfulResponse<S3Output> applyChanges(
      String profile,
      String bucket,
      String id,
      Injector injector,
      List<StepResult<S3Output>> changes,
      List<StepResult<S3Output>> referenceState,
      List<StepResult<S3Output>> originState
  ) throws CloudProvisioningTerminationException {

    // Get all available steps from Guice
    // TODO [Implementation] Think how to use the injector
    List<CloudProvisionStep<S3Output>> allSteps =
        injector.getInstance(Key.get(new TypeLiteral<>() {}, Names.named("s3Steps")));

    // Validate if changes are allowed
    verifyModificationRestrictions(changes, referenceState);

    // Determine which steps to apply
    List<CloudProvisionStep<S3Output>> stepsToProvision = resolveStepsToProvision(allSteps, changes);

    // Determine which steps to execute
    List<CloudProvisionStep<S3Output>> stepsToDelete = resolveStepsToDelete(allSteps, changes);

    CloudProvisionerSuccessfulResponse<S3Output> destroyed = destroyS3(injector, stepsToDelete, true);

    if (isMainResourceDeleted(destroyed, profile, bucket, id)) {
      return destroyed;
    }

    CloudProvisionerSuccessfulResponse<S3Output> response = provisionS3(injector, stepsToProvision);
    mergeAndPersistState(profile, bucket, id, response, originState);

    return response;
  }

  @SuppressWarnings("UnusedReturnValue")
  private static CloudProvisionerSuccessfulResponse<S3Output> revertChanges(
      String profile,
      String bucket,
      String id,
      Injector injector,
      List<StepResult<S3Output>> changes,
      List<StepResult<S3Output>> originState
  ) throws CloudProvisioningTerminationException {

    // Get all available steps from Guice
    List<CloudProvisionStep<S3Output>> allSteps =
        injector.getInstance(Key.get(new TypeLiteral<>() {}, Names.named("s3Steps")));

    // Determine which steps to apply
    List<CloudProvisionStep<S3Output>> stepsToProvision = resolveStepsToProvision(allSteps, changes);

    // Determine which steps to execute
    List<CloudProvisionStep<S3Output>> stepsToDelete = resolveStepsToDelete(allSteps, changes);

    CloudProvisionerSuccessfulResponse<S3Output> destroyed = destroyS3(injector, stepsToDelete, false);

    if (isMainResourceDeleted(destroyed, profile, bucket, id)) {
      return destroyed;
    }

    CloudProvisionerSuccessfulResponse<S3Output> response = revertS3(injector, stepsToProvision, originState);
    mergeAndPersistState(profile, bucket, id, response, originState);

    return response;
  }

  private static CloudProvisionerSuccessfulResponse<S3Output> provisionS3(Injector injector,
                                                                          List<CloudProvisionStep<S3Output>> resources)
      throws CloudProvisioningTerminationException {

    if (resources.isEmpty()) {
      log.info("Nothing to provision. Infrastructure is up to date.");
    }

    CloudResourceProvisioner<S3Output> provisioner = injector.getInstance(S3BucketProvisioner.class);

    return provisioner.provision(resources);
  }

  private static CloudProvisionerSuccessfulResponse<S3Output> revertS3(Injector injector,
                                                                       List<CloudProvisionStep<S3Output>> resources,
                                                                       List<StepResult<S3Output>> previous)
      throws CloudProvisioningTerminationException {

    if (resources.isEmpty()) {
      log.info("No changes detected. Skipping revert operation "
          + "as all S3 resources are already in their previous state.");
    }

    CloudResourceReverter<S3Output> reverter = injector.getInstance(S3BucketReverter.class);

    return reverter.revert(resources, previous);
  }

  private static CloudProvisionerSuccessfulResponse<S3Output> destroyS3(Injector injector,
                                                                        List<CloudProvisionStep<S3Output>> resources,
                                                                        boolean enforcePreventDestroy)
      throws CloudProvisioningTerminationException {

    if (resources.isEmpty()) {
      log.info("Nothing to destroy. Continuing with provisioning phase.");
    }

    S3BucketDestroyer destroyer = injector.getInstance(S3BucketDestroyer.class);

    return destroyer.destroy(resources, enforcePreventDestroy);
  }

  private static void logDifferences(List<StepResult<S3Output>> loaded, List<StepResult<S3Output>> changed) {

    Map<String, StepResult<S3Output>> loadedMap = loaded.stream()
        .collect(Collectors.toMap(StepResult::getStepName, r -> r));

    Map<String, StepResult<S3Output>> changedMap = changed.stream()
        .collect(Collectors.toMap(StepResult::getStepName, r -> r));

    for (String stepName : changedMap.keySet()) {
      StepResult<S3Output> newResult = changedMap.get(stepName);
      StepResult<S3Output> oldResult = loadedMap.get(stepName);

      if (oldResult == null) {
        log.info("🆕 Step '{}' is new:\n{}", stepName, formatOutputs(newResult.getOutputs()));
        continue;
      }

      Map<S3Output, Object> oldOutputs = oldResult.getOutputs();
      Map<S3Output, Object> newOutputs = newResult.getOutputs();

      if (!oldOutputs.isEmpty() && newOutputs.isEmpty()) {
        log.info("🗑 Step '{}' has been cleared and will be deleted.", stepName);
        continue;
      }

      List<String> diffs = new ArrayList<>();
      for (Map.Entry<S3Output, Object> entry : newOutputs.entrySet()) {
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

  private static String formatOutputs(Map<S3Output, Object> outputs) {

    return outputs.entrySet().stream()
        .map(e -> "  • " + e.getKey() + ": " + e.getValue())
        .collect(Collectors.joining("\n"));
  }

  // TODO [Implementation] Print resource name
  private static void logError(ErrorCode code, Exception e) {

    CloudProvisionerFailureResponse error = new CloudProvisionerFailureResponse(code);

    error.getDetails().add(e.getMessage());
    if (e.getCause() instanceof CloudResourceStepException stepException) {
      error.getDetails().addAll(stepException.getMessageDetails());
    }
    if (e.getCause() instanceof ExecutionException executionException) {
      if (executionException.getCause() instanceof CloudResourceStepException stepException) {
        error.getDetails().addAll(stepException.getMessageDetails());
      }
    }
    if (e.getCause() instanceof JsonMappingException jsonException) {
      error.getDetails().add(jsonException.getMessage());
    }

    if (isJsonLoggingEnabled()) {
      log.error(code.getMessage(), keyValue("error", error));
    } else {
      try {
        log.error("{}\n{}", code.getMessage(), YAML_MAPPER.writeValueAsString(error));
      } catch (JsonProcessingException ex) {
        log.debug("Failed to serialize error details to YAML format.", ex);
      }
    }
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

  // TODO [Maybe] Not needed for now. Maybe use with 'export' job ?
  //  private static void cleanupStateFiles(String bucket) {
  //    String[] prefixes = {"current-", "desired-"};
  //    for (String prefix : prefixes) {
  //      File file = new File(".cloudprovisioner/" + prefix + bucket + ".json");
  //      deleteFileIfExists(file, "Deleted state file: {}", "Failed to execute state file: {}");
  //    }
  //  }

}
