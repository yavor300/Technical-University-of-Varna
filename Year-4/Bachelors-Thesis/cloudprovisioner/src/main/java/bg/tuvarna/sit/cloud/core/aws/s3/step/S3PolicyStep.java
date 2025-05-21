package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.aws.s3.util.S3PolicyResultBuilder;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionAsync;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import bg.tuvarna.sit.cloud.exception.BucketPolicyProvisioningException;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.model.GetBucketPolicyResponse;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;

@Slf4j
@ProvisionAsync
public class S3PolicyStep extends S3ProvisionStep {

  @Inject
  public S3PolicyStep(S3SafeClient s3, S3BucketConfig config) {
    super(s3, config);
  }

  @Override
  public StepResult<S3Output> apply() {

    String policy = config.getPolicy();

    if (policy == null || policy.isBlank()) {
      return buildPolicyStepResult(policy);
    }

    String bucketName = config.getName();
    s3.putPolicy(bucketName, policy);

    GetBucketPolicyResponse response = s3.getPolicy(bucketName, false);
    return S3PolicyResultBuilder.fromResponse(response);
  }

  @Override
  public StepResult<S3Output> generateDesiredState() {

    return buildPolicyStepResult(config.getPolicy());
  }

  @Override
  public StepResult<S3Output> getCurrentState() {

    try {
      GetBucketPolicyResponse response = s3.getPolicy(config.getName(), true);
      return S3PolicyResultBuilder.fromResponse(response);

    } catch (BucketPolicyProvisioningException e) {
      if (e.getCause() instanceof NoSuchBucketException) {
        return StepResult.<S3Output>builder()
            .stepName(S3PolicyStep.class.getName())
            .build();
      }
      return null;
    }
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
