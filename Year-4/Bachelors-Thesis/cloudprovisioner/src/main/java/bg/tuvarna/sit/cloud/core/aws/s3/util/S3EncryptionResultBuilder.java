package bg.tuvarna.sit.cloud.core.aws.s3.util;

import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.step.S3EncryptionStep;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;

import software.amazon.awssdk.services.s3.model.GetBucketEncryptionResponse;
import software.amazon.awssdk.services.s3.model.ServerSideEncryptionByDefault;
import software.amazon.awssdk.services.s3.model.ServerSideEncryptionConfiguration;
import software.amazon.awssdk.services.s3.model.ServerSideEncryptionRule;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class S3EncryptionResultBuilder {

  public static StepResult<S3Output> fromResponse(GetBucketEncryptionResponse response) {

    List<ServerSideEncryptionRule> rules = Optional.ofNullable(response.serverSideEncryptionConfiguration())
        .map(ServerSideEncryptionConfiguration::rules)
        .orElse(Collections.emptyList());

    StepResult.Builder<S3Output> builder = StepResult.<S3Output>builder()
        .stepName(S3EncryptionStep.class.getName());

    if (!rules.isEmpty()) {
      ServerSideEncryptionRule rule = rules.getFirst();
      ServerSideEncryptionByDefault sseDefault = rule.applyServerSideEncryptionByDefault();

      builder.put(S3Output.TYPE, sseDefault.sseAlgorithmAsString().toUpperCase());
      builder.put(S3Output.BUCKET_KEY_ENABLED, rule.bucketKeyEnabled());

      if (sseDefault.kmsMasterKeyID() != null) {
        builder.put(S3Output.KMS_KEY_ID, sseDefault.kmsMasterKeyID());
      }
    }

    return builder.build();
  }
}
