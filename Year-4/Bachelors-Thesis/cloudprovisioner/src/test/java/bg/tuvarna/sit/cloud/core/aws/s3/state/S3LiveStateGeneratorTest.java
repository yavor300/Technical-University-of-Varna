package bg.tuvarna.sit.cloud.core.aws.s3.state;

import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class S3LiveStateGeneratorTest {

  @Mock
  private CloudStepStrategyExecutor<S3Output> stepExecutor;

  @Mock
  private CloudProvisionStep<S3Output> step1;

  @Mock
  private CloudProvisionStep<S3Output> step2;

  private S3LiveStateGenerator generator;

  @BeforeEach
  void setup() {
    List<CloudProvisionStep<S3Output>> steps = List.of(step1, step2);
    generator = new S3LiveStateGenerator(stepExecutor, steps);
  }

  @Test
  void generate_shouldReturnStepResults_whenExecutionSucceeds() throws Exception {

    StepResult<S3Output> result1 = StepResult.<S3Output>builder().stepName("Step1").build();
    StepResult<S3Output> result2 = StepResult.<S3Output>builder().stepName("Step2").build();

    when(stepExecutor.execute(anyList(), any())).thenReturn(List.of(result1, result2));

    List<StepResult<S3Output>> result = generator.generate();

    assertThat(result).containsExactly(result1, result2);
  }

  @Test
  void generate_shouldWrapCloudResourceStepException() throws Exception {

    CloudResourceStepException exception = new CloudResourceStepException("error");
    when(stepExecutor.execute(anyList(), any())).thenThrow(exception);

    assertThatThrownBy(() -> generator.generate())
        .isInstanceOf(CloudProvisioningTerminationException.class)
        .hasMessageContaining("error")
        .hasCause(exception);
  }

  @Test
  void generate_shouldWrapUnexpectedExecutionException() throws Exception {

    RuntimeException cause = new RuntimeException("boom");
    ExecutionException exception = new ExecutionException("wrapper", cause);
    when(stepExecutor.execute(anyList(), any())).thenThrow(exception);

    assertThatThrownBy(() -> generator.generate())
        .isInstanceOf(CloudProvisioningTerminationException.class)
        .hasMessageContaining("Unexpected exception occurred during async execution")
        .hasCause(cause);
  }

  @Test
  void generate_shouldWrapInterruptedException() throws Exception {

    InterruptedException exception = new InterruptedException("interrupted");
    when(stepExecutor.execute(anyList(), any())).thenThrow(exception);

    assertThatThrownBy(() -> generator.generate())
        .isInstanceOf(CloudProvisioningTerminationException.class)
        .hasCause(exception);
  }
}

