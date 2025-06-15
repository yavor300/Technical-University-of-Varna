package bg.tuvarna.sit.cloud.core.aws.s3.step.base;

import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.aws.s3.config.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;

public abstract class S3ProvisionStep implements CloudProvisionStep<S3Output> {

  protected final S3SafeClient s3;
  protected final S3BucketConfig config;

  protected S3ProvisionStep(S3SafeClient s3, S3BucketConfig config) {
    this.s3 = s3;
    this.config = config;
  }
}
