package bg.tuvarna.sit.cloud.core.aws.eks;

import bg.tuvarna.sit.cloud.core.aws.eks.client.EksSafeClient;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.model.CloudProvisionerSuccessfulResponse;
import bg.tuvarna.sit.cloud.core.provisioner.model.CloudResourceType;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EksClusterProvisionerTest {

  @Mock
  private EksSafeClient eks;

  @Mock
  private CloudStepStrategyExecutor<EksClusterOutput> stepExecutor;

  private StepResult<EksClusterOutput> metadata;

  private EksClusterProvisioner provisioner;

  @BeforeEach
  void setUp() {
    metadata = StepResult.<EksClusterOutput>builder()
        .stepName("EksClusterMetadata")
        .put(EksClusterOutput.NAME, "test-cluster")
        .put(EksClusterOutput.ARN, "arn:aws:eks:eu-west-1:000000000000:cluster/test-cluster")
        .build();

    provisioner = new EksClusterProvisioner(eks, stepExecutor, metadata);
  }

  @Test
  void provision_shouldReturnEmptyResponse_whenResourcesAreEmpty() {

    CloudProvisionerSuccessfulResponse<EksClusterOutput> response = provisioner.provision(List.of());

    assertThat(response.getResourceType()).isEqualTo(CloudResourceType.EKS);
    assertThat(response.getResourceName()).isEqualTo("test-cluster");
    assertThat(response.getResourceId()).isEqualTo("arn:aws:eks:eu-west-1:000000000000:cluster/test-cluster");
    assertThat(response.getResults()).isEmpty();
  }

  @Test
  void provision_shouldReturnResults_whenExecutionSucceeds() throws Exception {

    CloudProvisionStep<EksClusterOutput> step = mock(CloudProvisionStep.class);
    StepResult<EksClusterOutput> stepResult = StepResult.<EksClusterOutput>builder().stepName("step").build();

    when(stepExecutor.execute(anyList(), any())).thenReturn(List.of(stepResult));

    CloudProvisionerSuccessfulResponse<EksClusterOutput> response = provisioner.provision(List.of(step));

    assertThat(response.getResourceType()).isEqualTo(CloudResourceType.EKS);
    assertThat(response.getResourceName()).isEqualTo("test-cluster");
    assertThat(response.getResourceId()).isEqualTo("arn:aws:eks:eu-west-1:000000000000:cluster/test-cluster");
    assertThat(response.getResults()).containsExactly(stepResult);
  }

  @Test
  void provision_shouldThrowTerminationException_whenStepThrowsCloudResourceStepException()
      throws ExecutionException, InterruptedException {

    CloudResourceStepException stepException = new CloudResourceStepException("step failed");

    CloudProvisionStep<EksClusterOutput> step = mock(CloudProvisionStep.class);
    when(stepExecutor.execute(anyList(), any())).thenThrow(stepException);

    assertThatThrownBy(() -> provisioner.provision(List.of(step)))
        .isInstanceOf(CloudProvisioningTerminationException.class)
        .hasMessage("step failed")
        .hasCause(stepException);
  }

  @Test
  void provision_shouldWrapUnexpectedExecutionException() throws ExecutionException, InterruptedException {

    IllegalArgumentException cause = new IllegalArgumentException("unexpected");
    ExecutionException ex = new ExecutionException(cause);

    CloudProvisionStep<EksClusterOutput> step = mock(CloudProvisionStep.class);
    when(stepExecutor.execute(anyList(), any())).thenThrow(ex);

    assertThatThrownBy(() -> provisioner.provision(List.of(step)))
        .isInstanceOf(CloudProvisioningTerminationException.class)
        .hasMessageContaining("Unexpected exception occurred during async execution")
        .hasCause(cause);
  }

  @Test
  void provision_shouldWrapInterruptedException() throws ExecutionException, InterruptedException {

    InterruptedException ex = new InterruptedException("interrupted");

    CloudProvisionStep<EksClusterOutput> step = mock(CloudProvisionStep.class);
    when(stepExecutor.execute(anyList(), any())).thenThrow(ex);

    assertThatThrownBy(() -> provisioner.provision(List.of(step)))
        .isInstanceOf(CloudProvisioningTerminationException.class)
        .hasMessageContaining("interrupted")
        .hasCause(ex);
  }


}