package bg.tuvarna.sit.cloud.core.aws.s3;

import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.model.CloudProvisionerSuccessfulResponse;
import bg.tuvarna.sit.cloud.core.provisioner.executor.CloudStepRevertExecutor;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class S3BucketReverterTest {

  @Mock
  private S3SafeClient s3;

  @Mock
  private CloudStepRevertExecutor<S3Output> stepExecutor;

  @Mock
  private CloudProvisionStep<S3Output> step;

  private S3BucketReverter reverter;

  @BeforeEach
  void setup() {

    StepResult<S3Output> metadata = StepResult.<S3Output>builder()
        .stepName("S3Metadata")
        .put(S3Output.NAME, "test-bucket")
        .put(S3Output.ARN, "arn:aws:s3:::test-bucket")
        .build();

    reverter = new S3BucketReverter(s3, stepExecutor, metadata);
  }

  @Test
  void revert_shouldReturnSuccessfulResponse_whenStepsRevertedSuccessfully() throws Exception {
    List<CloudProvisionStep<S3Output>> steps = List.of(step);
    StepResult<S3Output> prev = StepResult.<S3Output>builder().stepName("step").build();

    StepResult<S3Output> reverted = StepResult.<S3Output>builder().stepName("step").build();

    when(stepExecutor.execute(steps, List.of(prev))).thenReturn(List.of(reverted));

    CloudProvisionerSuccessfulResponse<S3Output> response = reverter.revert(steps, List.of(prev));

    assertThat(response).isNotNull();
    assertThat(response.getResourceName()).isEqualTo("test-bucket");
    assertThat(response.getResourceId()).isEqualTo("arn:aws:s3:::test-bucket");
    assertThat(response.getResults()).containsExactly(reverted);

    verify(stepExecutor).execute(steps, List.of(prev));
    verify(s3).close();
  }

  @Test
  void revert_shouldThrow_whenStepExecutorThrowsCloudResourceStepException() throws Exception {
    List<CloudProvisionStep<S3Output>> steps = List.of(step);
    StepResult<S3Output> prev = StepResult.<S3Output>builder().stepName("step").build();

    when(stepExecutor.execute(any(), any()))
        .thenThrow(new CloudResourceStepException("revert-failed"));

    assertThatThrownBy(() -> reverter.revert(steps, List.of(prev)))
        .isInstanceOf(CloudProvisioningTerminationException.class)
        .hasMessageContaining("revert-failed");

    verify(s3).close();
  }

  @Test
  void revert_shouldWrapUnexpectedExecutionException() throws Exception {
    List<CloudProvisionStep<S3Output>> steps = List.of(step);
    StepResult<S3Output> prev = StepResult.<S3Output>builder().stepName("step").build();

    ExecutionException cause = new ExecutionException("boom", new IllegalStateException("bad"));
    when(stepExecutor.execute(any(), any())).thenThrow(cause);

    assertThatThrownBy(() -> reverter.revert(steps, List.of(prev)))
        .isInstanceOf(CloudProvisioningTerminationException.class)
        .hasMessageContaining("Unexpected exception");

    verify(s3).close();
  }

  @Test
  void revert_shouldReturnEmptyResults_whenNoStepsProvided() {
    CloudProvisionerSuccessfulResponse<S3Output> response = reverter.revert(List.of(), List.of());

    assertThat(response.getResults()).isEmpty();
    assertThat(response.getResourceName()).isEqualTo("test-bucket");
    assertThat(response.getResourceId()).isEqualTo("arn:aws:s3:::test-bucket");

    verify(s3).close();
  }
}
