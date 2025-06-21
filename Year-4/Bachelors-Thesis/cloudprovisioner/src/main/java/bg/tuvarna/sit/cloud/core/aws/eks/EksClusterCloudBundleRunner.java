package bg.tuvarna.sit.cloud.core.aws.eks;

import bg.tuvarna.sit.cloud.core.aws.eks.config.EksClusterConfig;
import bg.tuvarna.sit.cloud.core.aws.eks.config.EksClusterConfigList;
import bg.tuvarna.sit.cloud.core.aws.eks.state.EksClusterDesiredStateGenerator;
import bg.tuvarna.sit.cloud.core.aws.eks.state.EksClusterLiveStateGenerator;
import bg.tuvarna.sit.cloud.core.aws.eks.state.EksClusterStateComparator;
import bg.tuvarna.sit.cloud.core.aws.eks.state.EksClusterStoredStateLoader;
import bg.tuvarna.sit.cloud.core.aws.eks.step.EksClusterPersistentMetadataStep;
import bg.tuvarna.sit.cloud.core.aws.eks.step.EksClusterStep;
import bg.tuvarna.sit.cloud.core.provisioner.CloudBundleRunner;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionerSuccessfulResponse;
import bg.tuvarna.sit.cloud.core.provisioner.CloudResourceDestroyer;
import bg.tuvarna.sit.cloud.core.provisioner.CloudResourceProvisioner;
import bg.tuvarna.sit.cloud.core.provisioner.CloudResourceReverter;
import bg.tuvarna.sit.cloud.core.provisioner.CloudResourceType;
import bg.tuvarna.sit.cloud.core.provisioner.ErrorCode;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import bg.tuvarna.sit.cloud.core.provisioner.StepResultStateWriter;
import bg.tuvarna.sit.cloud.exception.CloudProvisioningTerminationException;
import bg.tuvarna.sit.cloud.exception.ConfigurationLoadException;
import bg.tuvarna.sit.cloud.utils.ConfigurationUtil;
import bg.tuvarna.sit.cloud.utils.EnvVar;
import bg.tuvarna.sit.cloud.utils.Slf4jLoggingUtil;

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
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
@Slf4j
public class EksClusterCloudBundleRunner extends CloudBundleRunner<EksClusterOutput> {

  @Inject
  public EksClusterCloudBundleRunner(@Named("yamlMapper") ObjectMapper yaml,
                                     EksClusterStoredStateLoader storedState,
                                     EksClusterStateComparator stateComparator,
                                     ConfigurationUtil config,
                                     StepResultStateWriter<EksClusterOutput> stateWriter,
                                     Slf4jLoggingUtil loggingUtil) {

    super(yaml, storedState, stateComparator, config, stateWriter, loggingUtil);
  }

  @Override
  protected CloudResourceType getType() {

    return CloudResourceType.EKS;
  }

  @Override
  protected String getServiceConfigurationPath() {

    return "src/main/resources/cloud/%s/eks.yml".formatted(EnvVar.AWS_PROFILE.getValue());
  }

  @Override
  public void run(AwsBasicCredentials credentials) {

    String path = getServiceConfigurationPath();
    log.info("Loading EKS config from: {}", path);
    EksClusterConfigList configList;
    try {
      configList = loadConfig(path, EksClusterConfigList.class);
    } catch (ConfigurationLoadException e) {
      loggingUtil.logError(log, ErrorCode.EKS_CONFIG_LOAD_ERROR, e);
      return;
    }

    Set<String> ids = configList.getClusters().stream().map(EksClusterConfig::getId).collect(Collectors.toSet());

    String profile = EnvVar.AWS_PROFILE.getValue();
    File stateDir = new File(".cloudprovisioner/" + profile + "/" + getType().toString().toLowerCase());

    for (String stateKey : findObsoleteStateFiles(stateDir, ids)) {

      String[] parts = stateKey.split("#");
      EksClusterConfig delete = new EksClusterConfig();
      delete.setName(parts[0]);
      delete.setId(parts[1].replace(".json", ""));
      delete.setToDelete(true);
      delete.setPreventDestroy(null);
      configList.getClusters().add(delete);

      log.info("Cluster with configKey '{}' is removed from config and is marked for deletion", stateKey);
    }

    executeProvisioningTasks(configList.getClusters(),
        config -> () -> provisionEks(config, credentials, profile, stateDir), ErrorCode.EKS_PROVISION_ERROR);
  }

