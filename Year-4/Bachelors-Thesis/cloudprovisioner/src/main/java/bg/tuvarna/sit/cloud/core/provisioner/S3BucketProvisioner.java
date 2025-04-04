package bg.tuvarna.sit.cloud.core.provisioner;

import bg.tuvarna.sit.cloud.core.config.S3BucketConfig;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;

import java.net.URI;

@Slf4j
public class S3BucketProvisioner implements CloudResourceProvisioner<S3BucketConfig> {

  private final AwsBasicCredentials awsBasicCredentials;

  public S3BucketProvisioner(AwsBasicCredentials awsBasicCredentials) {
    this.awsBasicCredentials = awsBasicCredentials;
  }

  @Override
  public CloudProvisioningResponse provision(S3BucketConfig config) {

    String bucketName = config.getName();
    Region region = Region.of(config.getRegion());

    log.info("Starting provisioning of S3 bucket '{}'", bucketName);

    // TODO Think of a client hierarchy for different services
    try (S3Client s3Client = S3Client.builder()
        .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
        .endpointOverride(URI.create("https://s3.localhost.localstack.cloud:4566"))
        .region(region)
        .forcePathStyle(true)
        .build()) {

      CreateBucketRequest request = CreateBucketRequest.builder()
          .bucket(bucketName)
          .build();

      log.info("Sending request to create S3 bucket '{}'", bucketName);
      s3Client.createBucket(request);

      HeadBucketRequest headRequest = HeadBucketRequest.builder()
          .bucket(bucketName)
          .build();

      log.debug("Verifying bucket existence with HeadBucket request");
      HeadBucketResponse headBucketResponse = s3Client.headBucket(headRequest);

      log.info("S3 bucket '{}' exists and is active", bucketName);

      String arn = String.format("arn:aws:s3:::%s", bucketName);
      return new CloudProvisioningResponse("S3", bucketName, arn);

    } catch (Exception e) {
      log.error("Failed to provision S3 bucket '{}'", bucketName, e);
      throw e;
    }
  }
}
