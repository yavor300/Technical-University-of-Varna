package bg.tuvarna.sit.cloud.core.aws.s3.util;

import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.step.S3EncryptionStep;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import software.amazon.awssdk.services.s3.model.GetBucketEncryptionResponse;
import software.amazon.awssdk.services.s3.model.ServerSideEncryptionByDefault;
import software.amazon.awssdk.services.s3.model.ServerSideEncryptionRule;

public class S3EncryptionResultBuilder {

  public static StepResult<S3Output> fromResponse(GetBucketEncryptionResponse response) {

    ServerSideEncryptionRule rule = response.serverSideEncryptionConfiguration().rules().getFirst();
    ServerSideEncryptionByDefault sseDefault = rule.applyServerSideEncryptionByDefault();

    StepResult.Builder<S3Output> builder = StepResult.<S3Output>builder()
        .stepName(S3EncryptionStep.class.getName())
        .put(S3Output.TYPE, sseDefault.sseAlgorithmAsString());

    if (sseDefault.kmsMasterKeyID() != null) {
      builder.put(S3Output.KMS_KEY_ID, sseDefault.kmsMasterKeyID());
    }

    return builder.build();
  }
}
