package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.common.Region;
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
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class S3BucketStepTest {

  @Mock
  private S3SafeClient s3;

  private S3BucketConfig config;
  private StepResult<S3Output> metadata;
  private S3BucketStep step;

  @BeforeEach
  void setup() {

    config = new S3BucketConfig();
    config.setName("my-test-bucket");
    config.setRegion(Region.EU_WEST_1);

    metadata = StepResult.<S3Output>builder()
        .stepName("metadata-step")
        .put(S3Output.NAME, "my-test-bucket")
        .put(S3Output.REGION, "eu-west-1")
        .build();

    step = new S3BucketStep(s3, config, metadata);
  }

  @Test
  void apply_shouldCreateBucket_whenBucketNameIsValid() {
    StepResult<S3Output> result = step.apply();

    assertThat(result.getStepName()).isEqualTo(S3BucketStep.class.getName());
    assertThat(result.getOutputs().get(S3Output.NAME)).isEqualTo("my-test-bucket");
    assertThat(result.getOutputs().get(S3Output.REGION)).isEqualTo("eu-west-1");

    verify(s3).create("my-test-bucket");
    verify(s3).waitUntilBucketExists(eq("my-test-bucket"), eq(Duration.ofMinutes(5)));
  }

  @Test
  void apply_shouldSkip_whenBucketNameIsNullOrEmpty() {

    config.setName("");

    step = new S3BucketStep(s3, config, metadata);
    StepResult<S3Output> result = step.apply();

    assertThat(result.getStepName()).isEqualTo(S3BucketStep.class.getName());
    assertThat(result.getOutputs()).isEmpty();

    verify(s3, never()).create(anyString());
    verify(s3, never()).waitUntilBucketExists(anyString(), any());
  }

  @Test
  void generateDesiredState_shouldReturnConfigValues_whenNotMarkedForDeletion() {

    config.setToDelete(false);

    StepResult<S3Output> result = step.generateDesiredState();

    assertThat(result.getStepName()).isEqualTo(S3BucketStep.class.getName());
    assertThat(result.getOutputs()).containsEntry(S3Output.NAME, "my-test-bucket");
    assertThat(result.getOutputs()).containsEntry(S3Output.REGION, "eu-west-1");
  }

  @Test
  void generateDesiredState_shouldReturnEmptyOutput_whenMarkedForDeletion() {

    config.setToDelete(true);

    StepResult<S3Output> result = step.generateDesiredState();

    assertThat(result.getStepName()).isEqualTo(S3BucketStep.class.getName());
    assertThat(result.getOutputs()).isEmpty();
  }

  @Test
  void getCurrentState_shouldReturnBucketInfo_whenBucketExists() {

    doReturn(HeadBucketResponse.builder().build()).when(s3).head("my-test-bucket");

    StepResult<S3Output> result = step.getCurrentState();

    assertThat(result.getStepName()).isEqualTo(S3BucketStep.class.getName());
    assertThat(result.getOutputs()).containsEntry(S3Output.NAME, "my-test-bucket");
    assertThat(result.getOutputs()).containsEntry(S3Output.REGION, "eu-west-1");
  }

  @Test
  void getCurrentState_shouldReturnEmptyResult_whenBucketNotFound() {

    CloudResourceStepException exception = new CloudResourceStepException(
        "No such bucket", NoSuchBucketException.builder().message("No such bucket").build());

    doThrow(exception).when(s3).head("my-test-bucket");

    StepResult<S3Output> result = step.getCurrentState();

    assertThat(result.getStepName()).isEqualTo(S3BucketStep.class.getName());
    assertThat(result.getOutputs()).isEmpty();
  }

  @Test
  void getCurrentState_shouldThrow_whenUnexpectedExceptionOccurs() {

    CloudResourceStepException exception = new CloudResourceStepException("Unexpected", new RuntimeException(""));

    doThrow(exception).when(s3).head("my-test-bucket");

    assertThatThrownBy(() -> step.getCurrentState())
        .isInstanceOf(CloudResourceStepException.class)
        .hasMessage("Unexpected");
  }

  @Test
  void destroy_shouldDeleteBucket_whenPreventDestroyIsFalseOrNull() {

    metadata = StepResult.<S3Output>builder()
        .stepName(S3PersistentMetadataStep.class.getName())
        .put(S3Output.NAME, "my-test-bucket")
        .put(S3Output.PREVENT_DESTROY, Boolean.FALSE)
        .build();

    step = new S3BucketStep(s3, config, metadata);

    StepResult<S3Output> result = step.destroy(true);

    assertThat(result.getStepName()).isEqualTo(S3BucketStep.class.getName());
    verify(s3).delete("my-test-bucket");
  }

  @Test
  void destroy_shouldDeleteBucket_whenEnforcePreventDestroyIsFalse() {

    metadata = StepResult.<S3Output>builder()
        .stepName(S3PersistentMetadataStep.class.getName())
        .put(S3Output.NAME, "my-test-bucket")
        .put(S3Output.PREVENT_DESTROY, true)
        .build();

    step = new S3BucketStep(s3, config, metadata);

    StepResult<S3Output> result = step.destroy(false);

    assertThat(result.getStepName()).isEqualTo(S3BucketStep.class.getName());
    verify(s3).delete("my-test-bucket");
  }

  @Test
  void destroy_shouldThrow_whenPreventDestroyIsTrueAndEnforced() {

    metadata = StepResult.<S3Output>builder()
        .stepName(S3PersistentMetadataStep.class.getName())
        .put(S3Output.NAME, "my-test-bucket")
        .put(S3Output.PREVENT_DESTROY, true)
        .build();

    step = new S3BucketStep(s3, config, metadata);

    assertThatThrownBy(() -> step.destroy(true))
        .isInstanceOf(CloudResourceStepException.class)
        .hasMessageContaining("Destruction of bucket 'my-test-bucket' is prevented");

    verify(s3, never()).delete(any());
  }

  @Test
  void revert_shouldReturnBucketStepResultAndLogWarning() {

    StepResult<S3Output> previous = StepResult.<S3Output>builder()
        .stepName("previous-step")
        .put(S3Output.NAME, "my-test-bucket")
        .put(S3Output.REGION, "eu-west-1")
        .build();

    StepResult<S3Output> result = step.revert(previous);

    assertThat(result.getStepName()).isEqualTo(S3BucketStep.class.getName());
    assertThat(result.getOutputs()).containsEntry(S3Output.NAME, "my-test-bucket");
    assertThat(result.getOutputs()).containsEntry(S3Output.REGION, "eu-west-1");
  }

}
