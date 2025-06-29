package bg.tuvarna.sit.cloud.core.aws.s3.step;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.aws.s3.config.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.s3.model.BucketVersioningStatus;
import software.amazon.awssdk.services.s3.model.GetBucketVersioningResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

@ExtendWith(MockitoExtension.class)
class S3VersioningStepTest {

  @Mock
  private S3SafeClient s3;

  private S3BucketConfig config;
  private StepResult<S3Output> metadata;
  private S3VersioningStep step;

  @BeforeEach
  void setup() {

    config = new S3BucketConfig();
    metadata = StepResult.<S3Output>builder()
        .stepName("metadata-step")
        .put(S3Output.NAME, "test-bucket")
        .build();

    step = new S3VersioningStep(s3, config, metadata);
  }

  @Test
  void apply_shouldEnableVersioning_whenConfigIsEnabled() {

    config.setVersioning("Enabled");

    GetBucketVersioningResponse response = GetBucketVersioningResponse.builder()
        .status(BucketVersioningStatus.ENABLED)
        .build();
    when(s3.getVersioning("test-bucket")).thenReturn(response);

    StepResult<S3Output> result = step.apply();

    verify(s3).putVersioning("test-bucket", BucketVersioningStatus.ENABLED);
    verify(s3).getVersioning("test-bucket");

    assertThat(result.getStepName()).isEqualTo(S3VersioningStep.class.getName());
    assertThat(result.getOutputs()).containsEntry(S3Output.VERSIONING_STATUS, "Enabled");
  }

  @Test
  void apply_shouldSuspendVersioning_whenConfigIsNotEnabled() {

    config.setVersioning("Suspended");

    GetBucketVersioningResponse response = GetBucketVersioningResponse.builder()
        .status(BucketVersioningStatus.SUSPENDED)
        .build();
    when(s3.getVersioning("test-bucket")).thenReturn(response);

    StepResult<S3Output> result = step.apply();

    verify(s3).putVersioning("test-bucket", BucketVersioningStatus.SUSPENDED);
    verify(s3).getVersioning("test-bucket");

    assertThat(result.getStepName()).isEqualTo(S3VersioningStep.class.getName());
    assertThat(result.getOutputs()).containsEntry(S3Output.VERSIONING_STATUS, "Suspended");
  }


  @Test
  void generateDesiredState_shouldReturnEnabled_whenConfiguredAsEnabled() {

    config.setVersioning("Enabled");

    StepResult<S3Output> result = step.generateDesiredState();

    assertThat(result.getStepName()).isEqualTo(S3VersioningStep.class.getName());
    assertThat(result.getOutputs()).containsEntry(S3Output.VERSIONING_STATUS, "Enabled");
  }

  @Test
  void generateDesiredState_shouldReturnSuspended_whenConfiguredAsSuspended() {

    config.setVersioning("Suspended");

    StepResult<S3Output> result = step.generateDesiredState();

    assertThat(result.getStepName()).isEqualTo(S3VersioningStep.class.getName());
    assertThat(result.getOutputs()).containsEntry(S3Output.VERSIONING_STATUS, "Suspended");
  }

  @Test
  void generateDesiredState_shouldDefaultToSuspended_whenConfigIsNull() {

    config.setVersioning(null);

    StepResult<S3Output> result = step.generateDesiredState();

    assertThat(result.getStepName()).isEqualTo(S3VersioningStep.class.getName());
    assertThat(result.getOutputs()).containsEntry(S3Output.VERSIONING_STATUS, "Suspended");
  }

  @Test
  void getCurrentState_shouldReturnVersioningStepResult() {

    GetBucketVersioningResponse response = GetBucketVersioningResponse.builder()
        .status(BucketVersioningStatus.ENABLED)
        .build();

    when(s3.getVersioning("test-bucket")).thenReturn(response);

    StepResult<S3Output> result = step.getCurrentState();

    assertThat(result.getStepName()).isEqualTo(S3VersioningStep.class.getName());
    assertThat(result.getOutputs()).containsEntry(S3Output.VERSIONING_STATUS, "Enabled");
  }

  @Test
  void getCurrentState_shouldReturnEmptyResult_whenS3ReturnsNotFound() {

    AwsServiceException notFound = S3Exception.builder()
        .statusCode(404)
        .message("Not Found")
        .build();

    CloudResourceStepException wrapped = new CloudResourceStepException("Error", notFound);

    when(s3.getVersioning("test-bucket")).thenThrow(wrapped);

    StepResult<S3Output> result = step.getCurrentState();

    assertThat(result.getStepName()).isEqualTo(S3VersioningStep.class.getName());
    assertThat(result.getOutputs()).isEmpty();
  }

  @Test
  void getCurrentState_shouldRethrow_whenOtherExceptionOccurs() {

    RuntimeException unexpected = new RuntimeException("unexpected");

    CloudResourceStepException wrapped = new CloudResourceStepException("Wrapped", unexpected);

    when(s3.getVersioning("test-bucket")).thenThrow(wrapped);

    assertThrows(CloudResourceStepException.class, () -> step.getCurrentState());
  }

  @Test
  void destroy_shouldSuspendVersioningAndReturnResult() {

    GetBucketVersioningResponse response = GetBucketVersioningResponse.builder()
        .status(BucketVersioningStatus.SUSPENDED)
        .build();

    when(s3.getVersioning("test-bucket")).thenReturn(response);

    StepResult<S3Output> result = step.destroy(false);

    verify(s3).putVersioning("test-bucket", BucketVersioningStatus.SUSPENDED);
    verify(s3).getVersioning("test-bucket");

    assertThat(result.getStepName()).isEqualTo(S3VersioningStep.class.getName());
    assertThat(result.getOutputs()).containsEntry(S3Output.VERSIONING_STATUS, "Suspended");
  }

  @Test
  void revert_shouldRestorePreviousVersioningStatus() throws CloudResourceStepException {

    StepResult<S3Output> previous = StepResult.<S3Output>builder()
        .stepName("previous-step")
        .put(S3Output.VERSIONING_STATUS, "ENABLED")
        .build();

    GetBucketVersioningResponse response = GetBucketVersioningResponse.builder()
        .status(BucketVersioningStatus.ENABLED)
        .build();

    when(s3.getVersioning("test-bucket")).thenReturn(response);

    StepResult<S3Output> result = step.revert(previous);

    verify(s3).putVersioning("test-bucket", BucketVersioningStatus.ENABLED);
    verify(s3).getVersioning("test-bucket");

    assertThat(result.getStepName()).isEqualTo(S3VersioningStep.class.getName());
    assertThat(result.getOutputs()).containsEntry(S3Output.VERSIONING_STATUS, "Enabled");
  }

}
