package bg.tuvarna.sit.cloud.core.aws.eks.step;

import bg.tuvarna.sit.cloud.core.aws.common.model.ProvisionedTags;
import bg.tuvarna.sit.cloud.core.aws.eks.EksClusterOutput;
import bg.tuvarna.sit.cloud.core.aws.eks.client.EksSafeClient;
import bg.tuvarna.sit.cloud.core.aws.eks.config.EksClusterConfig;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.eks.model.EksException;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EksClusterTaggingStepTest {

  @Mock
  private EksSafeClient eks;

  private EksClusterConfig config;

  private StepResult<EksClusterOutput> metadata;

  private EksClusterTaggingStep step;

  @BeforeEach
  void setup() {
    config = new EksClusterConfig();
    metadata = StepResult.<EksClusterOutput>builder()
        .stepName(EksClusterPersistentMetadataStep.class.getName())
        .put(EksClusterOutput.ARN, "arn:aws:eks:eu-west-1:000000000000:cluster/test-cluster")
        .build();
  }

  @Test
  void apply_shouldSkip_whenNoTagsProvided() {

    config.setTags(Collections.emptyMap());
    step = new EksClusterTaggingStep(eks, config, metadata);

    StepResult<EksClusterOutput> result = step.apply();

    assertThat(result.getStepName()).isEqualTo(EksClusterTaggingStep.class.getName());
    assertThat(result.getOutputs()).isEmpty();
    verifyNoInteractions(eks);
  }

  @Test
  void apply_shouldApplyTagsSuccessfully() {

    Map<String, String> tags = Map.of("env", "test", "owner", "dev-team");
    Map<String, String> returnedTags = Map.of("env", "test", "owner", "dev-team");

    config.setTags(tags);
    step = new EksClusterTaggingStep(eks, config, metadata);

    String arn = (String) metadata.getOutputs().get(EksClusterOutput.ARN);

    when(eks.getTags(arn)).thenReturn(returnedTags);

    StepResult<EksClusterOutput> result = step.apply();

    assertThat(result.getStepName()).isEqualTo(EksClusterTaggingStep.class.getName());
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.TAGS, new ProvisionedTags(returnedTags));

    verify(eks).putTags(arn, tags);
    verify(eks).getTags(arn);
  }

  @Test
  void generateDesiredState_shouldReturnEmptyStep_whenMarkedForDeletion() {

    config.setToDelete(true);
    step = new EksClusterTaggingStep(eks, config, metadata);

    StepResult<EksClusterOutput> result = step.generateDesiredState();

    assertThat(result.getStepName()).isEqualTo(EksClusterTaggingStep.class.getName());
    assertThat(result.getOutputs()).isEmpty();
  }

  @Test
  void generateDesiredState_shouldReturnDesiredTags_whenNotMarkedForDeletion() {

    Map<String, String> tags = Map.of("team", "devops", "env", "staging");
    config.setTags(tags);
    step = new EksClusterTaggingStep(eks, config, metadata);

    StepResult<EksClusterOutput> result = step.generateDesiredState();

    assertThat(result.getStepName()).isEqualTo(EksClusterTaggingStep.class.getName());
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.TAGS, new ProvisionedTags(tags));
  }

  @Test
  void getCurrentState_shouldReturnTags_whenArnPresent() {

    Map<String, String> tags = Map.of("project", "alpha");
    when(eks.getTags("arn:aws:eks:eu-west-1:000000000000:cluster/test-cluster")).thenReturn(tags);
    step = new EksClusterTaggingStep(eks, config, metadata);

    StepResult<EksClusterOutput> result = step.getCurrentState();

    assertThat(result.getStepName()).isEqualTo(EksClusterTaggingStep.class.getName());
    assertThat(result.getOutputs()).containsEntry(EksClusterOutput.TAGS, new ProvisionedTags(tags));
  }

  @Test
  void getCurrentState_shouldReturnEmptyStep_whenEksReturnsNotFound() {

    AwsServiceException notFoundException = EksException.builder().statusCode(HttpStatus.SC_NOT_FOUND).build();
    CloudResourceStepException wrapped = new CloudResourceStepException("not found", notFoundException);
    when(eks.getTags("arn:aws:eks:eu-west-1:000000000000:cluster/test-cluster")).thenThrow(wrapped);
    step = new EksClusterTaggingStep(eks, config, metadata);

    StepResult<EksClusterOutput> result = step.getCurrentState();

    assertThat(result.getStepName()).isEqualTo(EksClusterTaggingStep.class.getName());
    assertThat(result.getOutputs()).isEmpty();
  }

  @Test
  void getCurrentState_shouldRethrow_whenUnexpectedExceptionOccurs() {

    RuntimeException cause = new RuntimeException("network failure");
    CloudResourceStepException wrapped = new CloudResourceStepException("wrapped", cause);
    when(eks.getTags("arn:aws:eks:eu-west-1:000000000000:cluster/test-cluster")).thenThrow(wrapped);
    step = new EksClusterTaggingStep(eks, config, metadata);

    assertThatThrownBy(() -> step.getCurrentState())
        .isInstanceOf(CloudResourceStepException.class)
        .hasCause(cause);
  }

  @Test
  void destroy_shouldDeleteTags_whenArnPresent() {

    step = new EksClusterTaggingStep(eks, config, metadata);

    StepResult<EksClusterOutput> result = step.destroy(false);

    verify(eks).deleteTags("arn:aws:eks:eu-west-1:000000000000:cluster/test-cluster");
    assertThat(result.getStepName()).isEqualTo(EksClusterTaggingStep.class.getName());
    assertThat(result.getOutputs()).isEmpty();
  }

  @Test
  void revert_shouldDeleteTags_whenRevertIsNullOrEmpty() {

    StepResult<EksClusterOutput> previous = StepResult.<EksClusterOutput>builder()
        .stepName("previous-step")
        .put(EksClusterOutput.TAGS, new ProvisionedTags(Map.of()))
        .build();

    step = new EksClusterTaggingStep(eks, config, metadata);

    StepResult<EksClusterOutput> result = step.revert(previous);

    verify(eks).deleteTags("arn:aws:eks:eu-west-1:000000000000:cluster/test-cluster");
    assertThat(result.getStepName()).isEqualTo(EksClusterTaggingStep.class.getName());
    assertThat(result.getOutputs()).isEmpty();
  }

  @Test
  void revert_shouldRestoreTags_whenRevertTagsPresent() {

    Map<String, String> tagsToRevert = Map.of("env", "dev", "team", "cloud");
    StepResult<EksClusterOutput> previous = StepResult.<EksClusterOutput>builder()
        .stepName("previous-step")
        .put(EksClusterOutput.TAGS, new ProvisionedTags(tagsToRevert))
        .build();

    when(eks.getTags(anyString())).thenReturn(tagsToRevert);

    step = new EksClusterTaggingStep(eks, config, metadata);

    StepResult<EksClusterOutput> result = step.revert(previous);

    verify(eks).putTags("arn:aws:eks:eu-west-1:000000000000:cluster/test-cluster", tagsToRevert);
    verify(eks).getTags("arn:aws:eks:eu-west-1:000000000000:cluster/test-cluster");

    assertThat(result.getStepName()).isEqualTo(EksClusterTaggingStep.class.getName());
    assertThat(result.getOutputs())
        .containsEntry(EksClusterOutput.TAGS, new ProvisionedTags(tagsToRevert));
  }

}
