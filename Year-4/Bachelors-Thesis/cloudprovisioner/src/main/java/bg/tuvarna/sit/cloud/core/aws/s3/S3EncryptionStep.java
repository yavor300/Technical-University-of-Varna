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

  @Override
  public StepResult<S3Output> apply(S3Client s3Client, S3BucketConfig config) {

    S3BucketConfig.EncryptionConfig encryption = config.getEncryption();

    StepResult.Builder<S3Output> result = StepResult.<S3Output>builder()
        .stepName(this.getClass().getName());

    if (encryption == null || encryption.getType() == null) {
      return result.build();
    }

    String algorithm = encryption.getType().toUpperCase(Locale.ROOT);
    ServerSideEncryption sseAlgorithm;

    switch (algorithm) {
      case "SSE-S3", "AES256" -> sseAlgorithm = ServerSideEncryption.AES256;
      case "AWS:KMS", "AWS:KMS:DSSE" -> sseAlgorithm = ServerSideEncryption.AWS_KMS;
      default -> {
        log.warn("Unsupported SSE algorithm '{}'. Skipping encryption config for bucket '{}'", algorithm, config.getName());
        return null;
      }
    }

    result.put(S3Output.TYPE, encryption.getType());

    ServerSideEncryptionByDefault.Builder defaultEncryption = ServerSideEncryptionByDefault.builder()
        .sseAlgorithm(sseAlgorithm);

    if (sseAlgorithm == ServerSideEncryption.AWS_KMS && encryption.getKmsKeyId() != null) {
      defaultEncryption.kmsMasterKeyID(encryption.getKmsKeyId());
      result.put(S3Output.KMS_KEY_ID, encryption.getKmsKeyId());
    }

    ServerSideEncryptionRule rule = ServerSideEncryptionRule.builder()
        .applyServerSideEncryptionByDefault(defaultEncryption.build())
        .build();

    PutBucketEncryptionRequest encryptionRequest = PutBucketEncryptionRequest.builder()
        .bucket(config.getName())
        .serverSideEncryptionConfiguration(
            ServerSideEncryptionConfiguration.builder()
                .rules(rule)
                .build())
        .build();

    s3Client.putBucketEncryption(encryptionRequest);
    log.info("Applied server-side encryption '{}' to bucket '{}'", sseAlgorithm, config.getName());

    return result.build();
  }
}
