package bg.tuvarna.sit.cloud.core.aws.s3;

import bg.tuvarna.sit.cloud.core.provisioner.ProvisionAsync;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.BucketVersioningStatus;
import software.amazon.awssdk.services.s3.model.PutBucketVersioningRequest;
import software.amazon.awssdk.services.s3.model.VersioningConfiguration;

@Slf4j
@ProvisionAsync
public class S3VersioningStep implements S3ProvisionStep {

  @Override
  public void apply(S3Client s3Client, S3BucketConfig config) {
    if ("enabled".equalsIgnoreCase(config.getVersioning())) {
      s3Client.putBucketVersioning(PutBucketVersioningRequest.builder()
          .bucket(config.getName())
          .versioningConfiguration(VersioningConfiguration.builder()
              .status(BucketVersioningStatus.ENABLED)
              .build())
          .build());
      log.info("Enabled versioning for bucket '{}'", config.getName());
    }
  }
}