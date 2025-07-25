package bg.tuvarna.sit.cloud.core.aws.s3;

import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.model.CloudProvisionerSuccessfulResponse;
import bg.tuvarna.sit.cloud.core.provisioner.CloudResourceDestroyer;
import bg.tuvarna.sit.cloud.core.provisioner.model.CloudResourceType;
import bg.tuvarna.sit.cloud.core.provisioner.executor.CloudStepDeleteExecutor;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudProvisioningTerminationException;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;
import bg.tuvarna.sit.cloud.utils.Slf4jLoggingUtil;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Singleton
public class S3BucketDestroyer implements CloudResourceDestroyer<S3Output> {

  private final S3SafeClient s3;
  private final CloudStepDeleteExecutor<S3Output> stepExecutor;
  private final StepResult<S3Output> metadata;

  @Inject
  public S3BucketDestroyer(S3SafeClient s3, CloudStepDeleteExecutor<S3Output> stepExecutor,
                           StepResult<S3Output> metadata) {
    this.s3 = s3;
    this.stepExecutor = stepExecutor;
    this.metadata = metadata;
  }

  @Override
  public CloudProvisionerSuccessfulResponse<S3Output> destroy(List<CloudProvisionStep<S3Output>> steps,
                                                              boolean enforcePreventDestroy)
      throws CloudProvisioningTerminationException {

    long startTime = System.nanoTime();
    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);
    String arn = (String) metadata.getOutputs().get(S3Output.ARN);

    if (steps.isEmpty()) {
      return new CloudProvisionerSuccessfulResponse<>(CloudResourceType.S3, bucket, arn, Collections.emptyList());
    }

    try (s3) {

      List<StepResult<S3Output>> results = stepExecutor.execute(steps, enforcePreventDestroy);

      long endTime = System.nanoTime();
      long durationMs = (endTime - startTime) / 1_000_000;
      log.info("S3 bucket destroyer for '{}' finished in {} ms", bucket, durationMs);

      return new CloudProvisionerSuccessfulResponse<>(CloudResourceType.S3, bucket, arn, results);

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
