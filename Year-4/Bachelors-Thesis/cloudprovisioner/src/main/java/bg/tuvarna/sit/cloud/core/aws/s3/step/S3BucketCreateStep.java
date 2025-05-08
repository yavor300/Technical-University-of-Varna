package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionOrder;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import bg.tuvarna.sit.cloud.exception.BucketCreationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ProvisionOrder(0)
public class S3BucketCreateStep implements S3ProvisionStep {

  @Override
  public StepResult<S3Output> apply(S3SafeClient s3Client, S3BucketConfig config) throws BucketCreationException {

    String bucketName = config.getName();

    s3Client.create(bucketName);
    s3Client.head(bucketName);

    return StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .put(S3Output.NAME, bucketName)
        .put(S3Output.REGION, config.getRegion())
        .build();
  }

  @Override
  public StepResult<S3Output> generateDesiredState(S3BucketConfig config) {

    return StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .put(S3Output.NAME, config.getName())
        .put(S3Output.REGION, config.getRegion())
        .build();
  }

  @Override
  public StepResult<S3Output> getCurrentState(S3SafeClient client, S3BucketConfig config) {

    return StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .put(S3Output.NAME, config.getName())
        .put(S3Output.REGION, config.getRegion())
        .build();
  }
}
