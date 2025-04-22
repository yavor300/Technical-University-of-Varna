package bg.tuvarna.sit.cloud.core.aws.s3;

import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisioningResponse;
import bg.tuvarna.sit.cloud.core.provisioner.CloudResourceProvisioner;
import bg.tuvarna.sit.cloud.core.provisioner.CloudStepExecutor;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import bg.tuvarna.sit.cloud.core.provisioner.StepResultStateWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class S3BucketProvisioner implements CloudResourceProvisioner<S3BucketConfig> {

  private final S3ProvisioningContext context;
  private final CloudStepExecutor<S3Client, S3BucketConfig, S3Output> stepExecutor;

  @Override
  public CloudProvisioningResponse provision(S3BucketConfig config) throws InterruptedException {

    long startTime = System.nanoTime();
    String bucketName = config.getName();

    try (S3Client s3Client = S3Client.builder()
        .credentialsProvider(StaticCredentialsProvider.create(context.getCredentials()))
        .endpointOverride(context.getEndpoint())
        .region(context.getRegion())
        .forcePathStyle(true)
        .build()) {

      List<StepResult<S3Output>> results = stepExecutor.execute(s3Client, config);

      log.info("Verifying bucket existence with HeadBucket request");
      s3Client.headBucket(HeadBucketRequest.builder().bucket(bucketName).build());
      log.info("S3 bucket '{}' exists and is active", bucketName);

      long endTime = System.nanoTime();
      long durationMs = (endTime - startTime) / 1_000_000;
      log.info("Provisioning completed for bucket '{}' in {} ms", bucketName, durationMs);

      StepResultStateWriter<S3Output> writer = new StepResultStateWriter<>(".cloudprovisioner/state.json");
      writer.write(results);

      String arn = String.format("arn:aws:s3:::%s", bucketName);
      return new CloudProvisioningResponse("S3", bucketName, arn);

    } catch (Exception e) {
      log.error("Failed to provision S3 bucket '{}'", bucketName, e);
      throw e;
    }
  }
}
