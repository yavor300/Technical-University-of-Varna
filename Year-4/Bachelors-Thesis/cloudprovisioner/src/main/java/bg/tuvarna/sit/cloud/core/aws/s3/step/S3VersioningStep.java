package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionAsync;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.model.BucketVersioningStatus;

import static software.amazon.awssdk.services.s3.model.BucketVersioningStatus.ENABLED;
import static software.amazon.awssdk.services.s3.model.BucketVersioningStatus.SUSPENDED;

@Slf4j
@ProvisionAsync
public class S3VersioningStep implements S3ProvisionStep {

  @Override
  public StepResult<S3Output> apply(S3SafeClient s3Client, S3BucketConfig config) {

    String versioning = config.getVersioning();

    if (versioning == null || versioning.isEmpty()) {
      return buildVersioningStepResult(null);
    }

    BucketVersioningStatus status = ENABLED.toString().equalsIgnoreCase(versioning) ? ENABLED : SUSPENDED;
    String bucketName = config.getName();

    s3Client.putBucketVersioning(bucketName, status);

    BucketVersioningStatus verifiedStatus = s3Client.getBucketVersioning(bucketName).status();
    BucketVersioningStatus actualStatus = verifiedStatus != null ? verifiedStatus : SUSPENDED;

    return StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .put(S3Output.VERSIONING_STATUS, actualStatus.toString())
        .build();
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
