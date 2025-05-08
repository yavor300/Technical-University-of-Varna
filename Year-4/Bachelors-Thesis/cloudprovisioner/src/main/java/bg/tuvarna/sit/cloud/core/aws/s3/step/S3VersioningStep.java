package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.aws.s3.util.S3VersioningResultBuilder;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionAsync;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.model.BucketVersioningStatus;
import software.amazon.awssdk.services.s3.model.GetBucketVersioningResponse;

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

    s3Client.putVersioning(bucketName, status);

    return S3VersioningResultBuilder.fromResponse(s3Client.getVersioning(bucketName));
  }

  @Override
  public StepResult<S3Output> generateDesiredState(S3BucketConfig config) {

    return buildVersioningStepResult(config.getVersioning());
  }

  private StepResult<S3Output> buildVersioningStepResult(String versioning) {

    StepResult.Builder<S3Output> result = StepResult.<S3Output>builder()
        .put(S3Output.VERSIONING_STATUS, SUSPENDED.toString())
        .stepName(this.getClass().getName());

    if (versioning == null || versioning.isEmpty()) {
      return result.build();
    }

    BucketVersioningStatus status = ENABLED.toString().equalsIgnoreCase(versioning) ? ENABLED : SUSPENDED;

    result.put(S3Output.VERSIONING_STATUS, status.toString());

    return result.build();
  }

  @Override
  public StepResult<S3Output> getCurrentState(S3SafeClient client, S3BucketConfig config) {

    GetBucketVersioningResponse response = client.getVersioning(config.getName());

    return S3VersioningResultBuilder.fromResponse(response);
  }

}
