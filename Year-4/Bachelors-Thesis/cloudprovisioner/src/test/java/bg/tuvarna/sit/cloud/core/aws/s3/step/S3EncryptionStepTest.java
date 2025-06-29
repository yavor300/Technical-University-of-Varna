package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.aws.s3.config.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.model.S3EncryptionType;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.s3.model.GetBucketEncryptionResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.ServerSideEncryption;
import software.amazon.awssdk.services.s3.model.ServerSideEncryptionByDefault;
import software.amazon.awssdk.services.s3.model.ServerSideEncryptionConfiguration;
import software.amazon.awssdk.services.s3.model.ServerSideEncryptionRule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class S3EncryptionStepTest {

  @Mock
  private S3SafeClient s3;

  private S3BucketConfig config;
  private StepResult<S3Output> metadata;

  private S3EncryptionStep step;

  @BeforeEach
  void setup() {

    config = new S3BucketConfig();
    S3BucketConfig.EncryptionConfig encryption = new S3BucketConfig.EncryptionConfig();
    encryption.setType(S3EncryptionType.SSE_S3);
    encryption.setKmsKeyId("kms-key-id");
    encryption.setBucketKeyEnabled(true);
    config.setEncryption(encryption);

    metadata = StepResult.<S3Output>builder()
        .stepName("S3Metadata")
        .put(S3Output.NAME, "test-bucket")
        .build();

    step = new S3EncryptionStep(s3, config, metadata);
  }

  @Test
  void apply_shouldApplySSE_S3EncryptionSuccessfully() {

    when(s3.getEncryption("test-bucket")).thenReturn(
        GetBucketEncryptionResponse.builder().build()
    );

    StepResult<S3Output> result = step.apply();

    assertThat(result.getStepName()).isEqualTo(S3EncryptionStep.class.getName());

    verify(s3).putEncryption(
        eq("test-bucket"),
        eq(ServerSideEncryption.AES256),
        eq("kms-key-id"),
        eq(true)
    );
    verify(s3).getEncryption("test-bucket");
  }

  @Test
  void apply_shouldApplyAWS_KMSEncryptionSuccessfully() {

    config.getEncryption().setType(S3EncryptionType.AWS_KMS);

    when(s3.getEncryption("test-bucket")).thenReturn(GetBucketEncryptionResponse.builder().build());

    StepResult<S3Output> result = step.apply();

    assertThat(result.getStepName()).isEqualTo(S3EncryptionStep.class.getName());

    verify(s3).putEncryption(
        eq("test-bucket"),
        eq(ServerSideEncryption.AWS_KMS),
        eq("kms-key-id"),
        eq(true)
    );
    verify(s3).getEncryption("test-bucket");
  }

  @Test
  void generateDesiredState_shouldReturnAES256StepResult() {

    S3BucketConfig config = new S3BucketConfig();
    S3BucketConfig.EncryptionConfig encryption = new S3BucketConfig.EncryptionConfig();
    encryption.setType(S3EncryptionType.SSE_S3);
    encryption.setBucketKeyEnabled(true);
    encryption.setKmsKeyId(null);
    config.setEncryption(encryption);

    metadata = StepResult.<S3Output>builder()
        .stepName("metadata")
        .put(S3Output.NAME, "test-bucket")
        .build();

    S3EncryptionStep step = new S3EncryptionStep(s3, config, metadata);
    StepResult<S3Output> result = step.generateDesiredState();

    assertThat(result.getStepName()).isEqualTo(S3EncryptionStep.class.getName());
    assertThat(result.getOutputs()).containsEntry(S3Output.TYPE, "AES256");
    assertThat(result.getOutputs()).containsEntry(S3Output.BUCKET_KEY_ENABLED, true);
    assertThat(result.getOutputs()).doesNotContainKey(S3Output.KMS_KEY_ID);
  }

  @Test
  void generateDesiredState_shouldReturnKmsKeyId_whenAWSKMSUsed() {

    S3BucketConfig config = new S3BucketConfig();
    S3BucketConfig.EncryptionConfig encryption = new S3BucketConfig.EncryptionConfig();
    encryption.setType(S3EncryptionType.AWS_KMS);
    encryption.setBucketKeyEnabled(false);
    encryption.setKmsKeyId("kms-1234");
    config.setEncryption(encryption);

    metadata = StepResult.<S3Output>builder()
        .stepName("metadata")
        .put(S3Output.NAME, "test-bucket")
        .build();

    S3EncryptionStep step = new S3EncryptionStep(s3, config, metadata);
    StepResult<S3Output> result = step.generateDesiredState();

    assertThat(result.getStepName()).isEqualTo(S3EncryptionStep.class.getName());
    assertThat(result.getOutputs()).containsEntry(S3Output.TYPE, "AWS:KMS");
    assertThat(result.getOutputs()).containsEntry(S3Output.BUCKET_KEY_ENABLED, false);
    assertThat(result.getOutputs()).containsEntry(S3Output.KMS_KEY_ID, "kms-1234");
  }

  @Test
  void generateDesiredState_shouldOmitKmsKey_whenNullWithKmsType() {

    S3BucketConfig config = new S3BucketConfig();
    S3BucketConfig.EncryptionConfig encryption = new S3BucketConfig.EncryptionConfig();
    encryption.setType(S3EncryptionType.AWS_KMS);
    encryption.setBucketKeyEnabled(false);
    encryption.setKmsKeyId(null);
    config.setEncryption(encryption);

    metadata = StepResult.<S3Output>builder()
        .stepName("metadata")
        .put(S3Output.NAME, "test-bucket")
        .build();

    S3EncryptionStep step = new S3EncryptionStep(s3, config, metadata);
    StepResult<S3Output> result = step.generateDesiredState();

    assertThat(result.getOutputs()).doesNotContainKey(S3Output.KMS_KEY_ID);
  }

  @Test
  void getCurrentState_shouldReturnEncryptionResult_whenBucketHasEncryption() {

    ServerSideEncryptionByDefault sseDefault = ServerSideEncryptionByDefault.builder()
        .sseAlgorithm(ServerSideEncryption.AES256)
        .kmsMasterKeyID("kms-key-id")
        .build();

    ServerSideEncryptionRule rule = ServerSideEncryptionRule.builder()
        .bucketKeyEnabled(true)
        .applyServerSideEncryptionByDefault(sseDefault)
        .build();

    ServerSideEncryptionConfiguration config = ServerSideEncryptionConfiguration.builder()
        .rules(rule)
        .build();

    GetBucketEncryptionResponse response = GetBucketEncryptionResponse.builder()
        .serverSideEncryptionConfiguration(config)
        .build();

    when(s3.getEncryption("test-bucket")).thenReturn(response);

    StepResult<S3Output> result = step.getCurrentState();

    assertThat(result.getStepName()).isEqualTo(S3EncryptionStep.class.getName());
    assertThat(result.getOutputs()).containsEntry(S3Output.TYPE, "AES256");
    assertThat(result.getOutputs()).containsEntry(S3Output.BUCKET_KEY_ENABLED, true);
    assertThat(result.getOutputs()).containsEntry(S3Output.KMS_KEY_ID, "kms-key-id");
  }

  @Test
  void getCurrentState_shouldReturnEmptyResult_whenS3EncryptionNotFound() {

    AwsServiceException s3Exception = S3Exception.builder().statusCode(404).message("Not found").build();
    CloudResourceStepException wrapper = new CloudResourceStepException("not found", s3Exception);

    when(s3.getEncryption("test-bucket")).thenThrow(wrapper);

    StepResult<S3Output> result = step.getCurrentState();

    assertThat(result.getStepName()).isEqualTo(S3EncryptionStep.class.getName());
    assertThat(result.getOutputs()).isEmpty();
  }

  @Test
  void getCurrentState_shouldThrow_whenUnexpectedErrorOccurs() {

    RuntimeException cause = new RuntimeException("unexpected");
    CloudResourceStepException wrapper = new CloudResourceStepException("error", cause);

    when(s3.getEncryption("test-bucket")).thenThrow(wrapper);

    assertThatThrownBy(() -> step.getCurrentState())
        .isInstanceOf(CloudResourceStepException.class)
        .hasMessageContaining("error");
  }

  @Test
  void destroy_shouldResetEncryptionToSSES3_andReturnParsedResult() {

    ServerSideEncryptionByDefault sseDefault = ServerSideEncryptionByDefault.builder()
        .sseAlgorithm(ServerSideEncryption.AES256)
        .build();

    ServerSideEncryptionRule rule = ServerSideEncryptionRule.builder()
        .bucketKeyEnabled(false)
        .applyServerSideEncryptionByDefault(sseDefault)
        .build();

    GetBucketEncryptionResponse encryptionResponse = GetBucketEncryptionResponse.builder()
        .serverSideEncryptionConfiguration(
            ServerSideEncryptionConfiguration.builder()
                .rules(rule)
                .build()
        )
        .build();

    when(s3.getEncryption("test-bucket")).thenReturn(encryptionResponse);

    StepResult<S3Output> result = step.destroy(false);

    assertThat(result.getStepName()).isEqualTo(S3EncryptionStep.class.getName());
    assertThat(result.getOutputs().get(S3Output.TYPE)).isEqualTo("AES256");
    assertThat(result.getOutputs().get(S3Output.BUCKET_KEY_ENABLED)).isEqualTo(false);

    verify(s3).putEncryption("test-bucket", ServerSideEncryption.AES256, null, false);
    verify(s3).getEncryption("test-bucket");
  }

  @Test
  void revert_shouldReapplyEncryptionSettingsFromPreviousStep() {

    StepResult<S3Output> previous = StepResult.<S3Output>builder()
        .stepName(S3EncryptionStep.class.getName())
        .put(S3Output.TYPE, "aws:kms")
        .put(S3Output.KMS_KEY_ID, "revert-kms-id")
        .put(S3Output.BUCKET_KEY_ENABLED, true)
        .build();

    ServerSideEncryptionByDefault sseDefault = ServerSideEncryptionByDefault.builder()
        .sseAlgorithm(ServerSideEncryption.AWS_KMS)
        .kmsMasterKeyID("revert-kms-id")
        .build();

    ServerSideEncryptionRule rule = ServerSideEncryptionRule.builder()
        .bucketKeyEnabled(true)
        .applyServerSideEncryptionByDefault(sseDefault)
        .build();

    GetBucketEncryptionResponse encryptionResponse = GetBucketEncryptionResponse.builder()
        .serverSideEncryptionConfiguration(
            ServerSideEncryptionConfiguration.builder()
                .rules(rule)
                .build())
        .build();

    when(s3.getEncryption("test-bucket")).thenReturn(encryptionResponse);

    StepResult<S3Output> result = step.revert(previous);

    assertThat(result.getStepName()).isEqualTo(S3EncryptionStep.class.getName());
    assertThat(result.getOutputs().get(S3Output.TYPE)).isEqualTo("AWS:KMS");
    assertThat(result.getOutputs().get(S3Output.BUCKET_KEY_ENABLED)).isEqualTo(true);
    assertThat(result.getOutputs().get(S3Output.KMS_KEY_ID)).isEqualTo("revert-kms-id");

    verify(s3).putEncryption("test-bucket", ServerSideEncryption.AWS_KMS, "revert-kms-id", true);
    verify(s3).getEncryption("test-bucket");
  }
}
