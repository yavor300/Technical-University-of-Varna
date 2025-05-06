package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionAsync;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import bg.tuvarna.sit.cloud.exception.BucketEncryptionProvisioningException;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.model.GetBucketEncryptionResponse;
import software.amazon.awssdk.services.s3.model.ServerSideEncryption;
import software.amazon.awssdk.services.s3.model.ServerSideEncryptionByDefault;
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
  public StepResult<S3Output> apply(S3SafeClient s3Client, S3BucketConfig config) {

    S3BucketConfig.EncryptionConfig encryption = config.getEncryption();

    StepResult<S3Output> result = buildEncryptionStepResult(encryption);

    if (encryption == null) {
      return result;
    }

    String type = encryption.getType();
    if (type == null) {
      return result;
    }

    String algorithm = type.toUpperCase(Locale.ROOT);
    ServerSideEncryption sseAlgorithm;

    String bucketName = config.getName();
    switch (algorithm) {
      case SSE_S3_ALGORITHM, AES_256_ALGORITHM -> sseAlgorithm = ServerSideEncryption.AES256;
      case AWS_KMS_ALGORITHM, AWS_KMS_DSSE_ALGORITHM -> sseAlgorithm = ServerSideEncryption.AWS_KMS;
      default -> throw new BucketEncryptionProvisioningException(bucketName, "Unsupported SSE algorithm", null);
    }

    s3Client.putEncryption(bucketName, sseAlgorithm, encryption.getKmsKeyId());

    GetBucketEncryptionResponse response = s3Client.getEncryption(bucketName);

    ServerSideEncryptionRule ruleResponse = response.serverSideEncryptionConfiguration().rules().getFirst();
    ServerSideEncryptionByDefault sseDefault = ruleResponse.applyServerSideEncryptionByDefault();

    StepResult.Builder<S3Output> resultBuilder = StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .put(S3Output.TYPE, sseDefault.sseAlgorithmAsString());

    if (sseDefault.kmsMasterKeyID() != null) {
      resultBuilder.put(S3Output.KMS_KEY_ID, sseDefault.kmsMasterKeyID());
    }

    return resultBuilder.build();
  }

  @Override
  public StepResult<S3Output> generateDesiredState(S3BucketConfig config) {

    return buildEncryptionStepResult(config.getEncryption());
  }

  private StepResult<S3Output> buildEncryptionStepResult(S3BucketConfig.EncryptionConfig encryption) {

    StepResult.Builder<S3Output> result = StepResult.<S3Output>builder().stepName(this.getClass().getName());

    if (encryption == null) {
      return result.build();
    }

    String type = encryption.getType();
    if (type == null) {
      return result.build();
    }

    result.put(S3Output.TYPE, type);

    String kmsKeyId = encryption.getKmsKeyId();
    if ((AWS_KMS_ALGORITHM.equalsIgnoreCase(type) || AWS_KMS_DSSE_ALGORITHM.equalsIgnoreCase(type))
        && kmsKeyId != null) {
      result.put(S3Output.KMS_KEY_ID, kmsKeyId);
    }

    return result.build();
  }
}
