package bg.tuvarna.sit.cloud.core.provisioner;

import bg.tuvarna.sit.cloud.core.aws.s3.*;
import bg.tuvarna.sit.cloud.core.config.S3BucketConfig;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.regions.Region;

import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

      CreateBucketRequest.Builder request = CreateBucketRequest.builder()
          .bucket(bucketName);

      if (config.getAcl() != null && !config.getAcl().isBlank()) {
        request.acl(BucketCannedACL.fromValue(config.getAcl()));
        log.info("Setting ACL '{}' for bucket '{}'", config.getAcl(), bucketName);
      }

      log.info("Sending request to create S3 bucket '{}'", bucketName);
      s3Client.createBucket(request.build());

      if (config.getTags() != null && !config.getTags().isEmpty()) {
        List<Tag> tagList = config.getTags().entrySet().stream()
            .map(e -> Tag.builder().key(e.getKey()).value(e.getValue()).build())
            .collect(Collectors.toList());

        s3Client.putBucketTagging(PutBucketTaggingRequest.builder()
            .bucket(bucketName)
            .tagging(Tagging.builder().tagSet(tagList).build())
            .build());
        log.info("Applied tags to bucket '{}': {}", bucketName, config.getTags());
      }

      if ("enabled".equalsIgnoreCase(config.getVersioning())) {
        s3Client.putBucketVersioning(PutBucketVersioningRequest.builder()
            .bucket(bucketName)
            .versioningConfiguration(VersioningConfiguration.builder()
                .status(BucketVersioningStatus.ENABLED)
                .build())
            .build());
        log.info("Enabled versioning for bucket '{}'", bucketName);
      }

      if (config.getEncryption() != null && "SSE-S3".equalsIgnoreCase(config.getEncryption().getType())) {
        s3Client.putBucketEncryption(PutBucketEncryptionRequest.builder()
            .bucket(bucketName)
            .serverSideEncryptionConfiguration(ServerSideEncryptionConfiguration.builder()
                .rules(ServerSideEncryptionRule.builder()
                    .applyServerSideEncryptionByDefault(
                        ServerSideEncryptionByDefault.builder()
                            .sseAlgorithm(ServerSideEncryption.AES256)
                            .build())
                    .build())
                .build())
            .build());
        log.info("Enabled SSE-S3 encryption for bucket '{}'", bucketName);
      }

      if (config.getPolicy() != null && !config.getPolicy().isBlank()) {
        s3Client.putBucketPolicy(PutBucketPolicyRequest.builder()
            .bucket(bucketName)
            .policy(config.getPolicy())
            .build());
        log.info("Applied policy to bucket '{}'", bucketName);
      }

      List<S3ProvisionStep> steps = List.of(
          new S3PolicyStep(),
          new S3VersioningStep(),
          new S3TaggingStep(),
          new S3EncryptionStep()
      );

      steps.stream()
          .sorted(Comparator.comparingInt(s -> s.getClass().getAnnotation(ProvisionOrder.class).value()))
          .forEach(step -> step.apply(s3Client, config));

      log.debug("Verifying bucket existence with HeadBucket request");
      s3Client.headBucket(HeadBucketRequest.builder().bucket(bucketName).build());
      log.info("S3 bucket '{}' exists and is active", bucketName);

      String arn = String.format("arn:aws:s3:::%s", bucketName);
      return new CloudProvisioningResponse("S3", bucketName, arn);

    } catch (Exception e) {
      log.error("Failed to provision S3 bucket '{}'", bucketName, e);
      throw e;
    }
  }
}
