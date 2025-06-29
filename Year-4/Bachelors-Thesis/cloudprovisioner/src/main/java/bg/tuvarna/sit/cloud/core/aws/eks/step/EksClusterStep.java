package bg.tuvarna.sit.cloud.core.aws.eks.step;

import bg.tuvarna.sit.cloud.core.aws.eks.EksClusterOutput;
import bg.tuvarna.sit.cloud.core.aws.eks.client.EksSafeClient;
import bg.tuvarna.sit.cloud.core.aws.eks.config.EksClusterConfig;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterAuthenticationMode;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterSupportType;
import bg.tuvarna.sit.cloud.core.aws.eks.model.SubnetIdSet;
import bg.tuvarna.sit.cloud.core.aws.eks.step.base.EksClusterProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.MainResource;
import bg.tuvarna.sit.cloud.core.provisioner.PreventModification;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionOrder;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import lombok.extern.slf4j.Slf4j;

import software.amazon.awssdk.services.eks.model.Cluster;
import software.amazon.awssdk.services.eks.model.DescribeClusterResponse;
import software.amazon.awssdk.services.eks.model.ResourceNotFoundException;

import java.time.Duration;

@Slf4j
@MainResource
@ProvisionOrder(1)
@PreventModification
@Singleton
public class EksClusterStep extends EksClusterProvisionStep {

  private final StepResult<EksClusterOutput> metadata;

  @Inject
  protected EksClusterStep(EksSafeClient eks, EksClusterConfig config, StepResult<EksClusterOutput> metadata) {
    super(eks, config);
    this.metadata = metadata;
  }

  @Override
  public StepResult<EksClusterOutput> apply() {

    String step = this.getClass().getName();
    String clusterName = config.getName();

    eks.create(config);
    log.info("Successfully created EKS cluster '{}'", clusterName);

    eks.waitUntilActive(clusterName, Duration.ofMinutes(10));

    Cluster cluster = eks.describe(clusterName).cluster();
    log.info("Successfully verified existence of EKS cluster '{}'", clusterName);

    return StepResult.<EksClusterOutput>builder()
        .stepName(step)
        .put(EksClusterOutput.NAME, cluster.name())
        .put(EksClusterOutput.REGION, cluster.arn().split(":")[3])
        // TODO [Implementation] Think for handling upcoming properties
        // .put(EksClusterOutput.STATUS, cluster.statusAsString())
        .put(EksClusterOutput.VERSION, cluster.version())
        .put(EksClusterOutput.ROLE_ARN, cluster.roleArn())
        .put(EksClusterOutput.SUBNETS, new SubnetIdSet(cluster.resourcesVpcConfig().subnetIds()))
        .put(EksClusterOutput.CLUSTER_CREATOR_ADMIN_PERMISSIONS, cluster.accessConfig().bootstrapClusterCreatorAdminPermissions())
        //.put(EksClusterOutput.SELF_MANAGED_ADDONS, config.isBootstrapSelfManagedAddons())
        .put(EksClusterOutput.AUTHENTICATION_MODE, cluster.accessConfig().authenticationMode().toString())
        .put(EksClusterOutput.SUPPORT_TYPE, cluster.upgradePolicy().supportType().toString())
        .put(EksClusterOutput.ZONAL_SHIFT_ENABLED, cluster.zonalShiftConfig().enabled())
        .put(EksClusterOutput.OWNED_KMS_KEY_ID,
            !cluster.encryptionConfig().isEmpty() ?
                cluster.encryptionConfig().getFirst().provider().keyArn() : null)
        .build();
  }

  @Override
  public StepResult<EksClusterOutput> generateDesiredState() {

    String step = this.getClass().getName();

    if (config.isToDelete()) {
      return StepResult.<EksClusterOutput>builder()
          .stepName(step)
          .build();
    }

    StepResult.Builder<EksClusterOutput> builder = StepResult.<EksClusterOutput>builder()
        .stepName(step)
        .put(EksClusterOutput.NAME, config.getName())
        .put(EksClusterOutput.REGION, config.getRegion().getValue())
        .put(EksClusterOutput.VERSION, config.getVersion())
        .put(EksClusterOutput.ROLE_ARN, config.getRoleArn())
        .put(EksClusterOutput.SUBNETS, new SubnetIdSet(config.getSubnets()))
        .put(EksClusterOutput.CLUSTER_CREATOR_ADMIN_PERMISSIONS, config.isBootstrapClusterCreatorAdminPermissions())
        .put(EksClusterOutput.AUTHENTICATION_MODE, config.getAuthenticationMode().getValue())
        .put(EksClusterOutput.SUPPORT_TYPE, config.getSupportType().getValue())
        .put(EksClusterOutput.ZONAL_SHIFT_ENABLED, config.isEnableZonalShift())
        .put(EksClusterOutput.SELF_MANAGED_ADDONS, config.isBootstrapSelfManagedAddons())
        .put(EksClusterOutput.OWNED_KMS_KEY_ID, config.getOwnedEncryptionKMSKeyArn());

    return builder.build();
  }