  private CloudProvisionerSuccessfulResponse<EksClusterOutput> provisionEks(EksClusterConfig config,
                                                                            AwsBasicCredentials credentials,
                                                                            String profile, File stateDir)
      throws CloudProvisioningTerminationException {

    return provisionGeneric(
        config,
        profile,
        stateDir,
        "CLUSTER",
        EksClusterPersistentMetadataStep.class.getName(),
        inj -> inj.getInstance(Key.get(new TypeLiteral<>() {
        }, Names.named("eksSteps"))),
        inj -> inj.getInstance(EksClusterDestroyer.class),
        inj -> inj.getInstance(EksClusterProvisioner.class),
        inj -> inj.getInstance(EksClusterReverter.class),
        inj -> generateLiveCloudState(inj.getInstance(EksClusterLiveStateGenerator.class)),
        inj -> generateDesiredState(inj.getInstance(EksClusterDesiredStateGenerator.class)),
        (region, metadata) -> Guice.createInjector(new EksClusterInjectionModule(
            new EksClusterProvisioningContext(credentials, EnvVar.ENDPOINT_URL.getValue(), Region.of(region)), config, metadata)),
        baseConfig -> StepResult.<EksClusterOutput>builder()
            .stepName(EksClusterPersistentMetadataStep.class.getName())
            .put(EksClusterOutput.NAME, baseConfig.getName())
            .put(EksClusterOutput.REGION, baseConfig.getRegion())
            .put(EksClusterOutput.ARN, baseConfig.buildArn())
            .put(EksClusterOutput.PREVENT_DESTROY, baseConfig.preventDestroy() != null ? baseConfig.preventDestroy() : Boolean.TRUE)
            .build(),
        step -> (String) step.getOutputs().get(EksClusterOutput.NAME),
        step -> (String) step.getOutputs().get(EksClusterOutput.REGION),
        EksClusterOutput.class
    );


//
//    log.info("Loading the last persisted infrastructure state from the local library...");
//
//    List<StepResult<EksClusterOutput>> loadedState = new ArrayList<>();
//    try {
//      // TODO [Implementation] On missing folder
//      File[] matchingFiles = Optional.ofNullable(stateDir.listFiles((dir, name) ->
//              name.startsWith("state-") && name.endsWith("#" + config.getId() + ".json")))
//          .orElse(new File[0]);
//
//      if (matchingFiles.length > 1) {
//        String message = "Found multiple state files matching id '%s': count=%d"
//            .formatted(config.getId(), matchingFiles.length);
//        log.error(message);
//        throw new ConfigurationLoadException(message);
//      } else if (matchingFiles.length == 0) {
//        throw new FileNotFoundException("No state file found for identifier: " + config.getId());
//      }
//
//      File stateFile = matchingFiles[0];
//      loadedState = storedState.load(stateFile, EksClusterOutput.class);
//    } catch (FileNotFoundException ex) {
//      log.info("Cluster '{}' is not yet provisioned. Will be created from scratch", config.getName());
//    } catch (IOException ex) {
//      loggingUtil.logError(log, ErrorCode.S3_STORED_STATE_ERROR, ex);
//      String message = "Failed to load persisted state for cluster '%s'. File might be unreadable or corrupted."
//          .formatted(config.getName());
//      throw new CloudProvisioningTerminationException(message, ex);
//    }
//
//    StepResult<EksClusterOutput> metadata = loadedState.stream()
//        .filter(result -> result.getStepName().equals(EksClusterPersistentMetadataStep.class.getName()))
//        .findFirst()
//        .orElse(StepResult.<EksClusterOutput>builder().stepName(EksClusterPersistentMetadataStep.class.getName())
//            .put(EksClusterOutput.NAME, config.getName())
//            .put(EksClusterOutput.REGION, config.getRegion())
//            .put(EksClusterOutput.ARN, config.buildArn())
//            .put(EksClusterOutput.PREVENT_DESTROY, config.preventDestroy() != null ? config.preventDestroy() : Boolean.TRUE)
//            .build());
//
//    String cluster = (String) metadata.getOutputs().get(EksClusterOutput.NAME);
//    String region = (String) metadata.getOutputs().get(EksClusterOutput.REGION);
//
//    // Required as config is passed to the steps and on deletion it would be empty otherwise
//    if (config.getRegion() == null) {
//      config.setRegion(region);
//    }
//
//    Injector injector = Guice.createInjector(new EksClusterInjectionModule(
//        new EksClusterProvisioningContext(credentials, EnvVar.ENDPOINT_URL.getValue(), Region.of(region)), config,
//        metadata));
//
//    List<CloudProvisionStep<EksClusterOutput>> allSteps =
//        injector.getInstance(Key.get(new TypeLiteral<>() {
//        }, Names.named("eksSteps")));
//
//    if (loadedState.isEmpty()) {
//      for (CloudProvisionStep<EksClusterOutput> step : allSteps) {
//        loadedState.add(step.getCurrentState());
//      }
//    }
//
//    List<StepResult<EksClusterOutput>> currentState;
//    try {
//      currentState = generateLiveCloudState(injector.getInstance(EksClusterLiveStateGenerator.class));
//      // TODO [Implementation] We don't need to write current and desired states while provisioning resources
//      // TODO [Maybe] Add option to export them if needed with separate 'export' job ?
//      //  StepResultStateWriter<EksClusterOutput> writer = COMMON_INJECTOR.getInstance(Key.get(new TypeLiteral<>() {}));
//      //  writer.write(new File(".cloudprovisioner/current-" + cluster + ".json"), currentState);
//    } catch (CloudProvisioningTerminationException e) {
//      loggingUtil.logError(log, ErrorCode.S3_LIVE_STATE_ERROR, e);
//      throw e;
//    }
//
//    List<StepResult<EksClusterOutput>> changed;
//    log.info("Comparing live state with the last persisted state to detect configuration drift...");
//
//    changed = stateComparator.diff(loadedState, currentState);
//    logProvisioningChanges(changed, currentState);
//
//    CloudResourceDestroyer<EksClusterOutput> destroyer = injector.getInstance(EksClusterDestroyer.class);
//    CloudResourceProvisioner<EksClusterOutput> provisioner = injector.getInstance(EksClusterProvisioner.class);
//    CloudProvisionerSuccessfulResponse<EksClusterOutput> applied;
//    try {
//      if (!config.isEnableReconciliation()) {
//        log.info("Reconciliation is disabled for cluster '{}'. Skipping provisioning changes", cluster);
//        changed = new ArrayList<>();
//      }
//      applied = applyChanges(profile, cluster, config.getId(), allSteps, destroyer, provisioner, changed, currentState,
//          loadedState);
//    } catch (CloudProvisioningTerminationException e) {
//      loggingUtil.logError(log, ErrorCode.S3_RECONCILIATION_ERROR, e);
//      // cleanupStateFiles(cluster);
//      throw e;
//    }
//
//    List<StepResult<EksClusterOutput>> desiredState;
//    try {
//      desiredState = generateDesiredState(injector.getInstance(EksClusterDesiredStateGenerator.class));
//      // TODO [Implementation] We don't need to write current and desired states while provisioning resources
//      // TODO [Maybe] Add option to export them if needed with separate 'export' job ?
//      //  StepResultStateWriter<EksClusterOutput> writer = COMMON_INJECTOR.getInstance(
//      //      Key.get(new TypeLiteral<>() {}));
//      //  writer.write(new File(".cloudprovisioner/desired-" + cluster + ".json"), desiredState);
//    } catch (CloudProvisioningTerminationException e) {
//      loggingUtil.logError(log, ErrorCode.S3_DESIRED_STATE_ERROR, e);
//      // cleanupStateFiles(cluster);
//      throw e;
//    }
//
//    log.info("Comparing desired configuration with the current live state to identify necessary changes...");
//    changed = stateComparator.diff(desiredState, loadedState);
//    logProvisioningChanges(changed, currentState);
//
//    try {
//      applied = applyChanges(profile, cluster, config.getId(), allSteps, destroyer, provisioner, changed,
//          applied.getResults(),
//          loadedState);
//    } catch (CloudProvisioningTerminationException e) {
//      // TODO [Documentation] transactional pattern. All steps have to succeed in order to see changes live
//      loggingUtil.logError(log, ErrorCode.S3_PROVISION_ERROR, e);
//      try {
//        log.info("Initiating rollback to the initial state to maintain transactional consistency...");
//        currentState = generateLiveCloudState(injector.getInstance(EksClusterLiveStateGenerator.class));
//        changed = stateComparator.diff(loadedState, currentState);
//        logProvisioningChanges(changed, currentState);
//        CloudResourceReverter<EksClusterOutput> reverter = injector.getInstance(EksClusterReverter.class);
//        revertChanges(profile, cluster, config.getId(), allSteps, destroyer, reverter, changed, loadedState);
//      } catch (CloudProvisioningTerminationException ex) {
//        loggingUtil.logError(log, ErrorCode.S3_PROVISION_ERROR, e);
//        // cleanupStateFiles(cluster);
//        throw ex;
//      }
//      log.warn("Provisioning failed for cluster '{}', but all changes were rolled back to the previously "
//          + "persisted state. No partial modifications remain.", cluster);
//      // cleanupStateFiles(cluster);
//      throw e;
//    }
//
//    return applied;
  }
}
