package bg.tuvarna.sit.cloud.core.aws.s3;

import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionerSuccessfulResponse;
import bg.tuvarna.sit.cloud.core.provisioner.CloudResourceReverter;
import bg.tuvarna.sit.cloud.core.provisioner.CloudResourceType;
import bg.tuvarna.sit.cloud.core.provisioner.CloudStepRevertExecutor;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudProvisioningTerminationException;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;

import jakarta.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
public class S3BucketReverter implements CloudResourceReverter<S3Output> {

  private final S3SafeClient s3;
  private final CloudStepRevertExecutor<S3Output> stepExecutor;
  private final StepResult<S3Output> metadata;

  @Inject
  public S3BucketReverter(S3SafeClient s3, CloudStepRevertExecutor<S3Output> stepExecutor,
                          StepResult<S3Output> metadata) {
    this.s3 = s3;
    this.stepExecutor = stepExecutor;
    this.metadata = metadata;
  }

  @Override
  public CloudProvisionerSuccessfulResponse<S3Output> revert(List<CloudProvisionStep<S3Output>> resources,
                                                             List<StepResult<S3Output>> previous)
      throws CloudProvisioningTerminationException {

    long startTime = System.nanoTime();
    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);
    String arn = (String) metadata.getOutputs().get(S3Output.ARN);

    try (s3) {

      if (resources.isEmpty()) {
        return new CloudProvisionerSuccessfulResponse<>(CloudResourceType.S3, bucket, arn, Collections.emptyList());
      }

      List<StepResult<S3Output>> results = stepExecutor.execute(resources, previous);

      long endTime = System.nanoTime();
      long durationMs = (endTime - startTime) / 1_000_000;
      log.info("S3 bucket reverter for '{}' finished in {} ms", bucket, durationMs);

      return new CloudProvisionerSuccessfulResponse<>(CloudResourceType.S3, bucket, arn, results);

    } catch (CloudResourceStepException e) {

      throw new CloudProvisioningTerminationException(e.getMessage(), e);

    } catch (InterruptedException | ExecutionException e) {

      Throwable cause = e.getCause();

      if (!(cause instanceof CloudResourceStepException)) {
        String msg = "Unexpected exception occurred during async execution: %s".formatted(
            cause != null ? cause.getClass().getSimpleName() + " - " + cause.getMessage() : e.getMessage()
        );
        log.debug(msg, e);
        throw new CloudProvisioningTerminationException(msg, e);
      }

      throw new CloudProvisioningTerminationException(cause.getMessage(), cause);
    }
  }
}
