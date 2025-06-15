package bg.tuvarna.sit.cloud.core.aws.s3;

import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisioningSuccessfulResponse;
import bg.tuvarna.sit.cloud.core.provisioner.CloudResourceProvisioner;
import bg.tuvarna.sit.cloud.core.provisioner.CloudResourceType;
import bg.tuvarna.sit.cloud.core.provisioner.CloudStepExecutor;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;
import bg.tuvarna.sit.cloud.exception.CloudProvisioningTerminationException;

import jakarta.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
public class S3BucketProvisioner implements CloudResourceProvisioner<S3Output> {

  private final S3SafeClient s3;
  private final CloudStepExecutor<S3Output> stepExecutor;
  private final StepResult<S3Output> metadata;

  @Inject
  public S3BucketProvisioner(S3SafeClient s3, CloudStepExecutor<S3Output> stepExecutor,
                             StepResult<S3Output> metadata) {
    this.s3 = s3;
    this.stepExecutor = stepExecutor;
    this.metadata = metadata;
  }

  @Override
  public CloudProvisioningSuccessfulResponse<S3Output> provision(List<CloudProvisionStep<S3Output>> resources)
      throws CloudProvisioningTerminationException {

    long startTime = System.nanoTime();
    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);
    String arn = (String) metadata.getOutputs().get(S3Output.ARN);

    if (resources.isEmpty()) {
      return new CloudProvisioningSuccessfulResponse<>(CloudResourceType.S3, bucket, arn, Collections.emptyList());
    }

    try (s3) {

      List<StepResult<S3Output>> results = stepExecutor.execute(resources, CloudProvisionStep::apply);

      long endTime = System.nanoTime();
      long durationMs = (endTime - startTime) / 1_000_000;
      // TODO [Maybe] Create a configuration file for log messages
      log.info("S3 provisioner for bucket '{}' finished in {} ms", bucket, durationMs);

      return new CloudProvisioningSuccessfulResponse<>(CloudResourceType.S3, bucket, arn, results);

    } catch (CloudResourceStepException e) {
      throw new CloudProvisioningTerminationException(e.getMessage(), e);

    } catch (InterruptedException | ExecutionException e) {

      Throwable cause = e.getCause();

      if (!(cause instanceof CloudResourceStepException)) {
        String msg = "Unexpected exception occurred during async execution: %s"
            .formatted(cause != null ? cause.getClass().getSimpleName() + " - " + cause.getMessage() : e.getMessage());
        log.debug(msg, e);
        throw new CloudProvisioningTerminationException(msg, e);
      }

      throw new CloudProvisioningTerminationException(cause.getMessage(), cause);
    }
  }
}
