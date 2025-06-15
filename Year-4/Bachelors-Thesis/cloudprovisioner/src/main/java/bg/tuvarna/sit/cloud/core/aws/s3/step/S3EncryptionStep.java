package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.config.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.model.S3EncryptionType;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.step.base.S3ProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.aws.s3.util.S3EncryptionResultBuilder;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionOrder;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;

import com.google.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.apache.hc.core5.http.HttpStatus;

import software.amazon.awssdk.services.s3.model.GetBucketEncryptionResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.ServerSideEncryption;

@Slf4j
@ProvisionOrder(3)
public class S3EncryptionStep extends S3ProvisionStep {

  private static final String SSE_S3_ALGORITHM = "sse-s3";
  private static final String AES_256_ALGORITHM = "aes256";
  private static final String AWS_KMS_ALGORITHM = "aws:kms";
  private static final String AWS_KMS_DSSE_ALGORITHM = AWS_KMS_ALGORITHM + ":dsse";

  private final StepResult<S3Output> metadata;

  @Inject
  public S3EncryptionStep(S3SafeClient s3, S3BucketConfig config, StepResult<S3Output> metadata) {
    super(s3, config);
    this.metadata = metadata;
  }

  @Override
  public StepResult<S3Output> apply() {

    S3BucketConfig.EncryptionConfig encryption = config.getEncryption();

    ServerSideEncryption sseAlgorithm;

    switch (encryption.getType()) {
      case SSE_S3, AES_256 -> sseAlgorithm = ServerSideEncryption.AES256;
      case AWS_KMS, AWS_KMS_DSSE -> sseAlgorithm = ServerSideEncryption.AWS_KMS;
      default -> throw new CloudResourceStepException("Unsupported SSE algorithm");
    }

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);
    s3.putEncryption(bucket, sseAlgorithm, encryption.getKmsKeyId(), encryption.isBucketKeyEnabled());
    log.info("Successfully applied server-side encryption '{}' to bucket '{}'", sseAlgorithm, bucket);

    GetBucketEncryptionResponse response = s3.getEncryption(bucket);
    log.info("Successfully verified server-side encryption '{}' for bucket '{}'", sseAlgorithm, bucket);

    return S3EncryptionResultBuilder.fromResponse(response);
  }

  @Override
  public StepResult<S3Output> generateDesiredState() {

    return buildEncryptionStepResult(config.getEncryption());
  }

  @Override
  public StepResult<S3Output> getCurrentState() {

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);

    try {
      GetBucketEncryptionResponse response = s3.getEncryption(bucket);
      log.info("Successfully fetched server-side encryption for bucket '{}'", bucket);
      return S3EncryptionResultBuilder.fromResponse(response);

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

    s3.putEncryption(bucket, ServerSideEncryption.AES256, null, false);
    log.info("Set encryption for bucket '{}' to the default SSE-S3", bucket);

    GetBucketEncryptionResponse response = s3.getEncryption(bucket);
    return S3EncryptionResultBuilder.fromResponse(response);
  }

  @Override
  public StepResult<S3Output> revert(StepResult<S3Output> previous) throws CloudResourceStepException {

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);

    ServerSideEncryption sseAlgorithm;
    String sseAlgorithmStr = (String) previous.getOutputs().get(S3Output.TYPE);
    switch (sseAlgorithmStr.toLowerCase()) {
      case SSE_S3_ALGORITHM, AES_256_ALGORITHM -> sseAlgorithm = ServerSideEncryption.AES256;
      case AWS_KMS_ALGORITHM, AWS_KMS_DSSE_ALGORITHM -> sseAlgorithm = ServerSideEncryption.AWS_KMS;
      default -> throw new CloudResourceStepException("Unsupported SSE algorithm");
    }

    String kmsKeyId = (String) previous.getOutputs().get(S3Output.KMS_KEY_ID);
    Boolean bucketKeyEnabled = (Boolean) previous.getOutputs().get(S3Output.BUCKET_KEY_ENABLED);
    s3.putEncryption(bucket, sseAlgorithm, kmsKeyId, bucketKeyEnabled);

    GetBucketEncryptionResponse response = s3.getEncryption(bucket);
    return S3EncryptionResultBuilder.fromResponse(response);
  }

  private StepResult<S3Output> buildEncryptionStepResult(S3BucketConfig.EncryptionConfig encryption) {

    String type;

    switch (encryption.getType()) {
      case SSE_S3, AES_256 -> type = S3EncryptionType.AES_256.getValue();
      case AWS_KMS, AWS_KMS_DSSE -> type = S3EncryptionType.AWS_KMS.getValue();
      default -> throw new CloudResourceStepException("Unsupported SSE algorithm");
    }

    StepResult.Builder<S3Output> result = StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .put(S3Output.TYPE, type.toUpperCase())
        .put(S3Output.BUCKET_KEY_ENABLED, encryption.isBucketKeyEnabled());

    String kmsKeyId = encryption.getKmsKeyId();
    if ((AWS_KMS_ALGORITHM.equals(type) || AWS_KMS_DSSE_ALGORITHM.equals(type)) && kmsKeyId != null) {
      result.put(S3Output.KMS_KEY_ID, kmsKeyId);
    }

    return result.build();
  }

}
