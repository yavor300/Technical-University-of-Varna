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

    StepResult.Builder<S3Output> result = StepResult.<S3Output>builder()
        .stepName(this.getClass().getName());

    if (config.getPolicy() != null && !config.getPolicy().isBlank()) {
      s3Client.putBucketPolicy(PutBucketPolicyRequest.builder()
          .bucket(config.getName())
          .policy(config.getPolicy())
          .build());
      log.info("Applied policy to bucket '{}'", config.getName());

      result.put(S3Output.VALUE_NODE, config.getPolicy());
    }

    return result.build();
  }
}
