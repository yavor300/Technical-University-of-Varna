package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.S3OwnershipType;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.aws.s3.util.S3OwnershipControlsResultBuilder;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionAsync;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.model.GetBucketOwnershipControlsResponse;

@ProvisionAsync
@Slf4j
public class S3OwnershipControlsStep implements S3ProvisionStep {

  @Override
  public StepResult<S3Output> apply(S3SafeClient s3Client, S3BucketConfig config) {

    S3OwnershipType ownershipType = config.getOwnershipControls();

    StepResult.Builder<S3Output> result = StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .put(S3Output.TYPE, S3OwnershipType.BUCKET_OWNER_ENFORCED.getValue());

    if (ownershipType == null) {
      return result.build();
    }

    String bucketName = config.getName();
    s3Client.putOwnershipControls(bucketName, ownershipType.toSdkType());

    GetBucketOwnershipControlsResponse response = s3Client.getOwnershipControls(bucketName);

    return S3OwnershipControlsResultBuilder.fromResponse(response);
  }

  @Override
  public StepResult<S3Output> generateDesiredState(S3BucketConfig config) {

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
  public StepResult<S3Output> getCurrentState(S3SafeClient client, S3BucketConfig config) {

    GetBucketOwnershipControlsResponse response = client.getOwnershipControls(config.getName());

    return S3OwnershipControlsResultBuilder.fromResponse(response);
  }

}
