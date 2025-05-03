package bg.tuvarna.sit.cloud.core.aws.s3;

import bg.tuvarna.sit.cloud.core.provisioner.ProvisionAsync;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetBucketOwnershipControlsRequest;
import software.amazon.awssdk.services.s3.model.GetBucketOwnershipControlsResponse;
import software.amazon.awssdk.services.s3.model.OwnershipControlsRule;
import software.amazon.awssdk.services.s3.model.PutBucketOwnershipControlsRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@ProvisionAsync
@Slf4j
public class S3OwnershipControlsStep implements S3ProvisionStep {

  @Override
  public StepResult<S3Output> apply(S3Client s3Client, S3BucketConfig config) {

    String bucketName = config.getName();
    S3OwnershipType ownershipType = config.getOwnershipControls();

    StepResult.Builder<S3Output> result = StepResult.<S3Output>builder()
        .stepName(this.getClass().getName());

    if (ownershipType == null) {
      return result.build();
    }

    PutBucketOwnershipControlsRequest request = PutBucketOwnershipControlsRequest.builder()
        .bucket(bucketName)
        .ownershipControls(
            software.amazon.awssdk.services.s3.model.OwnershipControls.builder()
                .rules(OwnershipControlsRule.builder()
                    .objectOwnership(ownershipType.toSdkType())
                    .build())
                .build()
        ).build();

    s3Client.putBucketOwnershipControls(request);
    log.info("Set ownership controls '{}' for bucket '{}'", ownershipType.getValue(), bucketName);

    try {
      GetBucketOwnershipControlsResponse response = s3Client.getBucketOwnershipControls(
          GetBucketOwnershipControlsRequest.builder().bucket(bucketName).build()
      );

      String actualValue = response.ownershipControls()
          .rules()
          .getFirst()
          .objectOwnershipAsString();

      log.info("Confirmed ownership controls for bucket '{}': {}", bucketName, actualValue);

      return result.put(S3Output.VALUE_NODE, actualValue).build();

      // TODO Apply better exception handling for all steps
    } catch (S3Exception e) {
      log.warn("Failed to retrieve ownership controls for bucket '{}': {}", bucketName,
          e.awsErrorDetails().errorMessage());
      return result.build();
    }
  }

  @Override
  public StepResult<S3Output> generateDesiredState(S3BucketConfig config) {
    return null;
  }
}
