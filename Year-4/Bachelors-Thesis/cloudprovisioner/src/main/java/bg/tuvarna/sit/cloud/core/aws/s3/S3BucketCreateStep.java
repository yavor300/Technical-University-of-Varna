package bg.tuvarna.sit.cloud.core.aws.s3;

import bg.tuvarna.sit.cloud.core.provisioner.ProvisionOrder;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;

@Slf4j
@ProvisionOrder(0)
public class S3BucketCreateStep implements S3ProvisionStep {

  @Override
  public StepResult<S3Output> apply(S3Client s3Client, S3BucketConfig config) {

    String bucketName = config.getName();
    log.info("Creating S3 bucket '{}'", bucketName);

    s3Client.createBucket(CreateBucketRequest.builder()
        .bucket(bucketName)
        .build());

    log.info("S3 bucket '{}' created successfully", bucketName);

    return StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .put(S3Output.NAME, bucketName)
        .build();
  }
}
