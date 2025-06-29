package bg.tuvarna.sit.cloud.core.aws.s3.step;

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
import software.amazon.awssdk.services.s3.model.GetBucketPolicyResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class S3PolicyStepTest {

  @Mock
  private S3SafeClient s3;

  private S3BucketConfig config;
  private S3PolicyStep step;

  @BeforeEach
  void setup() {

    config = new S3BucketConfig();
    StepResult<S3Output> metadata = StepResult.<S3Output>builder()
        .stepName("metadata-step")
        .put(S3Output.NAME, "test-bucket")
        .build();
    step = new S3PolicyStep(s3, config, metadata);
  }

  @Test
  void apply_shouldSkipPolicyApplication_whenPolicyIsBlank() {
    config.setPolicy("   ");

    StepResult<S3Output> result = step.apply();

    assertThat(result.getStepName()).isEqualTo(S3PolicyStep.class.getName());
    assertThat(result.getOutputs().get(S3Output.VALUE_NODE)).isEqualTo(null);
    verifyNoInteractions(s3);
  }

  @Test
  void apply_shouldApplyPolicyAndReturnSanitizedResult() {
    String originalPolicy = "{\n  \"Version\": \"2012-10-17\",\n  \"Statement\": []\n}";
    String expectedSanitized = "{\"Version\":\"2012-10-17\",\"Statement\":[]}";

    config.setPolicy(originalPolicy);

    when(s3.getPolicy("test-bucket")).thenReturn(
        GetBucketPolicyResponse.builder().policy(originalPolicy).build()
    );

    StepResult<S3Output> result = step.apply();

    assertThat(result.getStepName()).isEqualTo(S3PolicyStep.class.getName());
    assertThat(result.getOutputs().get(S3Output.VALUE_NODE)).isEqualTo(expectedSanitized);

    verify(s3).putPolicy("test-bucket", originalPolicy);
    verify(s3).getPolicy("test-bucket");
  }

  @Test
  void generateDesiredState_shouldReturnSanitizedPolicy_whenPolicyIsNonBlank() {

    String original = "{ \"Version\": \"2012-10-17\", \"Statement\": [] }";
    config.setPolicy(original);

    StepResult<S3Output> result = step.generateDesiredState();

    assertThat(result.getOutputs()).containsEntry(S3Output.VALUE_NODE, "{\"Version\":\"2012-10-17\",\"Statement\":[]}");
  }

  @Test
  void generateDesiredState_shouldNotContainValueNode_whenPolicyIsBlank() {

    config.setPolicy("   ");

    StepResult<S3Output> result = step.generateDesiredState();

    assertThat(result.getOutputs()).doesNotContainKey(S3Output.VALUE_NODE);
  }

  @Test
  void getCurrentState_shouldReturnSanitizedPolicy_whenPolicyExists() {

    String bucket = "test-bucket";
    String policy = "{ \"Version\": \"2012-10-17\", \"Statement\": [] }";

    GetBucketPolicyResponse response = GetBucketPolicyResponse.builder()
        .policy(policy)
        .build();

    when(s3.getPolicy(bucket)).thenReturn(response);

    StepResult<S3Output> result = step.getCurrentState();

    assertThat(result.getStepName()).isEqualTo(S3PolicyStep.class.getName());
    assertThat(result.getOutputs()).containsEntry(S3Output.VALUE_NODE,
        policy.replaceAll("\n", "").replaceAll("\\s+", ""));
  }

  @Test
  void getCurrentState_shouldReturnEmptyResult_whenPolicyNotFound() {

    String bucket = "test-bucket";
    AwsServiceException notFoundException = S3Exception.builder().statusCode(404).build();

    when(s3.getPolicy(bucket)).thenThrow(new CloudResourceStepException("not found", notFoundException));

    StepResult<S3Output> result = step.getCurrentState();

    assertThat(result.getStepName()).isEqualTo(S3PolicyStep.class.getName());
    assertThat(result.getOutputs()).isEmpty();
  }

  @Test
  void getCurrentState_shouldThrow_whenUnexpectedErrorOccurs() {

    String bucket = "test-bucket";
    RuntimeException cause = new RuntimeException("Internal error");

    when(s3.getPolicy(bucket)).thenThrow(new CloudResourceStepException("failure", cause));

    assertThrows(CloudResourceStepException.class, () -> step.getCurrentState());
  }

  @Test
  void destroy_shouldDeletePolicyAndReturnEmptyResult() {

    StepResult<S3Output> result = step.destroy(true);

    verify(s3).deletePolicy("test-bucket");

    assertThat(result).isNotNull();
    assertThat(result.getStepName()).isEqualTo(S3PolicyStep.class.getName());
    assertThat(result.getOutputs()).isEmpty();
  }

  @Test
  void revert_shouldPutPolicyAndReturnResult_whenPolicyIsNotEmpty() {

    String policy = "{\"Version\":\"2012-10-17\",\"Statement\":[]}";
    StepResult<S3Output> previous = StepResult.<S3Output>builder()
        .stepName("some-step")
        .put(S3Output.VALUE_NODE, policy)
        .build();

    GetBucketPolicyResponse mockResponse = GetBucketPolicyResponse.builder()
        .policy(policy)
        .build();

    when(s3.getPolicy("test-bucket")).thenReturn(mockResponse);

    StepResult<S3Output> result = step.revert(previous);

    verify(s3).putPolicy("test-bucket", policy);
    verify(s3).getPolicy("test-bucket");

    assertThat(result.getStepName()).isEqualTo(S3PolicyStep.class.getName());
    assertThat(result.getOutputs().get(S3Output.VALUE_NODE))
        .isEqualTo(policy.replaceAll("\n", "").replaceAll("\\s+", ""));
  }

  @Test
  void revert_shouldDeletePolicy_whenPolicyIsNullOrEmpty() {

    StepResult<S3Output> previous = StepResult.<S3Output>builder()
        .stepName("some-step")
        .put(S3Output.VALUE_NODE, "")
        .build();

    StepResult<S3Output> result = step.revert(previous);

    verify(s3).deletePolicy("test-bucket");
    assertThat(result.getStepName()).isEqualTo(S3PolicyStep.class.getName());
    assertThat(result.getOutputs()).isEmpty();
  }

}
