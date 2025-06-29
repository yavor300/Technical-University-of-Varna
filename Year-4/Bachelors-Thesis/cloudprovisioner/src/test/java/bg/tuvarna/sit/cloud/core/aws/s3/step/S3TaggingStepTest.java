package bg.tuvarna.sit.cloud.core.aws.s3.step;

import static org.assertj.core.api.Assertions.assertThat;
import static org.gradle.internal.impldep.org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

import bg.tuvarna.sit.cloud.core.aws.common.model.ProvisionedTags;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.aws.s3.config.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.s3.model.GetBucketTaggingResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.Tag;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class S3TaggingStepTest {

  @Mock
  private S3SafeClient s3;

  private S3BucketConfig config;
  private S3TaggingStep step;

  @BeforeEach
  void setup() {
    config = new S3BucketConfig();
    StepResult<S3Output> metadata = StepResult.<S3Output>builder()
        .stepName("metadata-step")
        .put(S3Output.NAME, "test-bucket")
        .build();

    step = new S3TaggingStep(s3, config, metadata);
  }

  @Test
  void apply_shouldReturnEmptyResult_whenNoTagsProvided() {

    config.setTags(Map.of());

    StepResult<S3Output> result = step.apply();

    verifyNoInteractions(s3);
    assertThat(result.getStepName()).isEqualTo(S3TaggingStep.class.getName());
    assertThat(result.getOutputs()).isEmpty();
  }

  @Test
  void apply_shouldPutAndGetTags_whenTagsProvided() {

    Map<String, String> tags = Map.of("env", "dev", "owner", "team-a");
    config.setTags(tags);

    List<Tag> expectedTags = tags.entrySet().stream()
        .map(e -> Tag.builder().key(e.getKey()).value(e.getValue()).build())
        .toList();

    GetBucketTaggingResponse mockResponse = GetBucketTaggingResponse.builder()
        .tagSet(expectedTags)
        .build();

    when(s3.getTags("test-bucket")).thenReturn(mockResponse);

    StepResult<S3Output> result = step.apply();

    verify(s3).putTags("test-bucket", expectedTags);
    verify(s3).getTags("test-bucket");

    assertThat(result.getStepName()).isEqualTo(S3TaggingStep.class.getName());
    assertThat(result.getOutputs()).containsKey(S3Output.VALUE_NODE);

    ProvisionedTags provisioned = (ProvisionedTags) result.getOutputs().get(S3Output.VALUE_NODE);
    assertThat(provisioned.getTags()).containsAllEntriesOf(tags);
  }

  @Test
  void generateDesiredState_shouldReturnEmptyResult_whenNoTagsConfigured() {

    config.setTags(Collections.emptyMap());

    StepResult<S3Output> result = step.generateDesiredState();

    assertThat(result.getStepName()).isEqualTo(S3TaggingStep.class.getName());
    assertThat(result.getOutputs()).isEmpty();
  }

  @Test
  void generateDesiredState_shouldReturnTags_whenTagsConfigured() {

    Map<String, String> tags = Map.of("env", "prod", "team", "devops");
    config.setTags(tags);

    StepResult<S3Output> result = step.generateDesiredState();

    assertThat(result.getStepName()).isEqualTo(S3TaggingStep.class.getName());
    assertThat(result.getOutputs()).containsKey(S3Output.VALUE_NODE);

    ProvisionedTags actualTags = (ProvisionedTags) result.getOutputs().get(S3Output.VALUE_NODE);
    assertThat(actualTags.getTags()).containsAllEntriesOf(tags);
  }

  @Test
  void getCurrentState_shouldReturnTagResult_whenTagsExist() {

    GetBucketTaggingResponse response = GetBucketTaggingResponse.builder()
        .tagSet(
            Tag.builder().key("env").value("prod").build(),
            Tag.builder().key("team").value("infra").build()
        )
        .build();

    when(s3.getTags("test-bucket")).thenReturn(response);

    StepResult<S3Output> result = step.getCurrentState();

    assertThat(result.getStepName()).isEqualTo(S3TaggingStep.class.getName());
    ProvisionedTags tags = (ProvisionedTags) result.getOutputs().get(S3Output.VALUE_NODE);
    assertThat(tags.getTags()).containsExactlyInAnyOrderEntriesOf(Map.of("env", "prod", "team", "infra"));
  }

  @Test
  void getCurrentState_shouldReturnEmpty_whenTagsNotFound() {

    AwsServiceException s3Exception = S3Exception.builder()
        .statusCode(HttpStatus.SC_NOT_FOUND)
        .message("No tags found")
        .build();

    CloudResourceStepException wrapped = new CloudResourceStepException("Wrapped", s3Exception);

    when(s3.getTags("test-bucket")).thenThrow(wrapped);

    StepResult<S3Output> result = step.getCurrentState();

    assertThat(result.getStepName()).isEqualTo(S3TaggingStep.class.getName());
    assertThat(result.getOutputs()).isEmpty();
  }

  @Test
  void getCurrentState_shouldThrow_whenUnexpectedExceptionOccurs() {

    RuntimeException internal = new RuntimeException("Unexpected");
    CloudResourceStepException wrapped = new CloudResourceStepException("Wrapped", internal);

    when(s3.getTags("test-bucket")).thenThrow(wrapped);

    assertThrows(CloudResourceStepException.class, () -> step.getCurrentState());
  }

  @Test
  void destroy_shouldDeleteTagsAndReturnEmptyResult() {

    StepResult<S3Output> result = step.destroy(false);

    verify(s3).deleteTags("test-bucket");

    assertThat(result).isNotNull();
    assertThat(result.getStepName()).isEqualTo(S3TaggingStep.class.getName());
    assertThat(result.getOutputs()).isEmpty();
  }

  @Test
  void revert_shouldDeleteTags_whenNoPreviousTagsExist() {

    StepResult<S3Output> previous = StepResult.<S3Output>builder()
        .stepName("previous-step")
        .put(S3Output.VALUE_NODE, new ProvisionedTags(Map.of()))
        .build();

    StepResult<S3Output> result = step.revert(previous);

    verify(s3).deleteTags("test-bucket");
    assertThat(result.getStepName()).isEqualTo(S3TaggingStep.class.getName());
    assertThat(result.getOutputs()).isEmpty();
  }

  @Test
  void revert_shouldPutTags_whenPreviousTagsExist() {

    Map<String, String> previousTags = Map.of("env", "test", "team", "dev");
    ProvisionedTags provisionedTags = new ProvisionedTags(previousTags);
    StepResult<S3Output> previous = StepResult.<S3Output>builder()
        .stepName("previous-step")
        .put(S3Output.VALUE_NODE, provisionedTags)
        .build();

    GetBucketTaggingResponse response = GetBucketTaggingResponse.builder()
        .tagSet(
            Tag.builder().key("env").value("test").build(),
            Tag.builder().key("team").value("dev").build()
        )
        .build();

    when(s3.getTags("test-bucket")).thenReturn(response);

    StepResult<S3Output> result = step.revert(previous);

    verify(s3).putTags(eq("test-bucket"), argThat(tags ->
        tags.size() == 2 &&
            tags.stream().anyMatch(t -> t.key().equals("env") && t.value().equals("test")) &&
            tags.stream().anyMatch(t -> t.key().equals("team") && t.value().equals("dev"))
    ));

    verify(s3).getTags("test-bucket");

    assertThat(result.getStepName()).isEqualTo(S3TaggingStep.class.getName());
    assertThat(result.getOutputs()).containsKey(S3Output.VALUE_NODE);
    ProvisionedTags outputTags = (ProvisionedTags) result.getOutputs().get(S3Output.VALUE_NODE);
    assertThat(outputTags.getTags()).containsAllEntriesOf(previousTags);
  }
}
