package bg.tuvarna.sit.cloud.core.aws.s3;

import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.exception.S3ProvisioningException;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;

public interface S3ProvisionStep extends CloudProvisionStep<S3SafeClient, S3BucketConfig, S3Output> {

  @Override
  StepResult<S3Output> apply(S3SafeClient client, S3BucketConfig config) throws S3ProvisioningException;

  @Override
  StepResult<S3Output> generateDesiredState(S3BucketConfig config);

  @Override
  StepResult<S3Output> getCurrentState(S3SafeClient client, S3BucketConfig config);
}
