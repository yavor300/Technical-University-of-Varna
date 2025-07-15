package bg.tuvarna.sit.cloud.core.aws.eks;

import bg.tuvarna.sit.cloud.core.aws.eks.client.EksSafeClient;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.model.CloudProvisionerSuccessfulResponse;
import bg.tuvarna.sit.cloud.core.provisioner.CloudResourceDestroyer;
import bg.tuvarna.sit.cloud.core.provisioner.model.CloudResourceType;
import bg.tuvarna.sit.cloud.core.provisioner.executor.CloudStepDeleteExecutor;
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
public class EksClusterDestroyer implements CloudResourceDestroyer<EksClusterOutput> {

  private final EksSafeClient eks;
  private final CloudStepDeleteExecutor<EksClusterOutput> stepExecutor;
  private final StepResult<EksClusterOutput> metadata;

  @Inject
  public EksClusterDestroyer(EksSafeClient eks, CloudStepDeleteExecutor<EksClusterOutput> stepExecutor,
                             StepResult<EksClusterOutput> metadata) {
    this.eks = eks;
    this.stepExecutor = stepExecutor;
    this.metadata = metadata;
  }

  @Override
  public CloudProvisionerSuccessfulResponse<EksClusterOutput> destroy(List<CloudProvisionStep<EksClusterOutput>> steps,
                                                                      boolean enforcePreventDestroy)
      throws CloudProvisioningTerminationException {

    long startTime = System.nanoTime();
    String name = (String) metadata.getOutputs().get(EksClusterOutput.NAME);
    String arn = (String) metadata.getOutputs().get(EksClusterOutput.ARN);

    if (steps.isEmpty()) {
      return new CloudProvisionerSuccessfulResponse<>(CloudResourceType.EKS, name, arn, Collections.emptyList());
    }

    try (eks) {

      List<StepResult<EksClusterOutput>> results = stepExecutor.execute(steps, enforcePreventDestroy);

      long endTime = System.nanoTime();
      long durationMs = (endTime - startTime) / 1_000_000;
      log.info("S3 bucket destroyer for '{}' finished in {} ms", name, durationMs);

      return new CloudProvisionerSuccessfulResponse<>(CloudResourceType.EKS, name, arn, results);

    } catch (CloudResourceStepException e) {
      throw new CloudProvisioningTerminationException(e.getMessage(), e);

    } catch (InterruptedException | ExecutionException e) {

      Throwable cause = e.getCause();

      if (!(cause instanceof CloudResourceStepException)) {
        String msg = "Unexpected exception occurred during async execution: %s"
            .formatted(cause != null ? cause.getClass().getSimpleName() + " - " + cause.getMessage() : e.getMessage());
        log.debug(Slf4jLoggingUtil.DEBUG_PREFIX + "{}", msg, cause != null ? cause : e);
        throw new CloudProvisioningTerminationException(msg, cause != null ? cause : e);
      }

      throw new CloudProvisioningTerminationException(cause.getMessage(), cause);
    }
  }
}
