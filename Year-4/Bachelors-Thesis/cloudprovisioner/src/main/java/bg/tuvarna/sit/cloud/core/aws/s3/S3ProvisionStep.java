package bg.tuvarna.sit.cloud.core.aws.s3;

import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import software.amazon.awssdk.services.s3.S3Client;

public interface S3ProvisionStep extends CloudProvisionStep<S3Client, S3BucketConfig, S3Output> {

  @Override
  StepResult<S3Output> apply(S3Client client, S3BucketConfig config);
}
