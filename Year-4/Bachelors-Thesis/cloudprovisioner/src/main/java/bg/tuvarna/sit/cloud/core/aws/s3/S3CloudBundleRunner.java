package bg.tuvarna.sit.cloud.core.aws.s3;

import bg.tuvarna.sit.cloud.core.aws.s3.config.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.config.S3BucketConfigList;
import bg.tuvarna.sit.cloud.core.aws.s3.state.S3DesiredStateGenerator;
import bg.tuvarna.sit.cloud.core.aws.s3.state.S3LiveStateGenerator;
import bg.tuvarna.sit.cloud.core.aws.s3.state.S3StateComparator;
import bg.tuvarna.sit.cloud.core.aws.s3.state.S3StoredStateLoader;
import bg.tuvarna.sit.cloud.core.aws.s3.step.S3PersistentMetadataStep;
import bg.tuvarna.sit.cloud.core.provisioner.CloudBundleRunner;
import bg.tuvarna.sit.cloud.core.provisioner.model.CloudProvisionerSuccessfulResponse;
import bg.tuvarna.sit.cloud.core.provisioner.model.CloudResourceType;
import bg.tuvarna.sit.cloud.core.provisioner.model.ErrorCode;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.utils.StepResultStateWriter;
import bg.tuvarna.sit.cloud.exception.CloudProvisioningTerminationException;
import bg.tuvarna.sit.cloud.exception.ConfigurationLoadException;
import bg.tuvarna.sit.cloud.utils.ConfigurationUtil;
import bg.tuvarna.sit.cloud.utils.EnvVar;
import bg.tuvarna.sit.cloud.utils.NamedInjections;
import bg.tuvarna.sit.cloud.utils.Slf4jLoggingUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.inject.Guice;
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
import java.util.Set;
import java.util.stream.Collectors;

// TODO [Idea] Readability of objects and methods (names)
@Singleton
@Slf4j
public class S3CloudBundleRunner extends CloudBundleRunner<S3Output> {

  @Inject
  public S3CloudBundleRunner(@Named(NamedInjections.YAML_MAPPER) ObjectMapper yaml, S3StoredStateLoader storedState,
                             S3StateComparator stateComparator, StepResultStateWriter<S3Output> stateWriter,
                             ConfigurationUtil config, Slf4jLoggingUtil loggingUtil) {

    super(log, yaml, storedState, stateComparator, config, stateWriter, loggingUtil);
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

    String path = getServiceConfigurationPath();
    log.info("Loading S3 config from: {}", path);
    S3BucketConfigList configList;
    try {
      configList = loadConfig(path, S3BucketConfigList.class);
    } catch (ConfigurationLoadException e) {
      loggingUtil.logError(log, ErrorCode.S3_CONFIG_LOAD_ERROR, e);
      return;
    }

    Set<String> ids = configList.getBuckets().stream().map(S3BucketConfig::getId).collect(Collectors.toSet());

    String profile = EnvVar.AWS_PROFILE.getValue();
    File stateDir = new File(".cloudprovisioner/" + profile + "/" + getType().toString().toLowerCase());

    for (String stateKey : findObsoleteStateFiles(stateDir, ids)) {

      String[] parts = stateKey.split("#");
      S3BucketConfig delete = new S3BucketConfig();
      delete.setName(parts[0]);
      delete.setId(parts[1].replace(".json", ""));
      delete.setToDelete(true);
      delete.setPreventDestroy(null);
      configList.getBuckets().add(delete);

      log.info("Bucket with configKey '{}' is removed from config and is marked for deletion", stateKey);
    }

    executeProvisioningTasks(configList.getBuckets(), configList.getFixedThreadPool().getMaxSize(),
        config -> () -> provisionS3(config, credentials, profile, stateDir), ErrorCode.S3_PROVISION_ERROR);
  }

  private CloudProvisionerSuccessfulResponse<S3Output> provisionS3(S3BucketConfig config,
                                                                   AwsBasicCredentials credentials, String profile,
                                                                   File stateDir)
      throws CloudProvisioningTerminationException {

    return provisionGeneric(
        ProvisionRequest.<S3Output, S3BucketConfig>builder()
            .config(config)
            .profile(profile)
            .stateDir(stateDir)
            .resourceKey(getType().getValue())
            .metadataStepName(S3PersistentMetadataStep.class.getName())
            .stepProvider(inj -> inj.getInstance(Key.get(new TypeLiteral<>() {
            }, Names.named(NamedInjections.S3_STEPS))))
            .destroyerProvider(inj -> inj.getInstance(S3BucketDestroyer.class))
            .provisionerProvider(inj -> inj.getInstance(S3BucketProvisioner.class))
            .reverterProvider(inj -> inj.getInstance(S3BucketReverter.class))
            .liveStateGenerator(inj -> generateLiveCloudState(inj.getInstance(S3LiveStateGenerator.class)))
            .desiredStateGenerator(inj -> generateDesiredState(inj.getInstance(S3DesiredStateGenerator.class)))
            .injectorFactory((region, metadata) -> Guice.createInjector(new S3InjectionModule(
                new S3ProvisioningContext(credentials, EnvVar.S3_ENDPOINT_URL.getValue(), Region.of(region)), config,
                metadata)))
            .metadataSupplier(cfg -> StepResult.<S3Output>builder()
                .stepName(S3PersistentMetadataStep.class.getName())
                .put(S3Output.NAME, cfg.getName())
                .put(S3Output.REGION, cfg.getRegion().getValue())
                .put(S3Output.ARN, cfg.buildArn())
                .put(S3Output.PREVENT_DESTROY, cfg.preventDestroy() != null ? cfg.preventDestroy() : Boolean.TRUE)
                .build())
            .nameExtractor(step -> (String) step.getOutputs().get(S3Output.NAME))
            .regionExtractor(step -> (String) step.getOutputs().get(S3Output.REGION))
            .outputClass(S3Output.class)
            .build()
    );
  }
}
