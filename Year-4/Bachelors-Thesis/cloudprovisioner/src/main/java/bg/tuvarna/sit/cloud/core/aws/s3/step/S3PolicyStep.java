package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionAsync;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ProvisionAsync
public class S3PolicyStep implements S3ProvisionStep {

  @Override
  public StepResult<S3Output> apply(S3SafeClient s3Client, S3BucketConfig config) {

    String policy = config.getPolicy();

    if (policy == null || policy.isBlank()) {
      return buildPolicyStepResult(policy);
    }

    String bucketName = config.getName();
    s3Client.putPolicy(bucketName, policy);

    String actualPolicy = s3Client.getPolicy(bucketName).policy();

    return buildPolicyStepResult(actualPolicy);
  }

  @Override
  public StepResult<S3Output> generateDesiredState(S3BucketConfig config) {

    return buildPolicyStepResult(config.getPolicy());
  }

  private StepResult<S3Output> buildPolicyStepResult(String policy) {

    StepResult.Builder<S3Output> result = StepResult.<S3Output>builder()
        .stepName(this.getClass().getName());

    if (policy != null && !policy.isBlank()) {
      result.put(S3Output.VALUE_NODE, policy);
    }

    return result.build();
  }
}
