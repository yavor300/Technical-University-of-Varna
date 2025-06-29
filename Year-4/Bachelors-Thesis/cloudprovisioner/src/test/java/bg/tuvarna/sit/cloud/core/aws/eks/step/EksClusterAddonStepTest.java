package bg.tuvarna.sit.cloud.core.aws.eks.step;

import bg.tuvarna.sit.cloud.core.aws.eks.EksClusterOutput;
import bg.tuvarna.sit.cloud.core.aws.eks.client.EksSafeClient;
import bg.tuvarna.sit.cloud.core.aws.eks.config.EksClusterConfig;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterAddon;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterAddons;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.eks.model.EksException;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EksClusterAddonStepTest {

  @Mock
  private EksSafeClient eks;
  @Mock
  private EksClusterConfig config;

  private StepResult<EksClusterOutput> metadata;

  private EksClusterAddonStep step;

  @BeforeEach
  void setup() {

    metadata = StepResult.<EksClusterOutput>builder()
        .stepName(EksClusterPersistentMetadataStep.class.getName())
        .put(EksClusterOutput.NAME, "test-cluster")
        .build();
  }

  private void initStep() {
    step = new EksClusterAddonStep(eks, config, metadata);
  }

  @Test
  void testApply_CreatesAddonIfMissing() {

    EksClusterConfig.EksAddonConfig addonConfig = new EksClusterConfig.EksAddonConfig();
    addonConfig.setName("vpc-cni");
    addonConfig.setVersion("v1.12");

    when(config.getAddons()).thenReturn(List.of(addonConfig));
    when(eks.getAddons("test-cluster")).thenReturn(new EksClusterAddons());

    initStep();
    StepResult<EksClusterOutput> result = step.apply();

    verify(eks).createAddon("test-cluster", "vpc-cni", "v1.12");
    verify(eks).waitUntilAddonsActive(eq("test-cluster"), any(), any());
    assertEquals("vpc-cni", ((EksClusterAddons) result.getOutputs().get(EksClusterOutput.ADDONS))
        .getAddons().getFirst().getName());
  }

  @Test
  void testApply_UpdatesAddonIfVersionChanged() {

    EksClusterConfig.EksAddonConfig configured = new EksClusterConfig.EksAddonConfig();
    configured.setName("coredns");
    configured.setVersion("v1.9.0");

    EksClusterAddon current = new EksClusterAddon("coredns", "v1.8.0");
    EksClusterAddons existing = new EksClusterAddons(List.of(current));

    when(config.getAddons()).thenReturn(List.of(configured));
    when(eks.getAddons("test-cluster")).thenReturn(existing);

    initStep();
    step.apply();

    verify(eks).updateAddon("test-cluster", "coredns", "v1.9.0");
    verify(eks).waitUntilAddonsActive(eq("test-cluster"), any(), any());
  }

  @Test
  void testApply_SkipsUnchangedAddon() {

    EksClusterConfig.EksAddonConfig configured = new EksClusterConfig.EksAddonConfig();
    configured.setName("kube-proxy");
    configured.setVersion("v1.0");

    EksClusterAddon current = new EksClusterAddon("kube-proxy", "v1.0");
    EksClusterAddons existing = new EksClusterAddons(List.of(current));

    when(config.getAddons()).thenReturn(List.of(configured));
    when(eks.getAddons("test-cluster")).thenReturn(existing);

    initStep();
    step.apply();

    verify(eks, never()).createAddon(any(), any(), any());
    verify(eks, never()).updateAddon(any(), any(), any());
    verify(eks).waitUntilAddonsActive(eq("test-cluster"), any(), any());
  }

  @Test
  void testGenerateDesiredState_NoAddonsConfigured() {

    when(config.getAddons()).thenReturn(null);

    initStep();
    StepResult<EksClusterOutput> result = step.generateDesiredState();

    assertEquals(EksClusterAddonStep.class.getName(), result.getStepName());
    assertTrue(result.getOutputs().isEmpty());
  }

  @Test
  void testGenerateDesiredState_EmptyListConfigured() {

    when(config.getAddons()).thenReturn(List.of());

    initStep();
    StepResult<EksClusterOutput> result = step.generateDesiredState();

    assertEquals(EksClusterAddonStep.class.getName(), result.getStepName());
    assertTrue(result.getOutputs().isEmpty());
  }

  @Test
  void testGenerateDesiredState_WithAddons() {

    EksClusterConfig.EksAddonConfig addon1 = new EksClusterConfig.EksAddonConfig();
    addon1.setName("vpc-cni");
    addon1.setVersion("v1.12.0");

    EksClusterConfig.EksAddonConfig addon2 = new EksClusterConfig.EksAddonConfig();
    addon2.setName("coredns");
    addon2.setVersion("v1.8.7");

    when(config.getAddons()).thenReturn(List.of(addon1, addon2));

    initStep();
    StepResult<EksClusterOutput> result = step.generateDesiredState();

    assertEquals(EksClusterAddonStep.class.getName(), result.getStepName());
    Object output = result.getOutputs().get(EksClusterOutput.ADDONS);

    assertInstanceOf(EksClusterAddons.class, output);
    EksClusterAddons addons = (EksClusterAddons) output;

    assertEquals(2, addons.getAddons().size());
    assertEquals("vpc-cni", addons.getAddons().get(0).getName());
    assertEquals("coredns", addons.getAddons().get(1).getName());
  }

  @Test
  void testGetCurrentState_ClusterExistsWithAddons() {
    EksClusterAddon addon = new EksClusterAddon("vpc-cni", "v1.12.0");
    EksClusterAddons addons = new EksClusterAddons(List.of(addon));

    when(eks.getAddons("test-cluster")).thenReturn(addons);

    initStep();
    StepResult<EksClusterOutput> result = step.getCurrentState();

    assertEquals(EksClusterAddonStep.class.getName(), result.getStepName());
    Object value = result.getOutputs().get(EksClusterOutput.ADDONS);
    assertInstanceOf(EksClusterAddons.class, value);
    assertEquals(1, ((EksClusterAddons) value).getAddons().size());
  }

  @Test
  void testGetCurrentState_ClusterNotFound404() {

    AwsServiceException notFound = EksException.builder().message("not found").statusCode(404).build();
    CloudResourceStepException wrapped = new CloudResourceStepException("not found", notFound);

    when(eks.getAddons("test-cluster")).thenThrow(wrapped);

    initStep();
    StepResult<EksClusterOutput> result = step.getCurrentState();

    assertEquals(EksClusterAddonStep.class.getName(), result.getStepName());
    assertTrue(result.getOutputs().isEmpty());
  }

  @Test
  void testGetCurrentState_ThrowsCloudResourceStepException_OtherEksError() {

    AwsServiceException eksException = EksException.builder().message("exception").statusCode(500).build();
    CloudResourceStepException wrapped = new CloudResourceStepException("err", eksException);

    when(eks.getAddons("test-cluster")).thenThrow(wrapped);

    initStep();
    assertThrows(CloudResourceStepException.class, () -> step.getCurrentState());
  }

  @Test
  void testGetCurrentState_ThrowsCloudResourceStepException_WithoutCause() {
    CloudResourceStepException ex = new CloudResourceStepException("generic error");
    when(eks.getAddons("test-cluster")).thenThrow(ex);

    initStep();
    assertThrows(CloudResourceStepException.class, () -> step.getCurrentState());
  }

  @Test
  void testDestroy_WhenClusterHasAddons_ShouldRemoveAndWait() {
    String clusterName = "test-cluster";

    List<EksClusterAddon> addons = List.of(
        new EksClusterAddon("vpc-cni", "1.2.3"),
        new EksClusterAddon("coredns", "1.8.4")
    );
    EksClusterAddons currentAddons = new EksClusterAddons(addons);

    when(eks.getAddons(clusterName)).thenReturn(currentAddons);

    initStep();

    StepResult<EksClusterOutput> result = step.destroy(false);

    assertEquals(EksClusterAddonStep.class.getName(), result.getStepName());
    assertTrue(result.getOutputs().isEmpty());

    for (EksClusterAddon addon : addons) {
      verify(eks).removeAddon(clusterName, addon.getName());
    }

    verify(eks).waitUntilAddonsDeleted(clusterName, currentAddons, Duration.ofMinutes(5));
  }

  @Test
  void testRevert_RemovesMissingAddonsAndRestoresPreviousVersions() {

    EksClusterAddon oldCni = new EksClusterAddon("vpc-cni", "1.2.3");
    EksClusterAddon oldDns = new EksClusterAddon("coredns", "1.8.4");

    EksClusterAddon liveCni = new EksClusterAddon("vpc-cni", "1.2.3");
    EksClusterAddon extra = new EksClusterAddon("ebs-csi", "1.0.0");

    StepResult<EksClusterOutput> previous = StepResult.<EksClusterOutput>builder()
        .stepName("previous")
        .put(EksClusterOutput.ADDONS, new EksClusterAddons(List.of(oldCni, oldDns)))
        .build();

    when(eks.getAddons("test-cluster")).thenReturn(
        new EksClusterAddons(List.of(liveCni, extra)),
        new EksClusterAddons(List.of(oldCni, oldDns))
    );

    initStep();
    StepResult<EksClusterOutput> result = step.revert(previous);

    assertEquals(EksClusterAddonStep.class.getName(), result.getStepName());
    assertTrue(result.getOutputs().containsKey(EksClusterOutput.ADDONS));
    EksClusterAddons output = (EksClusterAddons) result.getOutputs().get(EksClusterOutput.ADDONS);
    assertEquals(2, output.getAddons().size());

    verify(eks).removeAddon("test-cluster", "ebs-csi");
    verify(eks).createAddon("test-cluster", "coredns", "1.8.4");

    verify(eks).waitUntilAddonsDeleted(eq("test-cluster"), any(), eq(Duration.ofMinutes(5)));
    verify(eks).waitUntilAddonsActive(eq("test-cluster"), any(), eq(Duration.ofMinutes(5)));
  }


  @Test
  void testRevert_UpdatesAddonIfVersionDiffers() {

    EksClusterAddon previousCni = new EksClusterAddon("vpc-cni", "1.2.3");

    EksClusterAddon currentCni = new EksClusterAddon("vpc-cni", "1.1.0");

    StepResult<EksClusterOutput> previous = StepResult.<EksClusterOutput>builder()
        .stepName("previous")
        .put(EksClusterOutput.ADDONS, new EksClusterAddons(List.of(previousCni)))
        .build();

    when(eks.getAddons("test-cluster")).thenReturn(
        new EksClusterAddons(List.of(currentCni)),
        new EksClusterAddons(List.of(previousCni))
    );

    initStep();
    StepResult<EksClusterOutput> result = step.revert(previous);

    assertEquals(EksClusterAddonStep.class.getName(), result.getStepName());
    assertTrue(result.getOutputs().containsKey(EksClusterOutput.ADDONS));

    verify(eks).updateAddon("test-cluster", "vpc-cni", "1.2.3");
    verify(eks, never()).createAddon(any(), any(), any());
    verify(eks, never()).removeAddon(any(), any());

    verify(eks).waitUntilAddonsActive(eq("test-cluster"), any(), eq(Duration.ofMinutes(5)));
  }

}


