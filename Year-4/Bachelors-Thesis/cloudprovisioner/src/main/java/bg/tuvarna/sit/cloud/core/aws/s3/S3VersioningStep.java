package bg.tuvarna.sit.cloud.core.aws.s3;

import bg.tuvarna.sit.cloud.core.provisioner.ProvisionAsync;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.BucketVersioningStatus;
import software.amazon.awssdk.services.s3.model.PutBucketVersioningRequest;
import software.amazon.awssdk.services.s3.model.VersioningConfiguration;

@Slf4j
@ProvisionAsync
public class S3VersioningStep implements S3ProvisionStep {

  @Override
  public StepResult<S3Output> apply(S3Client s3Client, S3BucketConfig config) {

    StepResult.Builder<S3Output> result = StepResult.<S3Output>builder()
        .stepName(this.getClass().getName());

    if (config.getVersioning().isEmpty()) {
      return result.build();
    }

    boolean isEnabled = "enabled".equalsIgnoreCase(config.getVersioning());
    BucketVersioningStatus status = isEnabled ? BucketVersioningStatus.ENABLED : BucketVersioningStatus.SUSPENDED;

    s3Client.putBucketVersioning(PutBucketVersioningRequest.builder()
        .bucket(config.getName())
        .versioningConfiguration(VersioningConfiguration.builder()
            .status(status)
            .build())
        .build());
    log.info("Set versioning for bucket '{}'", config.getName());

    result.put(S3Output.VALUE_NODE, status.toString());

    return result.build();
  }
}
