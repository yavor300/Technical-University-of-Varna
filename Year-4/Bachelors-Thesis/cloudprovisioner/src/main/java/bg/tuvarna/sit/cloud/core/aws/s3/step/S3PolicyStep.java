package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.aws.s3.util.S3PolicyResultBuilder;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionAsync;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionOrder;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;

import jakarta.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.apache.hc.core5.http.HttpStatus;

import software.amazon.awssdk.services.s3.model.GetBucketPolicyResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Slf4j
@ProvisionOrder(5)
public class S3PolicyStep extends S3ProvisionStep {

  private final StepResult<S3Output> metadata;

  @Inject
  public S3PolicyStep(S3SafeClient s3, S3BucketConfig config, StepResult<S3Output> metadata) {
    super(s3, config);
    this.metadata = metadata;
  }

  @Override
  public StepResult<S3Output> apply() {

    String policy = config.getPolicy();

    if (policy == null || policy.isBlank()) {
      return buildPolicyStepResult(policy);
    }

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);
    s3.putPolicy(bucket, policy);
    log.info("Successfully applied policy '{}' to bucket '{}'", policy, bucket);

    GetBucketPolicyResponse response = s3.getPolicy(bucket);
    log.info("Successfully verified policy '{}' for bucket '{}'", response.policy(), bucket);

    return S3PolicyResultBuilder.fromResponse(response);
  }

  @Override
  public StepResult<S3Output> generateDesiredState() {

    return buildPolicyStepResult(config.getPolicy());
  }

  @Override
  public StepResult<S3Output> getCurrentState() {

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);

    try {
      GetBucketPolicyResponse response = s3.getPolicy(bucket);
      log.info("Retrieved policy for bucket '{}'", bucket);
      return S3PolicyResultBuilder.fromResponse(response);

    } catch (CloudResourceStepException e) {
      if (e.getCause() instanceof S3Exception s3Exception) {
        if (s3Exception.statusCode() == HttpStatus.SC_NOT_FOUND) {
          return StepResult.<S3Output>builder()
              .stepName(this.getClass().getName())
              .build();
        }
      }
      throw e;
    }
  }

  @Override
  public StepResult<S3Output> destroy(boolean enforcePreventDestroy) {

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);

    s3.deletePolicy(bucket);
    log.info("Deleted policy from bucket '{}'", bucket);

    return StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .build();
  }

  @Override
  public StepResult<S3Output> revert(StepResult<S3Output> previous) throws CloudResourceStepException {

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);
    String revert = (String) previous.getOutputs().get(S3Output.VALUE_NODE);

    if (revert == null || revert.isEmpty()) {

      s3.deletePolicy(bucket);
      log.debug("Reverted the created policy for bucket '{}'", bucket);

      return StepResult.<S3Output>builder()
          .stepName(this.getClass().getName())
          .build();
    }

    s3.putPolicy(bucket, revert);
    log.info("Reverted policy for bucket '{}'", bucket);

    GetBucketPolicyResponse response = s3.getPolicy(bucket);
    return S3PolicyResultBuilder.fromResponse(response);
  }

  private StepResult<S3Output> buildPolicyStepResult(String policy) {

    StepResult.Builder<S3Output> result = StepResult.<S3Output>builder()
        .stepName(this.getClass().getName());

    if (policy != null && !policy.isBlank()) {
      String cleaned = policy.replaceAll("\\s+", "");
      result.put(S3Output.VALUE_NODE, cleaned);
    }

    return result.build();
  }
}
