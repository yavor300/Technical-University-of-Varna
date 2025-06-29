package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.aws.s3.config.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class S3PersistentMetadataStepTest {

  @Mock
  private S3SafeClient s3;

  private S3BucketConfig config;
  private StepResult<S3Output> metadata;
  private S3PersistentMetadataStep step;

  @BeforeEach
  void setup() {
    config = new S3BucketConfig();
  }

  @Test
  void apply_shouldBuildStepResult_withConfigPreventDestroy() {

    config.setPreventDestroy(true);

    metadata = StepResult.<S3Output>builder()
        .stepName("metadata")
        .put(S3Output.NAME, "test-bucket")
        .put(S3Output.REGION, "eu-west-1")
        .put(S3Output.ARN, "arn:aws:s3:::test-bucket")
        .put(S3Output.PREVENT_DESTROY, false)
        .build();

    step = new S3PersistentMetadataStep(s3, config, metadata);

    StepResult<S3Output> result = step.apply();

    assertThat(result.getStepName()).isEqualTo(S3PersistentMetadataStep.class.getName());
    assertThat(result.getOutputs().get(S3Output.NAME)).isEqualTo("test-bucket");
    assertThat(result.getOutputs().get(S3Output.REGION)).isEqualTo("eu-west-1");
    assertThat(result.getOutputs().get(S3Output.ARN)).isEqualTo("arn:aws:s3:::test-bucket");
    assertThat(result.getOutputs().get(S3Output.PREVENT_DESTROY)).isEqualTo(true); // from config
  }

  @Test
  void apply_shouldFallbackToMetadataPreventDestroy_ifConfigValueIsNull() {

    config.setPreventDestroy(null);

    metadata = StepResult.<S3Output>builder()
        .stepName("metadata")
        .put(S3Output.NAME, "test-bucket")
        .put(S3Output.REGION, "us-east-1")
        .put(S3Output.ARN, "arn:aws:s3:::test-bucket")
        .put(S3Output.PREVENT_DESTROY, false)
        .build();

    step = new S3PersistentMetadataStep(s3, config, metadata);

    StepResult<S3Output> result = step.apply();

    assertThat(result.getOutputs().get(S3Output.PREVENT_DESTROY)).isEqualTo(false);
  }

  @Test
  void generateDesiredState_shouldFallbackToMetadataPreventDestroy_ifConfigValueIsNull() {

    config.setPreventDestroy(null);

    metadata = StepResult.<S3Output>builder()
        .stepName("metadata")
        .put(S3Output.NAME, "test-bucket")
        .put(S3Output.REGION, "us-east-1")
        .put(S3Output.ARN, "arn:aws:s3:::test-bucket")
        .put(S3Output.PREVENT_DESTROY, false)
        .build();

    step = new S3PersistentMetadataStep(s3, config, metadata);

    StepResult<S3Output> result = step.generateDesiredState();

    assertThat(result.getOutputs().get(S3Output.PREVENT_DESTROY)).isEqualTo(false);
  }

  @Test
  void getCurrentState_shouldReturnValuesFromMetadata() {

    metadata = StepResult.<S3Output>builder()
        .stepName("metadata")
        .put(S3Output.NAME, "test-bucket")
        .put(S3Output.REGION, "eu-west-1")
        .put(S3Output.ARN, "arn:aws:s3:::test-bucket")
        .put(S3Output.PREVENT_DESTROY, false)
        .build();

    step = new S3PersistentMetadataStep(s3, config, metadata);

    StepResult<S3Output> result = step.getCurrentState();

    assertThat(result.getStepName()).isEqualTo(S3PersistentMetadataStep.class.getName());
    assertThat(result.getOutputs().get(S3Output.NAME)).isEqualTo("test-bucket");
    assertThat(result.getOutputs().get(S3Output.REGION)).isEqualTo("eu-west-1");
    assertThat(result.getOutputs().get(S3Output.ARN)).isEqualTo("arn:aws:s3:::test-bucket");
    assertThat(result.getOutputs().get(S3Output.PREVENT_DESTROY)).isEqualTo(false);
  }

  @Test
  void destroy_shouldReturnNull() {
    step = new S3PersistentMetadataStep(s3, config, metadata);
    StepResult<S3Output> result = step.destroy(true);
    assertThat(result).isNull();
  }

  @Test
  void revert_shouldReturnNull() {
    step = new S3PersistentMetadataStep(s3, config, metadata);
    StepResult<S3Output> result = step.revert(metadata);
    assertThat(result).isNull();
  }

}
