package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.S3OwnershipType;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionAsync;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import lombok.extern.slf4j.Slf4j;

@ProvisionAsync
@Slf4j
public class S3OwnershipControlsStep implements S3ProvisionStep {

  @Override
  public StepResult<S3Output> apply(S3SafeClient s3Client, S3BucketConfig config) {

    S3OwnershipType ownershipType = config.getOwnershipControls();

    // TODO Check for default value and set it in the step
    StepResult.Builder<S3Output> result = StepResult.<S3Output>builder()
        .stepName(this.getClass().getName());

    if (ownershipType == null) {
      return result.build();
    }

    String bucketName = config.getName();
    s3Client.putBucketOwnershipControls(bucketName, ownershipType.toSdkType());

    String actualValue = s3Client.getBucketOwnershipControls(bucketName)
        .ownershipControls()
        .rules()
        .getFirst()
        .objectOwnershipAsString();

    return result.put(S3Output.VALUE_NODE, actualValue).build();
  }

  @Override
  public StepResult<S3Output> generateDesiredState(S3BucketConfig config) {
    return null;
  }

}
