package bg.tuvarna.sit.cloud.core.aws.eks.step;

import bg.tuvarna.sit.cloud.core.aws.common.Region;
import bg.tuvarna.sit.cloud.core.aws.eks.EksClusterOutput;
import bg.tuvarna.sit.cloud.core.aws.eks.client.EksSafeClient;
import bg.tuvarna.sit.cloud.core.aws.eks.config.EksClusterConfig;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterAuthenticationMode;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterSupportType;
import bg.tuvarna.sit.cloud.core.aws.eks.model.SubnetIdSet;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.eks.model.AccessConfigResponse;
import software.amazon.awssdk.services.eks.model.AuthenticationMode;
import software.amazon.awssdk.services.eks.model.Cluster;
import software.amazon.awssdk.services.eks.model.DescribeClusterResponse;
import software.amazon.awssdk.services.eks.model.EncryptionConfig;
import software.amazon.awssdk.services.eks.model.Provider;
import software.amazon.awssdk.services.eks.model.ResourceNotFoundException;
import software.amazon.awssdk.services.eks.model.SupportType;
import software.amazon.awssdk.services.eks.model.UpgradePolicyResponse;
import software.amazon.awssdk.services.eks.model.VpcConfigResponse;
import software.amazon.awssdk.services.eks.model.ZonalShiftConfigResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EksClusterStepTest {

  @Mock
  private EksSafeClient eks;

  @Mock
  private EksClusterConfig config;

  @Mock
  private Cluster cluster;

  @Mock
  private VpcConfigResponse vpcConfig;

  @Mock
  private AccessConfigResponse accessConfig;

  @Mock
  private EncryptionConfig encryptionConfig;

  @Mock
  private Provider provider;

  @Mock
  private UpgradePolicyResponse upgradePolicy;

  @Mock
  private ZonalShiftConfigResponse zonalShiftConfig;

  private StepResult<EksClusterOutput> metadata;
  private EksClusterStep step;

  @BeforeEach
  void setup() {

    metadata = StepResult.<EksClusterOutput>builder()
        .stepName(EksClusterPersistentMetadataStep.class.getName())
        .put(EksClusterOutput.NAME, "test-cluster")
        .build();

    step = new EksClusterStep(eks, config, metadata);
  }

  @Test
  void apply_shouldCreateClusterAndReturnExpectedResult() {

    when(config.getName()).thenReturn("test-cluster");
    when(eks.describe("test-cluster")).thenReturn(DescribeClusterResponse.builder().cluster(cluster).build());
    when(cluster.name()).thenReturn("test-cluster");
    when(cluster.arn()).thenReturn("arn:aws:eks:eu-west-1:123456789012:cluster/test-cluster");
    when(cluster.version()).thenReturn("1.29");
    when(cluster.roleArn()).thenReturn("arn:aws:iam::123456789012:role/EKSRole");
    when(cluster.resourcesVpcConfig()).thenReturn(vpcConfig);
    when(vpcConfig.subnetIds()).thenReturn(List.of("subnet-123", "subnet-456"));
    when(cluster.accessConfig()).thenReturn(accessConfig);
    when(accessConfig.bootstrapClusterCreatorAdminPermissions()).thenReturn(true);
    when(accessConfig.authenticationMode()).thenReturn(AuthenticationMode.CONFIG_MAP);
    when(cluster.upgradePolicy()).thenReturn(upgradePolicy);
    when(upgradePolicy.supportType()).thenReturn(SupportType.STANDARD);
    when(cluster.zonalShiftConfig()).thenReturn(zonalShiftConfig);
    when(zonalShiftConfig.enabled()).thenReturn(true);
    when(cluster.encryptionConfig()).thenReturn(List.of(encryptionConfig));
    when(encryptionConfig.provider()).thenReturn(provider);
    when(provider.keyArn()).thenReturn("arn:aws:kms:eu-west-1:123456789012:key/abc123");

    StepResult<EksClusterOutput> result = step.apply();

    assertEquals("test-cluster", result.getOutputs().get(EksClusterOutput.NAME));
    assertEquals("eu-west-1", result.getOutputs().get(EksClusterOutput.REGION));
    assertEquals("1.29", result.getOutputs().get(EksClusterOutput.VERSION));
    assertEquals("arn:aws:iam::123456789012:role/EKSRole", result.getOutputs().get(EksClusterOutput.ROLE_ARN));
    assertTrue(((SubnetIdSet) result.getOutputs().get(EksClusterOutput.SUBNETS)).getSubnetIds().contains("subnet-123"));
    assertTrue((Boolean) result.getOutputs().get(EksClusterOutput.CLUSTER_CREATOR_ADMIN_PERMISSIONS));
    assertEquals("CONFIG_MAP", result.getOutputs().get(EksClusterOutput.AUTHENTICATION_MODE));
    assertEquals("STANDARD", result.getOutputs().get(EksClusterOutput.SUPPORT_TYPE));
    assertEquals(true, result.getOutputs().get(EksClusterOutput.ZONAL_SHIFT_ENABLED));
    assertEquals("arn:aws:kms:eu-west-1:123456789012:key/abc123", result.getOutputs().get(EksClusterOutput.OWNED_KMS_KEY_ID));
  }

  @Test
  void shouldReturnDesiredStateWithExpectedOutputs_whenConfigIsValid() {

    when(config.getName()).thenReturn("test-cluster");
    when(config.isToDelete()).thenReturn(false);
    when(config.getName()).thenReturn("test-cluster");
    when(config.getRegion()).thenReturn(Region.EU_WEST_1);
    when(config.getVersion()).thenReturn("1.29");
    when(config.getRoleArn()).thenReturn("arn:aws:iam::000000000000:role/eks-cluster-role");
    when(config.getSubnets()).thenReturn(List.of("subnet-123", "subnet-456"));
    when(config.isBootstrapClusterCreatorAdminPermissions()).thenReturn(true);
    when(config.getAuthenticationMode()).thenReturn(EksClusterAuthenticationMode.API);
    when(config.getSupportType()).thenReturn(EksClusterSupportType.STANDARD);
    when(config.isEnableZonalShift()).thenReturn(true);
    when(config.isBootstrapSelfManagedAddons()).thenReturn(false);
    when(config.getOwnedEncryptionKMSKeyArn()).thenReturn("arn:aws:kms:key-id");

    step = new EksClusterStep(eks, config, metadata);

    // when
    StepResult<EksClusterOutput> result = step.generateDesiredState();

    // then
    assertThat(result.getStepName()).isEqualTo(EksClusterStep.class.getName());
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.NAME, "test-cluster");
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.REGION, "eu-west-1");
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.VERSION, "1.29");
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.ROLE_ARN, "arn:aws:iam::000000000000:role/eks-cluster-role");
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.SUBNETS, new SubnetIdSet(List.of("subnet-123", "subnet-456")));
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.CLUSTER_CREATOR_ADMIN_PERMISSIONS, true);
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.AUTHENTICATION_MODE, "API");
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.SUPPORT_TYPE, "STANDARD");
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.ZONAL_SHIFT_ENABLED, true);
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.SELF_MANAGED_ADDONS, false);
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.OWNED_KMS_KEY_ID, "arn:aws:kms:key-id");
  }

  @Test
  void shouldReturnEmptyResult_whenMarkedForDeletion() {

    config = new EksClusterConfig();
    config.setToDelete(true);

    step = new EksClusterStep(eks, config, metadata);

    StepResult<EksClusterOutput> result = step.generateDesiredState();

    assertThat(result.getStepName()).isEqualTo(EksClusterStep.class.getName());
    assertThat(result.getOutputs()).isEmpty();
    assertThat(result.isVoid()).isTrue();
  }

  @Test
  void shouldReturnCurrentState_whenClusterExists() {

    when(eks.describe("test-cluster"))
        .thenReturn(DescribeClusterResponse.builder().cluster(cluster).build());

    when(cluster.name()).thenReturn("test-cluster");
    when(cluster.arn()).thenReturn("arn:aws:eks:eu-west-1:000000000000:cluster/test-cluster");
    when(cluster.version()).thenReturn("1.29");
    when(cluster.roleArn()).thenReturn("arn:aws:iam::000000000000:role/eks-cluster-role");
    when(cluster.resourcesVpcConfig()).thenReturn(vpcConfig);
    when(vpcConfig.subnetIds()).thenReturn(List.of("subnet-1", "subnet-2"));

    when(cluster.accessConfig()).thenReturn(accessConfig);
    when(accessConfig.bootstrapClusterCreatorAdminPermissions()).thenReturn(true);
    when(accessConfig.authenticationMode()).thenReturn(AuthenticationMode.API);

    when(cluster.upgradePolicy()).thenReturn(upgradePolicy);
    when(upgradePolicy.supportType()).thenReturn(SupportType.STANDARD);

    when(cluster.zonalShiftConfig()).thenReturn(zonalShiftConfig);
    when(zonalShiftConfig.enabled()).thenReturn(true);

    when(cluster.encryptionConfig()).thenReturn(List.of(encryptionConfig));
    when(encryptionConfig.provider()).thenReturn(provider);
    when(provider.keyArn()).thenReturn("arn:aws:kms:key-id");

    // when
    StepResult<EksClusterOutput> result = step.getCurrentState();

    // then
    assertThat(result.getStepName()).isEqualTo(EksClusterStep.class.getName());
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.NAME, "test-cluster");
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.REGION, "eu-west-1");
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.VERSION, "1.29");
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.ROLE_ARN, "arn:aws:iam::000000000000:role/eks-cluster-role");
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.SUBNETS, new SubnetIdSet(List.of("subnet-1", "subnet-2")));
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.CLUSTER_CREATOR_ADMIN_PERMISSIONS, true);
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.AUTHENTICATION_MODE, "API");
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.SUPPORT_TYPE, "STANDARD");
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.ZONAL_SHIFT_ENABLED, true);
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.OWNED_KMS_KEY_ID, "arn:aws:kms:key-id");
  }

  @Test
  void shouldReturnEmptyStepResult_whenClusterNotFound() {

    ResourceNotFoundException notFound = ResourceNotFoundException.builder().message("Cluster not found").build();
    CloudResourceStepException exception = new CloudResourceStepException("wrapped", notFound);

    when(eks.describe("test-cluster")).thenThrow(exception);

    StepResult<EksClusterOutput> result = step.getCurrentState();

    assertThat(result.getStepName()).isEqualTo(EksClusterStep.class.getName());
    assertThat(result.getOutputs()).isEmpty();
  }

  @Test
  void shouldRethrowException_whenCauseIsNotResourceNotFound() {

    RuntimeException unexpectedCause = new RuntimeException("Unexpected error");
    CloudResourceStepException exception = new CloudResourceStepException("Wrapped error", unexpectedCause);

    when(eks.describe("test-cluster")).thenThrow(exception);

    assertThatThrownBy(() -> step.getCurrentState())
        .isInstanceOf(CloudResourceStepException.class)
        .hasCause(unexpectedCause)
        .hasMessageContaining("Wrapped error");
  }

  @Test
  void shouldDeleteCluster_whenPreventDestroyIsFalse() {

    metadata.getOutputs().put(EksClusterOutput.PREVENT_DESTROY, false);
    step = new EksClusterStep(eks, config, metadata);

    StepResult<EksClusterOutput> result = step.destroy(true);

    verify(eks).delete("test-cluster");
    verify(eks).waitUntilDeleted(eq("test-cluster"), any());
    assertThat(result.getStepName()).isEqualTo(EksClusterStep.class.getName());
    assertThat(result.getOutputs()).isEmpty();
  }

  @Test
  void shouldDeleteCluster_whenPreventDestroyTrueButNotEnforced() {

    metadata.getOutputs().put(EksClusterOutput.PREVENT_DESTROY, true);
    step = new EksClusterStep(eks, config, metadata);

    StepResult<EksClusterOutput> result = step.destroy(false);

    verify(eks).delete("test-cluster");
    verify(eks).waitUntilDeleted(eq("test-cluster"), any());
    assertThat(result.getStepName()).isEqualTo(EksClusterStep.class.getName());
    assertThat(result.getOutputs()).isEmpty();
  }

  @Test
  void shouldThrow_whenPreventDestroyTrueAndEnforced() {

    metadata.getOutputs().put(EksClusterOutput.PREVENT_DESTROY, true);
    step = new EksClusterStep(eks, config, metadata);

    assertThatThrownBy(() -> step.destroy(true))
        .isInstanceOf(CloudResourceStepException.class)
        .hasMessageContaining("Destruction of EKS cluster 'test-cluster' is prevented by configuration.");

    verify(eks, never()).delete(any());
    verify(eks, never()).waitUntilDeleted(any(), any());
  }

  @Test
  void shouldRevertCluster_whenConfigChanged() {

    when(config.isBootstrapSelfManagedAddons()).thenReturn(false);

    StepResult<EksClusterOutput> previous = StepResult.<EksClusterOutput>builder()
        .stepName(EksClusterStep.class.getName())
        .put(EksClusterOutput.NAME, "test-cluster")
        .put(EksClusterOutput.VERSION, "1.28") // different version triggers revert
        .put(EksClusterOutput.SUBNETS, new SubnetIdSet(List.of("subnet-123", "subnet-456")))
        .put(EksClusterOutput.AUTHENTICATION_MODE, "API")
        .put(EksClusterOutput.SUPPORT_TYPE, "STANDARD")
        .put(EksClusterOutput.ZONAL_SHIFT_ENABLED, true)
        .build();

    Cluster cluster = mock(Cluster.class);
    when(cluster.name()).thenReturn("test-cluster");
    when(cluster.arn()).thenReturn("arn:aws:eks:eu-west-1:123456789012:cluster/test-cluster");
    when(cluster.version()).thenReturn("1.28");
    when(cluster.roleArn()).thenReturn("arn:aws:iam::123456789012:role/eks-role");

    VpcConfigResponse vpcConfig = mock(VpcConfigResponse.class);
    when(vpcConfig.subnetIds()).thenReturn(List.of("subnet-123", "subnet-456"));
    when(cluster.resourcesVpcConfig()).thenReturn(vpcConfig);

    AccessConfigResponse access = mock(AccessConfigResponse.class);
    when(access.bootstrapClusterCreatorAdminPermissions()).thenReturn(true);
    when(access.authenticationMode()).thenReturn(AuthenticationMode.API);
    when(cluster.accessConfig()).thenReturn(access);

    UpgradePolicyResponse upgradePolicy = mock(UpgradePolicyResponse.class);
    when(upgradePolicy.supportType()).thenReturn(SupportType.STANDARD);
    when(cluster.upgradePolicy()).thenReturn(upgradePolicy);

    ZonalShiftConfigResponse zonal = mock(ZonalShiftConfigResponse.class);
    when(zonal.enabled()).thenReturn(true);
    when(cluster.zonalShiftConfig()).thenReturn(zonal);

    Provider provider = mock(Provider.class);
    when(provider.keyArn()).thenReturn("arn:aws:kms:key-id");
    EncryptionConfig encryptionConfig = mock(EncryptionConfig.class);
    when(encryptionConfig.provider()).thenReturn(provider);
    when(cluster.encryptionConfig()).thenReturn(List.of(encryptionConfig));

    DescribeClusterResponse response = mock(DescribeClusterResponse.class);
    when(response.cluster()).thenReturn(cluster);
    when(eks.describe("test-cluster")).thenReturn(response);

    step = new EksClusterStep(eks, config, metadata);

    StepResult<EksClusterOutput> result = step.revert(previous);

    verify(eks).updateCluster(eq("test-cluster"), any());
    verify(eks).waitUntilActive(eq("test-cluster"), any());

    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.NAME, "test-cluster");
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.VERSION, "1.28");
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.ROLE_ARN, "arn:aws:iam::123456789012:role/eks-role");
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.REGION, "eu-west-1");
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.ZONAL_SHIFT_ENABLED, true);
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.OWNED_KMS_KEY_ID, "arn:aws:kms:key-id");
  }

  @Test
  void shouldSkipRevert_whenConfigIsSame() {

    EksClusterConfig config = new EksClusterConfig();
    config.setName("test-cluster");
    config.setVersion("1.28");
    config.setSubnets(List.of("subnet-123", "subnet-456"));
    config.setAuthenticationMode(EksClusterAuthenticationMode.API);
    config.setSupportType(EksClusterSupportType.STANDARD);
    config.setEnableZonalShift(true);

    StepResult<EksClusterOutput> previous = StepResult.<EksClusterOutput>builder()
        .stepName(EksClusterStep.class.getName())
        .put(EksClusterOutput.NAME, "test-cluster")
        .put(EksClusterOutput.VERSION, "1.28")
        .put(EksClusterOutput.SUBNETS, new SubnetIdSet(List.of("subnet-123", "subnet-456")))
        .put(EksClusterOutput.AUTHENTICATION_MODE, "API")
        .put(EksClusterOutput.SUPPORT_TYPE, "STANDARD")
        .put(EksClusterOutput.ZONAL_SHIFT_ENABLED, true)
        .build();

    step = new EksClusterStep(eks, config, metadata);

    StepResult<EksClusterOutput> result = step.revert(previous);

    verify(eks, never()).updateCluster(any(), any());
    verify(eks, never()).waitUntilActive(any(), any());

    assertThat(result).isEqualTo(previous);
  }

}


