package bg.tuvarna.sit.cloud.core.aws.s3;

import bg.tuvarna.sit.cloud.core.provisioner.ProvisionAsync;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutBucketEncryptionRequest;
import software.amazon.awssdk.services.s3.model.ServerSideEncryption;
import software.amazon.awssdk.services.s3.model.ServerSideEncryptionByDefault;
import software.amazon.awssdk.services.s3.model.ServerSideEncryptionConfiguration;
import software.amazon.awssdk.services.s3.model.ServerSideEncryptionRule;

import java.util.Locale;

@Slf4j
@ProvisionAsync
public class S3EncryptionStep implements S3ProvisionStep {

  private static final String SSE_S3_ALGORITHM = "SSE-S3";
  private static final String AES_256_ALGORITHM = "AES256";
  private static final String AWS_KMS_ALGORITHM = "AWS:KMS";
  private static final String AWS_KMS_DSSE_ALGORITHM = AWS_KMS_ALGORITHM + ":DSSE";

  @Override
  public StepResult<S3Output> apply(S3Client s3Client, S3BucketConfig config) {

    S3BucketConfig.EncryptionConfig encryption = config.getEncryption();

    StepResult<S3Output> result = buildEncryptionStepResult(encryption);

    if (encryption == null || encryption.getType() == null) {
      return result;
    }

    String algorithm = encryption.getType().toUpperCase(Locale.ROOT);
    ServerSideEncryption sseAlgorithm;

    switch (algorithm) {
      case SSE_S3_ALGORITHM, AES_256_ALGORITHM -> sseAlgorithm = ServerSideEncryption.AES256;
      case AWS_KMS_ALGORITHM, AWS_KMS_DSSE_ALGORITHM -> sseAlgorithm = ServerSideEncryption.AWS_KMS;
      default -> {
        log.warn("Unsupported SSE algorithm '{}'. Skipping encryption config for bucket '{}'", algorithm,
            config.getName());
        // TODO Should throw exception and do not continue the flow with false config
        return null;
      }
    }

    ServerSideEncryptionByDefault.Builder defaultEncryption =
        ServerSideEncryptionByDefault.builder().sseAlgorithm(sseAlgorithm);

    if (sseAlgorithm == ServerSideEncryption.AWS_KMS && encryption.getKmsKeyId() != null) {
      defaultEncryption.kmsMasterKeyID(encryption.getKmsKeyId());
    }

    ServerSideEncryptionRule rule =
        ServerSideEncryptionRule.builder().applyServerSideEncryptionByDefault(defaultEncryption.build()).build();

    PutBucketEncryptionRequest encryptionRequest =
        PutBucketEncryptionRequest.builder().bucket(config.getName())
            .serverSideEncryptionConfiguration(ServerSideEncryptionConfiguration.builder().rules(rule).build()).build();

    s3Client.putBucketEncryption(encryptionRequest);
    log.info("Applied server-side encryption '{}' to bucket '{}'", sseAlgorithm, config.getName());

    return result;
  }

  @Override
  public StepResult<S3Output> generateDesiredState(S3BucketConfig config) {

    return buildEncryptionStepResult(config.getEncryption());
  }

  private StepResult<S3Output> buildEncryptionStepResult(S3BucketConfig.EncryptionConfig encryption) {

    StepResult.Builder<S3Output> result = StepResult.<S3Output>builder().stepName(this.getClass().getName());

    if (encryption == null || encryption.getType() == null) {
      return result.build();
    }

    result.put(S3Output.TYPE, encryption.getType());

    if ((AWS_KMS_ALGORITHM.equalsIgnoreCase(encryption.getType()) || AWS_KMS_DSSE_ALGORITHM.equalsIgnoreCase(
        encryption.getType())) && encryption.getKmsKeyId() != null) {
      result.put(S3Output.KMS_KEY_ID, encryption.getKmsKeyId());
    }

    return result.build();
  }
}
