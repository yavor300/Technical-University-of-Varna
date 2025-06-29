package bg.tuvarna.sit.cloud.core.aws.s3;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.model.CloudProvisionerSuccessfulResponse;
import bg.tuvarna.sit.cloud.core.provisioner.executor.CloudStepDeleteExecutor;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudProvisioningTerminationException;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

@ExtendWith(MockitoExtension.class)
class S3BucketDestroyerTest {

  @Mock
  private S3SafeClient s3;

  @Mock
  private CloudStepDeleteExecutor<S3Output> stepExecutor;

  @Mock
  private CloudProvisionStep<S3Output> step;

  private S3BucketDestroyer destroyer;

  @BeforeEach
  void setup() {

    StepResult<S3Output> metadata = StepResult.<S3Output>builder()
        .stepName("metadata-step")
        .put(S3Output.NAME, "my-bucket")
        .put(S3Output.ARN, "arn:aws:s3:::my-bucket")
        .build();

    destroyer = new S3BucketDestroyer(s3, stepExecutor, metadata);
  }

  @Test
  void destroy_shouldReturnSuccessfulResponse_whenStepsExecutedSuccessfully() throws Exception {
    List<CloudProvisionStep<S3Output>> steps = List.of(step);
    StepResult<S3Output> result = StepResult.<S3Output>builder().stepName("dummy").build();
    when(stepExecutor.execute(steps, true)).thenReturn(List.of(result));

    CloudProvisionerSuccessfulResponse<S3Output> response = destroyer.destroy(steps, true);

    assertThat(response).isNotNull();
    assertThat(response.getResourceName()).isEqualTo("my-bucket");
    assertThat(response.getResourceId()).isEqualTo("arn:aws:s3:::my-bucket");
    assertThat(response.getResults()).containsExactly(result);
    verify(stepExecutor).execute(steps, true);
    verify(s3).close();
  }

  @Test
  void destroy_shouldReturnEmptyResponse_whenStepsAreEmpty() throws Exception {
    CloudProvisionerSuccessfulResponse<S3Output> response = destroyer.destroy(Collections.emptyList(), false);

    assertThat(response).isNotNull();
    assertThat(response.getResourceName()).isEqualTo("my-bucket");
    assertThat(response.getResults()).isEmpty();
    verifyNoInteractions(stepExecutor);
    verifyNoInteractions(s3);
  }

  @Test
  void destroy_shouldThrowTerminationException_whenStepExecutionFails() throws Exception {
    List<CloudProvisionStep<S3Output>> steps = List.of(step);
    when(stepExecutor.execute(steps, false)).thenThrow(new CloudResourceStepException("exception"));

    assertThatThrownBy(() -> destroyer.destroy(steps, false))
        .isInstanceOf(CloudProvisioningTerminationException.class)
        .hasMessage("exception");
  }

  @Test
  void destroy_shouldHandleUnexpectedExecutionExceptionCause() throws Exception {
    List<CloudProvisionStep<S3Output>> steps = List.of(step);
    ExecutionException ex = new ExecutionException(new IllegalStateException("unexpected"));
    doThrow(ex).when(stepExecutor).execute(steps, false);

    assertThatThrownBy(() -> destroyer.destroy(steps, false))
        .isInstanceOf(CloudProvisioningTerminationException.class)
        .hasMessageContaining("Unexpected exception occurred");
  }
}
