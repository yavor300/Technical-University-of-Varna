package bg.tuvarna.sit.cloud.core.aws.s3;

import bg.tuvarna.sit.cloud.core.provisioner.ProvisionOrder;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

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

    try {
      s3Client.headBucket(HeadBucketRequest.builder()
          .bucket(bucketName)
          .build());
      log.info("Verified that bucket '{}' exists", bucketName);
    } catch (S3Exception e) {
      log.error("Failed to verify the existence of bucket '{}'", bucketName, e);
      throw new RuntimeException("Bucket creation failed verification", e);
    }

    return StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .put(S3Output.NAME, bucketName)
        .build();
  }

  @Override
  public StepResult<S3Output> generateDesiredState(S3BucketConfig config) {

    return StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .put(S3Output.NAME, config.getName())
        .build();
  }
}
