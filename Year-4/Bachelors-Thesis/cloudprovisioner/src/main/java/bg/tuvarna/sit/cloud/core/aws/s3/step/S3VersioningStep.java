package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.aws.s3.util.S3VersioningResultBuilder;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionAsync;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import bg.tuvarna.sit.cloud.exception.BucketVersioningProvisioningException;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.model.BucketVersioningStatus;
import software.amazon.awssdk.services.s3.model.GetBucketVersioningResponse;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;

import static software.amazon.awssdk.services.s3.model.BucketVersioningStatus.ENABLED;
import static software.amazon.awssdk.services.s3.model.BucketVersioningStatus.SUSPENDED;

@Slf4j
@ProvisionAsync
public class S3VersioningStep extends S3ProvisionStep {

  @Inject
  public S3VersioningStep(S3SafeClient s3, S3BucketConfig config) {
    super(s3, config);
  }

  @Override
  public StepResult<S3Output> apply() {

    String versioning = config.getVersioning();

    if (versioning == null || versioning.isEmpty()) {
      return buildVersioningStepResult(null);
    }

    BucketVersioningStatus status = ENABLED.toString().equalsIgnoreCase(versioning) ? ENABLED : SUSPENDED;
    String bucketName = config.getName();

    s3.putVersioning(bucketName, status);

    return S3VersioningResultBuilder.fromResponse(s3.getVersioning(bucketName, false));
  }

  @Override
  public StepResult<S3Output> generateDesiredState() {

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
  public StepResult<S3Output> getCurrentState() {

    try {
      GetBucketVersioningResponse response = s3.getVersioning(config.getName(), true);
      return S3VersioningResultBuilder.fromResponse(response);

    } catch (BucketVersioningProvisioningException e) {
      if (e.getCause() instanceof NoSuchBucketException) {
        return StepResult.<S3Output>builder()
            .stepName(S3VersioningStep.class.getName())
            .build();
      }
      return null;
    }
  }

}
