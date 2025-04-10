package bg.tuvarna.sit.cloud.core.aws.s3;

import bg.tuvarna.sit.cloud.core.aws.s3.config.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisioningResponse;
import bg.tuvarna.sit.cloud.core.provisioner.CloudResourceProvisioner;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;

@Slf4j
@AllArgsConstructor
public class S3BucketProvisioner implements CloudResourceProvisioner<S3BucketConfig> {

  private final S3ProvisioningContext context;
  private final S3StepExecutor stepExecutor;

  @Override
  public CloudProvisioningResponse provision(S3BucketConfig config) throws InterruptedException {

    long startTime = System.nanoTime();

    String bucketName = config.getName();
    log.info("Starting provisioning of S3 bucket '{}'", bucketName);

    try (S3Client s3Client = S3Client.builder()
        .credentialsProvider(StaticCredentialsProvider.create(context.getCredentials()))
        .endpointOverride(context.getEndpoint())
        .region(context.getRegion())
        .forcePathStyle(true)
        .build()) {

      log.info("Sending request to create S3 bucket '{}'", bucketName);
      s3Client.createBucket(CreateBucketRequest.builder().bucket(bucketName).build());

      stepExecutor.execute(s3Client, config);

      log.debug("Verifying bucket existence with HeadBucket request");
      s3Client.headBucket(HeadBucketRequest.builder().bucket(bucketName).build());
      log.info("S3 bucket '{}' exists and is active", bucketName);

      long endTime = System.nanoTime();
      long durationMs = (endTime - startTime) / 1_000_000;
      log.info("Provisioning completed for bucket '{}' in {} ms", bucketName, durationMs);

      String arn = String.format("arn:aws:s3:::%s", bucketName);
      return new CloudProvisioningResponse("S3", bucketName, arn);

    } catch (Exception e) {
      log.error("Failed to provision S3 bucket '{}'", bucketName, e);
      throw e;
    }
  }
}
