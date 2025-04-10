package bg.tuvarna.sit.cloud.core.provisioner;

import bg.tuvarna.sit.cloud.core.aws.s3.ProvisionAsync;
import bg.tuvarna.sit.cloud.core.aws.s3.ProvisionOrder;
import bg.tuvarna.sit.cloud.core.aws.s3.S3AclStep;
import bg.tuvarna.sit.cloud.core.aws.s3.S3EncryptionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.S3PolicyStep;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.S3TaggingStep;
import bg.tuvarna.sit.cloud.core.aws.s3.S3VersioningStep;
import bg.tuvarna.sit.cloud.core.config.S3BucketConfig;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;

import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

      log.info("Sending request to create S3 bucket '{}'", bucketName);
      s3Client.createBucket(request.build());

      List<S3ProvisionStep> steps = List.of(
          new S3PolicyStep(),
          new S3VersioningStep(),
          new S3TaggingStep(),
          new S3EncryptionStep(),
          new S3AclStep()
      );

      List<S3ProvisionStep> asyncSteps = new ArrayList<>();
      List<S3ProvisionStep> syncSteps = new ArrayList<>();

      for (S3ProvisionStep step : steps) {
        if (step.getClass().isAnnotationPresent(ProvisionAsync.class)) {
          asyncSteps.add(step);
        } else {
          syncSteps.add(step);
        }
      }

      syncSteps.stream()
          .sorted(Comparator.comparingInt(s -> s.getClass().getAnnotation(ProvisionOrder.class).value()))
          .forEach(step -> step.apply(s3Client, config));

      List<Callable<Void>> tasks = asyncSteps.stream()
          .map(step -> (Callable<Void>) () -> {
            step.apply(s3Client, config);
            return null;
          })
          .toList();

      try (ExecutorService executor = Executors.newFixedThreadPool(4)) {
        executor.invokeAll(tasks);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        log.error("Interrupted while provisioning S3 bucket '{}'", bucketName, e);
      }

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
