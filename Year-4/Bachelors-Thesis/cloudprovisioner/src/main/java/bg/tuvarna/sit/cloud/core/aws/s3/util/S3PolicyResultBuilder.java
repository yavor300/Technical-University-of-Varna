package bg.tuvarna.sit.cloud.core.aws.s3.util;

import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.step.S3PolicyStep;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import software.amazon.awssdk.services.s3.model.GetBucketPolicyResponse;

public class S3PolicyResultBuilder {

  public static StepResult<S3Output> fromResponse(GetBucketPolicyResponse response) {

    return StepResult.<S3Output>builder()
        .stepName(S3PolicyStep.class.getName())
        .put(S3Output.VALUE_NODE, response.policy())
        .build();
  }
}
