package bg.tuvarna.sit.cloud.core.aws.s3;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.model.CloudProvisionerSuccessfulResponse;
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
import java.util.Collections;
import java.util.concurrent.ExecutionException;

@ExtendWith(MockitoExtension.class)
class S3BucketProvisionerTest {

  @Mock
  private S3SafeClient s3;

  @Mock
  private CloudStepStrategyExecutor<S3Output> stepExecutor;

  @Mock
  private CloudProvisionStep<S3Output> step;

  private S3BucketProvisioner provisioner;

  @BeforeEach
  void setup() {

    StepResult<S3Output> metadata = StepResult.<S3Output>builder()
        .stepName("metadata-step")
        .put(S3Output.NAME, "my-bucket")
        .put(S3Output.ARN, "arn:aws:s3:::my-bucket")
        .build();

    provisioner = new S3BucketProvisioner(s3, stepExecutor, metadata);
  }

  @Test
  void provision_shouldReturnSuccessfulResponse_whenStepsExecutedSuccessfully() throws Exception {

    List<CloudProvisionStep<S3Output>> steps = List.of(step);
    StepResult<S3Output> result = StepResult.<S3Output>builder().stepName("dummy").build();

    when(stepExecutor.execute(any(), any())).thenReturn(List.of(result));

    CloudProvisionerSuccessfulResponse<S3Output> response = provisioner.provision(steps);

    assertThat(response).isNotNull();
    assertThat(response.getResourceName()).isEqualTo("my-bucket");
    assertThat(response.getResourceId()).isEqualTo("arn:aws:s3:::my-bucket");
    assertThat(response.getResults()).containsExactly(result);
    verify(stepExecutor).execute(any(), any());
    verify(s3).close();
  }

  @Test
  void provision_shouldReturnEmptyResponse_whenStepsAreEmpty() {

    CloudProvisionerSuccessfulResponse<S3Output> response = provisioner.provision(Collections.emptyList());

    assertThat(response).isNotNull();
    assertThat(response.getResourceName()).isEqualTo("my-bucket");
    assertThat(response.getResults()).isEmpty();
    verifyNoInteractions(stepExecutor);
    verifyNoInteractions(s3);
  }

  @Test
  void provision_shouldThrowTerminationException_whenStepExecutionFails() throws Exception {

    List<CloudProvisionStep<S3Output>> steps = List.of(step);
    when(stepExecutor.execute(eq(steps), any()))
        .thenThrow(new CloudResourceStepException("failure"));

    assertThatThrownBy(() -> provisioner.provision(steps))
        .isInstanceOf(CloudProvisioningTerminationException.class)
        .hasMessage("failure");
  }

  @Test
  void provision_shouldThrowTerminationException_whenExecutionExceptionWithUnexpectedCauseOccurs() throws Exception {
    List<CloudProvisionStep<S3Output>> steps = List.of(step);
    ExecutionException ex = new ExecutionException(new IllegalStateException("exception"));

    when(stepExecutor.execute(eq(steps), any())).thenThrow(ex);

    assertThatThrownBy(() -> provisioner.provision(steps))
        .isInstanceOf(CloudProvisioningTerminationException.class)
        .hasMessageContaining("Unexpected exception occurred during async execution");
  }
}
