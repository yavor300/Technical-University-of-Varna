package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.S3OwnershipType;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.aws.s3.util.S3OwnershipControlsResultBuilder;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionAsync;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import bg.tuvarna.sit.cloud.exception.BucketOwnershipVerificationException;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.model.GetBucketOwnershipControlsResponse;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;

@ProvisionAsync
@Slf4j
public class S3OwnershipControlsStep extends S3ProvisionStep {
  
  @Inject
  public S3OwnershipControlsStep(S3SafeClient s3, S3BucketConfig config) {
    super(s3, config);
  }

  @Override
  public StepResult<S3Output> apply() {

    S3OwnershipType ownershipType = config.getOwnershipControls();

    StepResult.Builder<S3Output> result = StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .put(S3Output.TYPE, S3OwnershipType.BUCKET_OWNER_ENFORCED.getValue());

    if (ownershipType == null) {
      return result.build();
    }

    String bucketName = config.getName();
    s3.putOwnershipControls(bucketName, ownershipType.toSdkType());

    GetBucketOwnershipControlsResponse response = s3.getOwnershipControls(bucketName, false);

    return S3OwnershipControlsResultBuilder.fromResponse(response);
  }

  @Override
  public StepResult<S3Output> generateDesiredState() {

    StepResult.Builder<S3Output> result = StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .put(S3Output.TYPE, S3OwnershipType.BUCKET_OWNER_ENFORCED.getValue());

    S3OwnershipType configOwnershipControls = config.getOwnershipControls();
    if (configOwnershipControls != null) {
      result.put(S3Output.TYPE, configOwnershipControls.getValue());
    }

    return result.build();
  }

  @Override
  public StepResult<S3Output> getCurrentState() {

    try {
      GetBucketOwnershipControlsResponse response = s3.getOwnershipControls(config.getName(), true);
      return S3OwnershipControlsResultBuilder.fromResponse(response);

    } catch (BucketOwnershipVerificationException e) {
      if (e.getCause() instanceof NoSuchBucketException) {
        return StepResult.<S3Output>builder()
            .stepName(S3OwnershipControlsStep.class.getName())
            .build();
      }
      return null;
    }
  }

}