  @Override
  public StepResult<EksClusterOutput> getCurrentState() {

    String resource = (String) metadata.getOutputs().get(EksClusterOutput.NAME);

    StepResult.Builder<EksClusterOutput> builder =
        StepResult.<EksClusterOutput>builder().stepName(this.getClass().getName());

    try {
      DescribeClusterResponse response = eks.describe(resource);
      Cluster cluster = response.cluster();
      log.info("Successfully fetched details for EKS cluster '{}'", resource);

      // TODO [Implementation] Think for handling upcoming properties
      return builder
          .put(EksClusterOutput.NAME, cluster.name())
          .put(EksClusterOutput.REGION, cluster.arn().split(":")[3])
          .put(EksClusterOutput.VERSION, cluster.version())
          .put(EksClusterOutput.ROLE_ARN, cluster.roleArn())
          .put(EksClusterOutput.SUBNETS, new SubnetIdSet(cluster.resourcesVpcConfig().subnetIds()))
          .put(EksClusterOutput.CLUSTER_CREATOR_ADMIN_PERMISSIONS, cluster.accessConfig().bootstrapClusterCreatorAdminPermissions())
          .put(EksClusterOutput.AUTHENTICATION_MODE, cluster.accessConfig().authenticationMode().toString())
          .put(EksClusterOutput.SUPPORT_TYPE, cluster.upgradePolicy().supportType().toString())
          .put(EksClusterOutput.ZONAL_SHIFT_ENABLED, cluster.zonalShiftConfig().enabled())
          .put(EksClusterOutput.OWNED_KMS_KEY_ID,
              !cluster.encryptionConfig().isEmpty() ?
              cluster.encryptionConfig().getFirst().provider().keyArn() : null)
          // TODO [Maybe] move to metadata
          // .put(EksClusterOutput.CLUSTER_CREATOR_ADMIN_PERMISSIONS, config.isBootstrapClusterCreatorAdminPermissions())
          // TODO [Maybe] move to metadata
          // .put(EksClusterOutput.SELF_MANAGED_ADDONS, config.isBootstrapSelfManagedAddons())
          .build();

    } catch (CloudResourceStepException e) {
      if (e.getCause() instanceof ResourceNotFoundException) {
        return builder.build();
      }
      throw e;
    }
  }

  @Override
  public StepResult<EksClusterOutput> destroy(boolean enforcePreventDestroy) {

    String clusterName = (String) metadata.getOutputs().get(EksClusterOutput.NAME);
    Boolean preventDestroy = (Boolean) metadata.getOutputs().getOrDefault(EksClusterOutput.PREVENT_DESTROY, false);

    if (enforcePreventDestroy && Boolean.TRUE.equals(preventDestroy)) {
      String msg = "Destruction of EKS cluster '%s' is prevented by configuration.".formatted(clusterName);
      log.warn(msg);
      throw new CloudResourceStepException(msg);
    }

    eks.delete(clusterName);
    log.info("Deleting EKS cluster '{}'...", clusterName);

    eks.waitUntilDeleted(clusterName, Duration.ofMinutes(10));

    return StepResult.<EksClusterOutput>builder()
        .stepName(this.getClass().getName())
        .build();
  }

  @Override
  public StepResult<EksClusterOutput> revert(StepResult<EksClusterOutput> previous) {

    String step = this.getClass().getName();
    String clusterName = (String) metadata.getOutputs().get(EksClusterOutput.NAME);

    String previousClusterName = (String) previous.getOutputs().get(EksClusterOutput.NAME);

    // Cluster existed — check if config changed and update if needed
    EksClusterConfig previousConfig = new EksClusterConfig();

    previousConfig.setName(previousClusterName);
    previousConfig.setVersion((String) previous.getOutputs().get(EksClusterOutput.VERSION));
    previousConfig.setSubnets(((SubnetIdSet) previous.getOutputs().get(EksClusterOutput.SUBNETS)).getSubnetIds());
    previousConfig.setAuthenticationMode(EksClusterAuthenticationMode.fromValue((String) previous.getOutputs().get(EksClusterOutput.AUTHENTICATION_MODE)));
    previousConfig.setSupportType(EksClusterSupportType.fromValue((String) previous.getOutputs().get(EksClusterOutput.SUPPORT_TYPE)));
    previousConfig.setEnableZonalShift((Boolean) previous.getOutputs().get(EksClusterOutput.ZONAL_SHIFT_ENABLED));

    if (!this.config.equals(previousConfig)) {
      log.info("Cluster '{}' has changed — reverting configuration...", clusterName);
      eks.updateCluster(clusterName, previousConfig);
      eks.waitUntilActive(clusterName, Duration.ofMinutes(10));
    } else {
      log.info("Cluster '{}' is unchanged — skipping update.", clusterName);
      return previous;
    }

    Cluster cluster = eks.describe(clusterName).cluster();
    log.info("Successfully reverted EKS cluster '{}' configuration to previous state", clusterName);

    return StepResult.<EksClusterOutput>builder()
        .stepName(step)
        .put(EksClusterOutput.NAME, cluster.name())
        .put(EksClusterOutput.REGION, cluster.arn().split(":")[3])
        .put(EksClusterOutput.VERSION, cluster.version())
        .put(EksClusterOutput.ROLE_ARN, cluster.roleArn())
        .put(EksClusterOutput.SUBNETS, new SubnetIdSet(cluster.resourcesVpcConfig().subnetIds()))
        .put(EksClusterOutput.CLUSTER_CREATOR_ADMIN_PERMISSIONS, cluster.accessConfig().bootstrapClusterCreatorAdminPermissions())
        .put(EksClusterOutput.SELF_MANAGED_ADDONS, config.isBootstrapSelfManagedAddons())
        .put(EksClusterOutput.AUTHENTICATION_MODE, cluster.accessConfig().authenticationMode().toString())
        .put(EksClusterOutput.SUPPORT_TYPE, cluster.upgradePolicy().supportType().toString())
        .put(EksClusterOutput.ZONAL_SHIFT_ENABLED, cluster.zonalShiftConfig().enabled())
        .put(EksClusterOutput.OWNED_KMS_KEY_ID,
            !cluster.encryptionConfig().isEmpty() ?
                cluster.encryptionConfig().getFirst().provider().keyArn() : null)
        .build();
  }

}

