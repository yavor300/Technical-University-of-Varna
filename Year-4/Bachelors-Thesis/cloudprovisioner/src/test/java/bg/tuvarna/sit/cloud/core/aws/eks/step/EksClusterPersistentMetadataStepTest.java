package bg.tuvarna.sit.cloud.core.aws.eks.step;

import bg.tuvarna.sit.cloud.core.aws.eks.EksClusterOutput;
import bg.tuvarna.sit.cloud.core.aws.eks.client.EksSafeClient;
import bg.tuvarna.sit.cloud.core.aws.eks.config.EksClusterConfig;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EksClusterPersistentMetadataStepTest {

  @Mock
  private EksSafeClient eks;

  @Mock
  private EksClusterConfig config;

  private StepResult<EksClusterOutput> metadata;

  private EksClusterPersistentMetadataStep step;

  @BeforeEach
  void setup() {

    metadata = StepResult.<EksClusterOutput>builder()
        .stepName(EksClusterPersistentMetadataStep.class.getName())
        .put(EksClusterOutput.NAME, "test-cluster")
        .put(EksClusterOutput.REGION, "eu-west-1")
        .put(EksClusterOutput.ARN, "arn:aws:eks:...")
        .put(EksClusterOutput.PREVENT_DESTROY, Boolean.TRUE)
        .build();
  }

  @Test
  void apply_shouldReturnMetadataWithClusterInfo() {

    when(config.preventDestroy()).thenReturn(null);

    step = new EksClusterPersistentMetadataStep(eks, config, metadata);

    StepResult<EksClusterOutput> result = step.apply();

    assertNotNull(result);
    assertEquals(EksClusterPersistentMetadataStep.class.getName(), result.getStepName());

    assertEquals("test-cluster", result.getOutputs().get(EksClusterOutput.NAME));
    assertEquals("eu-west-1", result.getOutputs().get(EksClusterOutput.REGION));
    assertEquals("arn:aws:eks:...", result.getOutputs().get(EksClusterOutput.ARN));
    assertEquals(Boolean.TRUE, result.getOutputs().get(EksClusterOutput.PREVENT_DESTROY));
  }

  @Test
  void apply_shouldOverridePreventDestroyWhenSetInConfig() {
    when(config.preventDestroy()).thenReturn(Boolean.FALSE);

    step = new EksClusterPersistentMetadataStep(eks, config, metadata);

    StepResult<EksClusterOutput> result = step.apply();

    assertEquals(Boolean.FALSE, result.getOutputs().get(EksClusterOutput.PREVENT_DESTROY));
  }

  @Test
  void generateDesiredState_shouldReturnMetadataWithClusterInfo() {

    when(config.preventDestroy()).thenReturn(null);

    step = new EksClusterPersistentMetadataStep(eks, config, metadata);

    StepResult<EksClusterOutput> result = step.generateDesiredState();

    assertNotNull(result);
    assertEquals(EksClusterPersistentMetadataStep.class.getName(), result.getStepName());

    assertEquals("test-cluster", result.getOutputs().get(EksClusterOutput.NAME));
    assertEquals("eu-west-1", result.getOutputs().get(EksClusterOutput.REGION));
    assertEquals("arn:aws:eks:...", result.getOutputs().get(EksClusterOutput.ARN));
    assertEquals(Boolean.TRUE, result.getOutputs().get(EksClusterOutput.PREVENT_DESTROY));
  }

  @Test
  void generateDesiredState_shouldOverridePreventDestroyWhenSetInConfig() {

    when(config.preventDestroy()).thenReturn(Boolean.FALSE);

    step = new EksClusterPersistentMetadataStep(eks, config, metadata);

    StepResult<EksClusterOutput> result = step.generateDesiredState();

    assertEquals(Boolean.FALSE, result.getOutputs().get(EksClusterOutput.PREVENT_DESTROY));
  }

  @Test
  void getCurrentState_shouldReturnMetadataSnapshot() {

    step = new EksClusterPersistentMetadataStep(eks, config, metadata);

    StepResult<EksClusterOutput> result = step.getCurrentState();

    assertNotNull(result);
    assertEquals(EksClusterPersistentMetadataStep.class.getName(), result.getStepName());
    assertEquals("test-cluster", result.getOutputs().get(EksClusterOutput.NAME));
    assertEquals("eu-west-1", result.getOutputs().get(EksClusterOutput.REGION));
    assertEquals("arn:aws:eks:...", result.getOutputs().get(EksClusterOutput.ARN));
    assertEquals(Boolean.TRUE, result.getOutputs().get(EksClusterOutput.PREVENT_DESTROY));
  }

  @Test
  void destroy_shouldReturnNull() {
    step = new EksClusterPersistentMetadataStep(eks, config, metadata);

    StepResult<EksClusterOutput> result = step.destroy(false);

    assertNull(result, "Expected destroy() to return null since it's not implemented yet.");
  }

  @Test
  void revert_shouldReturnNull() {
    step = new EksClusterPersistentMetadataStep(eks, config, metadata);

    StepResult<EksClusterOutput> result = step.revert(metadata);

    assertNull(result, "Expected revert() to return null since it's not implemented yet.");
  }

}
