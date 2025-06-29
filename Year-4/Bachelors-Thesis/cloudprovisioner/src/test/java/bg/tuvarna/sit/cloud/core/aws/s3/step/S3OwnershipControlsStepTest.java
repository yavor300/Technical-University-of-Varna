package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.aws.s3.config.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.model.S3OwnershipType;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.s3.model.GetBucketOwnershipControlsResponse;
import software.amazon.awssdk.services.s3.model.ObjectOwnership;
import software.amazon.awssdk.services.s3.model.OwnershipControls;
import software.amazon.awssdk.services.s3.model.OwnershipControlsRule;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class S3OwnershipControlsStepTest {

  @Mock
  private S3SafeClient s3;

  private S3BucketConfig config;
  private StepResult<S3Output> metadata;
  private S3OwnershipControlsStep step;

  @BeforeEach
  void setUp() {
    config = new S3BucketConfig();
    config.setOwnershipControls(S3OwnershipType.BUCKET_OWNER_ENFORCED);

    metadata = StepResult.<S3Output>builder()
        .stepName("metadata-step")
        .put(S3Output.NAME, "test-bucket")
        .build();

    step = new S3OwnershipControlsStep(s3, config, metadata);
  }

  @Test
  void apply_shouldApplyAndReturnOwnershipControls() {

    String bucket = "test-bucket";

    GetBucketOwnershipControlsResponse response = GetBucketOwnershipControlsResponse.builder()
        .ownershipControls(
            OwnershipControls.builder()
                .rules(List.of(
                    OwnershipControlsRule.builder()
                        .objectOwnership(ObjectOwnership.BUCKET_OWNER_ENFORCED)
                        .build()
                ))
                .build()
        ).build();

    when(s3.getOwnershipControls(bucket)).thenReturn(response);

    StepResult<S3Output> result = step.apply();

    assertThat(result.getStepName()).isEqualTo(S3OwnershipControlsStep.class.getName());
    assertThat(result.getOutputs().get(S3Output.TYPE)).isEqualTo("BucketOwnerEnforced");

    verify(s3).putOwnershipControls(bucket, ObjectOwnership.BUCKET_OWNER_ENFORCED);
    verify(s3).getOwnershipControls(bucket);
  }

  @Test
  void generateDesiredState_shouldReturnOwnershipType_WhenOwnershipIsSet() {

    config.setOwnershipControls(S3OwnershipType.BUCKET_OWNER_ENFORCED);
    step = new S3OwnershipControlsStep(s3, config, metadata);

    StepResult<S3Output> result = step.generateDesiredState();

    assertThat(result.getStepName()).isEqualTo(S3OwnershipControlsStep.class.getName());
    assertThat(result.getOutputs().get(S3Output.TYPE))
        .isEqualTo(S3OwnershipType.BUCKET_OWNER_ENFORCED.getValue());
  }

  @Test
  void generateDesiredState_shouldNotFail_WhenOwnershipIsNull() {

    config.setOwnershipControls(null);
    step = new S3OwnershipControlsStep(s3, config, metadata);

    StepResult<S3Output> result = step.generateDesiredState();

    assertThat(result.getStepName()).isEqualTo(S3OwnershipControlsStep.class.getName());
    assertThat(result.getOutputs().get(S3Output.TYPE)).isEqualTo(S3OwnershipType.BUCKET_OWNER_ENFORCED.getValue());
  }

  @Test
  void getCurrentState_shouldReturnOwnershipControls_whenPresent() {

    GetBucketOwnershipControlsResponse response = GetBucketOwnershipControlsResponse.builder()
        .ownershipControls(OwnershipControls.builder()
            .rules(List.of(
                OwnershipControlsRule.builder()
                    .objectOwnership(ObjectOwnership.BUCKET_OWNER_ENFORCED)
                    .build()))
            .build())
        .build();

    when(s3.getOwnershipControls("test-bucket")).thenReturn(response);

    StepResult<S3Output> result = step.getCurrentState();

    assertThat(result.getStepName()).isEqualTo(S3OwnershipControlsStep.class.getName());
    assertThat(result.getOutputs().get(S3Output.TYPE)).isEqualTo(S3OwnershipType.BUCKET_OWNER_ENFORCED.getValue());
  }

  @Test
  void getCurrentState_shouldReturnEmptyResult_whenBucketNotFound() {

    AwsServiceException notFound = S3Exception.builder()
        .statusCode(404)
        .message("Not Found")
        .build();

    when(s3.getOwnershipControls("test-bucket"))
        .thenThrow(new CloudResourceStepException("not found", notFound));

    StepResult<S3Output> result = step.getCurrentState();

    assertThat(result.getStepName()).isEqualTo(S3OwnershipControlsStep.class.getName());
    assertThat(result.getOutputs()).isEmpty();
  }

  @Test
  void getCurrentState_shouldThrow_whenUnexpectedException() {

    RuntimeException cause = new RuntimeException("Internal error");
    CloudResourceStepException wrapped = new CloudResourceStepException("error", cause);

    when(s3.getOwnershipControls("test-bucket")).thenThrow(wrapped);

    assertThatThrownBy(() -> step.getCurrentState())
        .isInstanceOf(CloudResourceStepException.class)
        .hasCause(cause);
  }

  @Test
  void destroy_shouldApplyDefaultOwnershipControlsAndReturnResult() {

    String bucketName = "test-bucket";

    GetBucketOwnershipControlsResponse mockResponse = GetBucketOwnershipControlsResponse.builder()
        .ownershipControls(OwnershipControls.builder()
            .rules(List.of(
                OwnershipControlsRule.builder()
                    .objectOwnership(ObjectOwnership.BUCKET_OWNER_ENFORCED)
                    .build()))
            .build())
        .build();

    when(s3.getOwnershipControls(bucketName)).thenReturn(mockResponse);

    StepResult<S3Output> result = step.destroy(true);

    verify(s3).putOwnershipControls(bucketName, ObjectOwnership.BUCKET_OWNER_ENFORCED);
    verify(s3).getOwnershipControls(bucketName);

    assertThat(result.getStepName()).isEqualTo(S3OwnershipControlsStep.class.getName());
    assertThat(result.getOutputs().get(S3Output.TYPE)).isEqualTo("BucketOwnerEnforced");
  }

  @Test
  void revert_shouldRestorePreviousOwnershipControls() {

    String bucketName = "test-bucket";
    StepResult<S3Output> previous = StepResult.<S3Output>builder()
        .stepName("previous-step")
        .put(S3Output.TYPE, "BucketOwnerPreferred")
        .build();

    GetBucketOwnershipControlsResponse mockResponse = GetBucketOwnershipControlsResponse.builder()
        .ownershipControls(OwnershipControls.builder()
            .rules(List.of(
                OwnershipControlsRule.builder()
                    .objectOwnership(ObjectOwnership.BUCKET_OWNER_PREFERRED)
                    .build()))
            .build())
        .build();

    when(s3.getOwnershipControls(bucketName)).thenReturn(mockResponse);

    StepResult<S3Output> result = step.revert(previous);

    verify(s3).putOwnershipControls(bucketName, ObjectOwnership.BUCKET_OWNER_PREFERRED);
    verify(s3).getOwnershipControls(bucketName);

    assertThat(result.getStepName()).isEqualTo(S3OwnershipControlsStep.class.getName());
    assertThat(result.getOutputs().get(S3Output.TYPE)).isEqualTo("BucketOwnerPreferred");
  }

}
