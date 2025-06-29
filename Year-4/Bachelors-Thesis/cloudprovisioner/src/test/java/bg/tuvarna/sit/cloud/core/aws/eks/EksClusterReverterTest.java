package bg.tuvarna.sit.cloud.core.aws.eks;

import bg.tuvarna.sit.cloud.core.aws.eks.client.EksSafeClient;
import bg.tuvarna.sit.cloud.core.aws.eks.step.EksClusterPersistentMetadataStep;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.model.CloudProvisionerSuccessfulResponse;
import bg.tuvarna.sit.cloud.core.provisioner.model.CloudResourceType;
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
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EksClusterReverterTest {

  @Mock
  private EksSafeClient eks;

  @Mock
  private CloudStepRevertExecutor<EksClusterOutput> stepExecutor;

  private StepResult<EksClusterOutput> metadata;

  private EksClusterReverter reverter;

  @BeforeEach
  void setUp() {
    metadata = StepResult.<EksClusterOutput>builder()
        .stepName(EksClusterPersistentMetadataStep.class.getName())
        .put(EksClusterOutput.NAME, "test-cluster")
        .put(EksClusterOutput.ARN, "arn:aws:eks:eu-west-1:000000000000:cluster/test-cluster")
        .build();

    reverter = new EksClusterReverter(eks, stepExecutor, metadata);
  }

  @Test
  void revert_shouldReturnEmptyResponse_whenResourcesAreEmpty() throws Exception {

    CloudProvisionerSuccessfulResponse<EksClusterOutput> response = reverter.revert(List.of(), List.of());

    assertThat(response.getResourceType()).isEqualTo(CloudResourceType.EKS);
    assertThat(response.getResourceName()).isEqualTo("test-cluster");
    assertThat(response.getResourceId()).isEqualTo("arn:aws:eks:eu-west-1:000000000000:cluster/test-cluster");
    assertThat(response.getResults()).isEmpty();
  }

  @Test
  void revert_shouldReturnResults_whenExecutionSucceeds() throws Exception {

    CloudProvisionStep<EksClusterOutput> step = mock(CloudProvisionStep.class);
    StepResult<EksClusterOutput> previousStep = StepResult.<EksClusterOutput>builder().stepName("step").build();
    StepResult<EksClusterOutput> result = StepResult.<EksClusterOutput>builder().stepName("step").build();

    when(stepExecutor.execute(List.of(step), List.of(previousStep))).thenReturn(List.of(result));

    CloudProvisionerSuccessfulResponse<EksClusterOutput> response = reverter.revert(List.of(step), List.of(previousStep));

    assertThat(response.getResourceType()).isEqualTo(CloudResourceType.EKS);
    assertThat(response.getResourceName()).isEqualTo("test-cluster");
    assertThat(response.getResourceId()).isEqualTo("arn:aws:eks:eu-west-1:000000000000:cluster/test-cluster");
    assertThat(response.getResults()).containsExactly(result);
  }

  @Test
  void revert_shouldThrowTerminationException_whenStepThrowsCloudResourceStepException()
      throws ExecutionException, InterruptedException {

    CloudResourceStepException exception = new CloudResourceStepException("revert failed");
    CloudProvisionStep<EksClusterOutput> step = mock(CloudProvisionStep.class);

    when(stepExecutor.execute(anyList(), anyList())).thenThrow(exception);

    assertThatThrownBy(() -> reverter.revert(List.of(step), List.of()))
        .isInstanceOf(CloudProvisioningTerminationException.class)
        .hasMessage("revert failed")
        .hasCause(exception);
  }

  @Test
  void revert_shouldWrapUnexpectedExecutionException() throws ExecutionException, InterruptedException {

    IllegalStateException unexpected = new IllegalStateException("boom");
    ExecutionException ex = new ExecutionException(unexpected);
    CloudProvisionStep<EksClusterOutput> step = mock(CloudProvisionStep.class);

    when(stepExecutor.execute(anyList(), anyList())).thenThrow(ex);

    assertThatThrownBy(() -> reverter.revert(List.of(step), List.of()))
        .isInstanceOf(CloudProvisioningTerminationException.class)
        .hasMessageContaining("Unexpected exception occurred during async execution")
        .hasCause(unexpected);
  }

  @Test
  void revert_shouldWrapInterruptedException() throws ExecutionException, InterruptedException {

    InterruptedException ex = new InterruptedException("interrupted");
    CloudProvisionStep<EksClusterOutput> step = mock(CloudProvisionStep.class);

    when(stepExecutor.execute(anyList(), anyList())).thenThrow(ex);

    assertThatThrownBy(() -> reverter.revert(List.of(step), List.of()))
        .isInstanceOf(CloudProvisioningTerminationException.class)
        .hasMessageContaining("interrupted")
        .hasCause(ex);
  }

}

