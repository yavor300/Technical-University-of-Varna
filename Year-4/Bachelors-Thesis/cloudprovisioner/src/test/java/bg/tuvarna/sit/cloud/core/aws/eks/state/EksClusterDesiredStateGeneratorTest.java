package bg.tuvarna.sit.cloud.core.aws.eks.state;

import bg.tuvarna.sit.cloud.core.aws.eks.EksClusterOutput;
import bg.tuvarna.sit.cloud.core.aws.eks.step.EksClusterStep;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.executor.CloudStepStrategyExecutor;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudProvisioningTerminationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EksClusterDesiredStateGeneratorTest {

  @Mock
  private CloudStepStrategyExecutor<EksClusterOutput> stepExecutor;

  @Mock
  private StepResult<EksClusterOutput> metadata;

  @Mock
  private CloudProvisionStep<EksClusterOutput> step1;

  @Mock
  private CloudProvisionStep<EksClusterOutput> step2;

  private EksClusterDesiredStateGenerator generator;

  @BeforeEach
  public void setUp() {

    List<CloudProvisionStep<EksClusterOutput>> steps = List.of(step1, step2);
    generator = new EksClusterDesiredStateGenerator(steps, stepExecutor, metadata);
  }

  @Test
  public void testGenerate_Success() throws Exception {

    List<StepResult<EksClusterOutput>> expectedResults = List.of(
        StepResult.<EksClusterOutput>builder()
            .stepName(EksClusterStep.class.getName())
            .put(EksClusterOutput.NAME, "test-cluster")
            .build());

    when(stepExecutor.execute(any(), any())).thenReturn(expectedResults);

    List<StepResult<EksClusterOutput>> results = generator.generate();

    assertEquals(expectedResults, results);
    verify(stepExecutor).execute(any(), any());
  }

  @Test
  public void testGenerate_ThrowsTerminationException() throws Exception {

    when(stepExecutor.execute(any(), any()))
        .thenThrow(new ExecutionException("Execution exception", new RuntimeException()));

    when(metadata.getOutputs()).thenReturn(Map.of(EksClusterOutput.NAME, "my-cluster"));

    CloudProvisioningTerminationException ex = assertThrows(
        CloudProvisioningTerminationException.class,
        () -> generator.generate()
    );

    assertTrue(ex.getMessage().contains("Failed to generate desired EKS state for cluster 'my-cluster'"));
  }

  @Test
  public void testGenerate_ThrowsTerminationException_OnInterrupted() throws Exception {

    when(stepExecutor.execute(any(), any())).thenThrow(new InterruptedException("interrupted"));

    when(metadata.getOutputs()).thenReturn(Map.of(EksClusterOutput.NAME, "interrupted-cluster"));

    CloudProvisioningTerminationException ex = assertThrows(
        CloudProvisioningTerminationException.class,
        () -> generator.generate()
    );

    assertTrue(ex.getMessage().contains("Failed to generate desired EKS state"));
  }
}

