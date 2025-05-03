package bg.tuvarna.sit.cloud.core.aws.s3;

import bg.tuvarna.sit.cloud.core.provisioner.ProvisionAsync;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.BucketVersioningStatus;
import software.amazon.awssdk.services.s3.model.GetBucketVersioningRequest;
import software.amazon.awssdk.services.s3.model.GetBucketVersioningResponse;
import software.amazon.awssdk.services.s3.model.PutBucketVersioningRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.VersioningConfiguration;

import static software.amazon.awssdk.services.s3.model.BucketVersioningStatus.ENABLED;
import static software.amazon.awssdk.services.s3.model.BucketVersioningStatus.SUSPENDED;

@Slf4j
@ProvisionAsync
public class S3VersioningStep implements S3ProvisionStep {

  @Override
  public StepResult<S3Output> apply(S3Client s3Client, S3BucketConfig config) {

    String versioning = config.getVersioning();

    if (versioning == null || versioning.isEmpty()) {
      return buildVersioningStepResult(null);
    }

    BucketVersioningStatus status = ENABLED.toString().equalsIgnoreCase(versioning) ? ENABLED : SUSPENDED;

    // TODO Surround with try-catch and terminate the process if not provisioned correctly
    String bucketName = config.getName();
    s3Client.putBucketVersioning(PutBucketVersioningRequest.builder()
        .bucket(bucketName)
        .versioningConfiguration(VersioningConfiguration.builder()
            .status(status)
            .build())
        .build());
    log.info("Set versioning for bucket '{}'", bucketName);

    try {
      GetBucketVersioningResponse response = s3Client.getBucketVersioning(
          GetBucketVersioningRequest.builder().bucket(bucketName).build());

      BucketVersioningStatus actualStatus = response.status() != null ? response.status() : SUSPENDED;
      log.info("Retrieved versioning status '{}' from bucket '{}'", actualStatus, bucketName);

      return StepResult.<S3Output>builder()
          .stepName(this.getClass().getName())
          .put(S3Output.VERSIONING_STATUS, actualStatus.toString())
          .build();

    } catch (S3Exception | SdkClientException e) {
      log.warn("Failed to retrieve versioning status for bucket '{}'", bucketName, e);
      return buildVersioningStepResult(null);
    }
  }

  @Override
  public StepResult<S3Output> generateDesiredState(S3BucketConfig config) {

    return buildVersioningStepResult(config.getVersioning());
  }

  private StepResult<S3Output> buildVersioningStepResult(String versioning) {

    StepResult.Builder<S3Output> result = StepResult.<S3Output>builder()
        .stepName(this.getClass().getName());

    if (versioning == null || versioning.isEmpty()) {
      return result.build();
    }

    BucketVersioningStatus status = ENABLED.toString().equalsIgnoreCase(versioning) ? ENABLED : SUSPENDED;

    result.put(S3Output.VERSIONING_STATUS, status.toString());

    return result.build();
  }

}
