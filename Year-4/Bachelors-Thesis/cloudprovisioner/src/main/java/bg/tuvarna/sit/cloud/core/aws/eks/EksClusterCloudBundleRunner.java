package bg.tuvarna.sit.cloud.core.aws.eks;

import bg.tuvarna.sit.cloud.core.aws.eks.config.EksClusterConfig;
import bg.tuvarna.sit.cloud.core.aws.eks.config.EksClusterConfigList;
import bg.tuvarna.sit.cloud.core.aws.eks.state.EksClusterDesiredStateGenerator;
import bg.tuvarna.sit.cloud.core.aws.eks.state.EksClusterLiveStateGenerator;
import bg.tuvarna.sit.cloud.core.aws.eks.state.EksClusterStateComparator;
import bg.tuvarna.sit.cloud.core.aws.eks.state.EksClusterStoredStateLoader;
import bg.tuvarna.sit.cloud.core.aws.eks.step.EksClusterPersistentMetadataStep;
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

@Singleton
@Slf4j
public class EksClusterCloudBundleRunner extends CloudBundleRunner<EksClusterOutput> {

  @Inject
  public EksClusterCloudBundleRunner(@Named(NamedInjections.YAML_MAPPER) ObjectMapper yaml,
                                     EksClusterStoredStateLoader storedState,
                                     EksClusterStateComparator stateComparator,
                                     ConfigurationUtil config,
                                     StepResultStateWriter<EksClusterOutput> stateWriter,
                                     Slf4jLoggingUtil loggingUtil) {

    super(log, yaml, storedState, stateComparator, config, stateWriter, loggingUtil);
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
    if (!stateDir.exists() && !stateDir.mkdirs()) {
      throw new IllegalStateException("Failed to create directory: " + stateDir.getAbsolutePath());
    }

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

    executeProvisioningTasks(configList.getClusters(), configList.getFixedThreadPool().getMaxSize(),
        config -> () -> provisionEks(config, credentials, profile, stateDir), ErrorCode.EKS_PROVISION_ERROR);
  }

  private CloudProvisionerSuccessfulResponse<EksClusterOutput> provisionEks(EksClusterConfig config,
                                                                            AwsBasicCredentials credentials,
                                                                            String profile, File stateDir)
      throws CloudProvisioningTerminationException {

    return provisionGeneric(
        ProvisionRequest.<EksClusterOutput, EksClusterConfig>builder()
            .config(config)
            .profile(profile)
            .stateDir(stateDir)
            .metadataStepName(EksClusterPersistentMetadataStep.class.getName())
            .stepProvider(inj -> inj.getInstance(Key.get(new TypeLiteral<>() {},
                Names.named(NamedInjections.EKS_STEPS))))
            .destroyerProvider(inj -> inj.getInstance(EksClusterDestroyer.class))
            .provisionerProvider(inj -> inj.getInstance(EksClusterProvisioner.class))
            .reverterProvider(inj -> inj.getInstance(EksClusterReverter.class))
            .liveStateGenerator(inj -> generateLiveCloudState(inj.getInstance(EksClusterLiveStateGenerator.class)))
            .desiredStateGenerator(inj -> generateDesiredState(inj.getInstance(EksClusterDesiredStateGenerator.class)))
            .injectorFactory((region, metadata) -> Guice.createInjector(
                new EksClusterInjectionModule(
                    new EksClusterProvisioningContext(credentials, EnvVar.ENDPOINT_URL.getValue(), Region.of(region)),
                    config, metadata)))
            .metadataSupplier(cfg -> StepResult.<EksClusterOutput>builder()
                .stepName(EksClusterPersistentMetadataStep.class.getName())
                .put(EksClusterOutput.NAME, cfg.getName())
                .put(EksClusterOutput.REGION, cfg.getRegion().getValue())
                .put(EksClusterOutput.ARN, cfg.buildArn())
                .put(EksClusterOutput.PREVENT_DESTROY,
                    cfg.preventDestroy() != null ? cfg.preventDestroy() : Boolean.TRUE)
                .build())
            .nameExtractor(step -> (String) step.getOutputs().get(EksClusterOutput.NAME))
            .regionExtractor(step -> (String) step.getOutputs().get(EksClusterOutput.REGION))
            .outputClass(EksClusterOutput.class)
            .build()
    );
  }

}
