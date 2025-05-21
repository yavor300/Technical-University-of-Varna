package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.aws.s3.util.S3EncryptionResultBuilder;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionAsync;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import bg.tuvarna.sit.cloud.exception.BucketEncryptionProvisioningException;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.model.GetBucketEncryptionResponse;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.ServerSideEncryption;

import java.util.Locale;

@Slf4j
@ProvisionAsync
public class S3EncryptionStep extends S3ProvisionStep {

  private static final String SSE_S3_ALGORITHM = "SSE-S3";
  private static final String AES_256_ALGORITHM = "AES256";
  private static final String AWS_KMS_ALGORITHM = "AWS:KMS";
  private static final String AWS_KMS_DSSE_ALGORITHM = AWS_KMS_ALGORITHM + ":DSSE";

  @Inject
  public S3EncryptionStep(S3SafeClient s3, S3BucketConfig config) {
    super(s3, config);
  }

  @Override
  public StepResult<S3Output> apply() {

    S3BucketConfig.EncryptionConfig encryption = config.getEncryption();

    StepResult<S3Output> result = buildEncryptionStepResult(encryption);

    if (encryption == null) {
      return result;
    }

    String algorithm = encryption.getType().toUpperCase(Locale.ROOT);
    ServerSideEncryption sseAlgorithm;

    String bucketName = config.getName();
    switch (algorithm) {
      case SSE_S3_ALGORITHM, AES_256_ALGORITHM -> sseAlgorithm = ServerSideEncryption.AES256;
      case AWS_KMS_ALGORITHM, AWS_KMS_DSSE_ALGORITHM -> sseAlgorithm = ServerSideEncryption.AWS_KMS;
      default -> throw new BucketEncryptionProvisioningException(bucketName, "Unsupported SSE algorithm", null);
    }

    s3.putEncryption(bucketName, sseAlgorithm, encryption.getKmsKeyId());

    // TODO Check if we can use the response status code from the above putEncryption call
    GetBucketEncryptionResponse response = s3.getEncryption(bucketName, false);

    return S3EncryptionResultBuilder.fromResponse(response);
  }

  @Override
  public StepResult<S3Output> generateDesiredState() {

    return buildEncryptionStepResult(config.getEncryption());
  }

  @Override
  public StepResult<S3Output> getCurrentState() {

    try {
      GetBucketEncryptionResponse response = s3.getEncryption(config.getName(), true);
      return S3EncryptionResultBuilder.fromResponse(response);

    } catch (BucketEncryptionProvisioningException e) {
      if (e.getCause() instanceof NoSuchBucketException) {
        return StepResult.<S3Output>builder()
            .stepName(S3EncryptionStep.class.getName())
            .build();
      }
      return null;
    }
  }

  private StepResult<S3Output> buildEncryptionStepResult(S3BucketConfig.EncryptionConfig encryption) {

    StepResult.Builder<S3Output> result = StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .put(S3Output.TYPE, AES_256_ALGORITHM);

    if (encryption == null) {
      return result.build();
    }

    String type = encryption.getType().toUpperCase(Locale.ROOT);
    result.put(S3Output.TYPE, type);

    String kmsKeyId = encryption.getKmsKeyId();
    if ((AWS_KMS_ALGORITHM.equals(type) || AWS_KMS_DSSE_ALGORITHM.equals(type))
        && kmsKeyId != null) {
      result.put(S3Output.KMS_KEY_ID, kmsKeyId);
    }

    return result.build();
  }

}
