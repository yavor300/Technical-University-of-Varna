package bg.tuvarna.sit.cloud.core.aws.eks.state;

import bg.tuvarna.sit.cloud.core.aws.eks.EksClusterOutput;
import bg.tuvarna.sit.cloud.core.aws.eks.step.EksClusterStep;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.executor.CloudStepStrategyExecutor;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudProvisioningTerminationException;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EksClusterLiveStateGeneratorTest {

  @Mock
  private CloudStepStrategyExecutor<EksClusterOutput> stepExecutor;

  @Mock
  private CloudProvisionStep<EksClusterOutput> step1;

  @Mock
  private CloudProvisionStep<EksClusterOutput> step2;

  private EksClusterLiveStateGenerator generator;

  @BeforeEach
  void setUp() {

    generator = new EksClusterLiveStateGenerator(stepExecutor, List.of(step1, step2));
  }

  @Test
  public void testGenerate_Success() throws Exception {

    List<StepResult<EksClusterOutput>> expected = List.of(
        StepResult.<EksClusterOutput>builder().stepName(EksClusterStep.class.getName())
            .put(EksClusterOutput.NAME, "cluster").build()
    );

    when(stepExecutor.execute(any(), any())).thenReturn(expected);

    List<StepResult<EksClusterOutput>> result = generator.generate();

    assertEquals(expected, result);
    verify(stepExecutor).execute(eq(List.of(step1, step2)), any());
  }

  @Test
  public void testGenerate_ThrowsCloudResourceStepException() throws Exception {

    when(stepExecutor.execute(any(), any()))
        .thenThrow(new CloudResourceStepException("bad step"));

    CloudProvisioningTerminationException ex = assertThrows(
        CloudProvisioningTerminationException.class,
        () -> generator.generate()
    );

    assertTrue(ex.getMessage().contains("bad step"));
  }

  @Test
  public void testGenerate_ExecutionException_WrappedCloudResourceStepException() throws Exception {

    ExecutionException exec = new ExecutionException(new CloudResourceStepException("wrapped error"));

    when(stepExecutor.execute(any(), any())).thenThrow(exec);

    CloudProvisioningTerminationException ex = assertThrows(
        CloudProvisioningTerminationException.class,
        () -> generator.generate()
    );

    assertEquals("wrapped error", ex.getMessage());
  }

  @Test
  public void testGenerate_ExecutionException_UnexpectedCause() throws Exception {

    ExecutionException exec = new ExecutionException(new IllegalArgumentException("unexpected failure"));

    when(stepExecutor.execute(any(), any())).thenThrow(exec);

    CloudProvisioningTerminationException ex = assertThrows(
        CloudProvisioningTerminationException.class,
        () -> generator.generate()
    );

    assertTrue(ex.getMessage().contains("Unexpected exception occurred"));
  }

  @Test
  public void testGenerate_InterruptedException() throws Exception {

    when(stepExecutor.execute(any(), any()))
        .thenThrow(new InterruptedException("interrupted"));

    CloudProvisioningTerminationException ex = assertThrows(
        CloudProvisioningTerminationException.class,
        () -> generator.generate()
    );

    assertTrue(ex.getMessage().contains("interrupted"));
  }

}
