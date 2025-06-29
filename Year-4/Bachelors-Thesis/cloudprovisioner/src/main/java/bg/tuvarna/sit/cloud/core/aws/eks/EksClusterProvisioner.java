package bg.tuvarna.sit.cloud.core.aws.eks;

import bg.tuvarna.sit.cloud.core.aws.eks.client.EksSafeClient;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.model.CloudProvisionerSuccessfulResponse;
import bg.tuvarna.sit.cloud.core.provisioner.CloudResourceProvisioner;
import bg.tuvarna.sit.cloud.core.provisioner.model.CloudResourceType;
import bg.tuvarna.sit.cloud.core.provisioner.executor.CloudStepStrategyExecutor;
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
public class EksClusterProvisioner implements CloudResourceProvisioner<EksClusterOutput> {

  private final EksSafeClient eks;
  private final CloudStepStrategyExecutor<EksClusterOutput> stepExecutor;
  private final StepResult<EksClusterOutput> metadata;

  @Inject
  public EksClusterProvisioner(EksSafeClient eks, CloudStepStrategyExecutor<EksClusterOutput> stepExecutor,
                               StepResult<EksClusterOutput> metadata) {
    this.eks = eks;
    this.stepExecutor = stepExecutor;
    this.metadata = metadata;
  }

  @Override
  public CloudProvisionerSuccessfulResponse<EksClusterOutput> provision(
      List<CloudProvisionStep<EksClusterOutput>> resources) throws CloudProvisioningTerminationException {

    long startTime = System.nanoTime();
    String cluster = (String) metadata.getOutputs().get(EksClusterOutput.NAME);
    String arn = (String) metadata.getOutputs().get(EksClusterOutput.ARN);

    if (resources.isEmpty()) {
      return new CloudProvisionerSuccessfulResponse<>(CloudResourceType.EKS, cluster, arn, Collections.emptyList());
    }

    try (eks) {

      List<StepResult<EksClusterOutput>> results = stepExecutor.execute(resources, CloudProvisionStep::apply);

      long endTime = System.nanoTime();
      long durationMs = (endTime - startTime) / 1_000_000;
      // TODO [Maybe] Create a configuration file for log messages
      log.info("EKS provisioner for cluster '{}' finished in {} ms", cluster, durationMs);

      return new CloudProvisionerSuccessfulResponse<>(CloudResourceType.EKS, cluster, arn, results);

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
