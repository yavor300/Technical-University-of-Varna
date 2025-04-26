package bg.tuvarna.sit.cloud.core.aws.s3;

import bg.tuvarna.sit.cloud.core.provisioner.ProvisionAsync;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutBucketPolicyRequest;

@Slf4j
@ProvisionAsync
public class S3PolicyStep implements S3ProvisionStep {

  @Override
  public StepResult<S3Output> apply(S3Client s3Client, S3BucketConfig config) {

    String policy = config.getPolicy();

    if (policy != null && !policy.isBlank()) {

      String bucketName = config.getName();
      s3Client.putBucketPolicy(PutBucketPolicyRequest.builder()
          .bucket(bucketName)
          .policy(policy)
          .build());
      log.info("Applied policy to bucket '{}'", bucketName);
    }

    return buildPolicyStepResult(policy);
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
