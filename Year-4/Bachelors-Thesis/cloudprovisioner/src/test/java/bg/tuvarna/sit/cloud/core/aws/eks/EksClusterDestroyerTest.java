package bg.tuvarna.sit.cloud.core.aws.eks;

import bg.tuvarna.sit.cloud.core.aws.eks.client.EksSafeClient;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.model.CloudProvisionerSuccessfulResponse;
import bg.tuvarna.sit.cloud.core.provisioner.model.CloudResourceType;
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
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EksClusterDestroyerTest {

  @Mock
  private EksSafeClient eks;

  @Mock
  private CloudStepDeleteExecutor<EksClusterOutput> stepExecutor;

  private StepResult<EksClusterOutput> metadata;

  private EksClusterDestroyer destroyer;

  @BeforeEach
  void setUp() {
    metadata = StepResult.<EksClusterOutput>builder()
        .stepName("EksClusterMetadata")
        .put(EksClusterOutput.NAME, "test-cluster")
        .put(EksClusterOutput.ARN, "arn:aws:eks:eu-west-1:000000000000:cluster/test-cluster")
        .build();

    destroyer = new EksClusterDestroyer(eks, stepExecutor, metadata);
  }

  @Test
  void destroy_shouldReturnEmptyResponse_whenNoSteps() {

    CloudProvisionerSuccessfulResponse<EksClusterOutput> response = destroyer.destroy(List.of(), true);

    assertThat(response.getResourceType()).isEqualTo(CloudResourceType.EKS);
    assertThat(response.getResourceName()).isEqualTo("test-cluster");
    assertThat(response.getResourceId()).isEqualTo("arn:aws:eks:eu-west-1:000000000000:cluster/test-cluster");
    assertThat(response.getResults()).isEmpty();
  }

  @Test
  void destroy_shouldReturnResponse_whenStepsExecutedSuccessfully() throws Exception {

    CloudProvisionStep<EksClusterOutput> step = mock(CloudProvisionStep.class);
    StepResult<EksClusterOutput> result = StepResult.<EksClusterOutput>builder().stepName("step-1").build();
    when(stepExecutor.execute(anyList(), anyBoolean())).thenReturn(List.of(result));

    CloudProvisionerSuccessfulResponse<EksClusterOutput> response = destroyer.destroy(List.of(step), false);

    assertThat(response.getResourceType()).isEqualTo(CloudResourceType.EKS);
    assertThat(response.getResourceName()).isEqualTo("test-cluster");
    assertThat(response.getResourceId()).isEqualTo("arn:aws:eks:eu-west-1:000000000000:cluster/test-cluster");
    assertThat(response.getResults()).containsExactly(result);
  }

  @Test
  void destroy_shouldThrowTerminationException_whenCloudResourceStepExceptionThrown()
      throws ExecutionException, InterruptedException {

    CloudResourceStepException stepEx = new CloudResourceStepException("Step failed");
    when(stepExecutor.execute(anyList(), anyBoolean())).thenThrow(stepEx);

    CloudProvisionStep<EksClusterOutput> step = mock(CloudProvisionStep.class);

    assertThatThrownBy(() -> destroyer.destroy(List.of(step), true))
        .isInstanceOf(CloudProvisioningTerminationException.class)
        .hasMessage("Step failed")
        .hasCause(stepEx);
  }

  @Test
  void destroy_shouldWrapUnexpectedExecutionException() throws ExecutionException, InterruptedException {

    ExecutionException ex = new ExecutionException(new IllegalArgumentException("exception"));
    when(stepExecutor.execute(anyList(), anyBoolean())).thenThrow(ex);

    CloudProvisionStep<EksClusterOutput> step = mock(CloudProvisionStep.class);

    assertThatThrownBy(() -> destroyer.destroy(List.of(step), true))
        .isInstanceOf(CloudProvisioningTerminationException.class)
        .hasMessageContaining("Unexpected exception occurred during async execution")
        .hasCauseInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void destroy_shouldWrapInterruptedException() throws ExecutionException, InterruptedException {

    InterruptedException ex = new InterruptedException("interrupted");
    when(stepExecutor.execute(anyList(), anyBoolean())).thenThrow(ex);

    CloudProvisionStep<EksClusterOutput> step = mock(CloudProvisionStep.class);

    assertThatThrownBy(() -> destroyer.destroy(List.of(step), false))
        .isInstanceOf(CloudProvisioningTerminationException.class)
        .hasMessageContaining("interrupted")
        .hasCause(ex);
  }




}