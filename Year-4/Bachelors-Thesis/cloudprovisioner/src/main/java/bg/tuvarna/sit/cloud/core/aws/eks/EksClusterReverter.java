package bg.tuvarna.sit.cloud.core.aws.eks;

import bg.tuvarna.sit.cloud.core.aws.eks.client.EksSafeClient;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.model.CloudProvisionerSuccessfulResponse;
import bg.tuvarna.sit.cloud.core.provisioner.CloudResourceReverter;
import bg.tuvarna.sit.cloud.core.provisioner.model.CloudResourceType;
import bg.tuvarna.sit.cloud.core.provisioner.executor.CloudStepRevertExecutor;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudProvisioningTerminationException;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;
import bg.tuvarna.sit.cloud.utils.Slf4jLoggingUtil;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Singleton
public class EksClusterReverter implements CloudResourceReverter<EksClusterOutput> {

  private final EksSafeClient eks;
  private final CloudStepRevertExecutor<EksClusterOutput> stepExecutor;
  private final StepResult<EksClusterOutput> metadata;

  @Inject
  public EksClusterReverter(EksSafeClient eks, CloudStepRevertExecutor<EksClusterOutput> stepExecutor,
                            StepResult<EksClusterOutput> metadata) {
    this.eks = eks;
    this.stepExecutor = stepExecutor;
    this.metadata = metadata;
  }

  @Override
  public CloudProvisionerSuccessfulResponse<EksClusterOutput> revert(
      List<CloudProvisionStep<EksClusterOutput>> resources,
      List<StepResult<EksClusterOutput>> previous)
      throws CloudProvisioningTerminationException {

    long startTime = System.nanoTime();
    String cluster = (String) metadata.getOutputs().get(EksClusterOutput.NAME);
    String arn = (String) metadata.getOutputs().get(EksClusterOutput.ARN);

    try (eks) {

      if (resources.isEmpty()) {
        return new CloudProvisionerSuccessfulResponse<>(CloudResourceType.EKS, cluster, arn, Collections.emptyList());
      }

      List<StepResult<EksClusterOutput>> results = stepExecutor.execute(resources, previous);

      long endTime = System.nanoTime();
      long durationMs = (endTime - startTime) / 1_000_000;
      log.info("EKS cluster reverter for '{}' finished in {} ms", cluster, durationMs);

      return new CloudProvisionerSuccessfulResponse<>(CloudResourceType.EKS, cluster, arn, results);

    } catch (CloudResourceStepException e) {

      throw new CloudProvisioningTerminationException(e.getMessage(), e);

    } catch (InterruptedException | ExecutionException e) {

      Throwable cause = e.getCause();

      if (!(cause instanceof CloudResourceStepException)) {
        String msg = "Unexpected exception occurred during async execution: %s".formatted(
            cause != null ? cause.getClass().getSimpleName() + " - " + cause.getMessage() : e.getMessage()
        );
        log.debug(Slf4jLoggingUtil.DEBUG_PREFIX + "{}", msg, cause != null ? cause : e);
        throw new CloudProvisioningTerminationException(msg, cause != null ? cause : e);
      }

      throw new CloudProvisioningTerminationException(cause.getMessage(), cause);
    }
  }
}
