package bg.tuvarna.sit.cloud.core.aws.eks.step;

import bg.tuvarna.sit.cloud.core.aws.eks.EksClusterOutput;
import bg.tuvarna.sit.cloud.core.aws.eks.client.EksSafeClient;
import bg.tuvarna.sit.cloud.core.aws.eks.config.EksClusterConfig;
import bg.tuvarna.sit.cloud.core.aws.eks.step.base.EksClusterProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.common.model.ProvisionedTags;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionOrder;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import lombok.extern.slf4j.Slf4j;

import org.apache.hc.core5.http.HttpStatus;

import software.amazon.awssdk.services.eks.model.EksException;

import java.util.Map;

@Slf4j
@ProvisionOrder(2)
@Singleton
public class EksClusterTaggingStep extends EksClusterProvisionStep {

  private final StepResult<EksClusterOutput> metadata;

  @Inject
  protected EksClusterTaggingStep(EksSafeClient eks, EksClusterConfig config, StepResult<EksClusterOutput> metadata) {
    super(eks, config);
    this.metadata = metadata;
  }

  @Override
  public StepResult<EksClusterOutput> apply() {

    String step = this.getClass().getName();
    String arn = (String) metadata.getOutputs().get(EksClusterOutput.ARN);
    Map<String, String> desiredTags = config.getTags();

    if (desiredTags.isEmpty()) {
      log.info("No tags to apply to cluster '{}'", arn);
      return StepResult.<EksClusterOutput>builder().stepName(step).build();
    }

    eks.putTags(arn, desiredTags);
    log.info("Successfully applied tags '{}' to EKS cluster '{}'", desiredTags, arn);

    Map<String, String> actualTags = eks.getTags(arn);
    log.info("Successfully verified tags '{}' on EKS cluster '{}'", actualTags, arn);

    return StepResult.<EksClusterOutput>builder()
        .stepName(step)
        .put(EksClusterOutput.TAGS, new ProvisionedTags(actualTags))
        .build();
  }

  @Override
  public StepResult<EksClusterOutput> generateDesiredState() {

    String step = this.getClass().getName();

    if (config.isToDelete()) {
      return StepResult.<EksClusterOutput>builder()
          .stepName(step)
          .build();
    }

    StepResult.Builder<EksClusterOutput> builder = StepResult.<EksClusterOutput>builder()
        .stepName(step)
        .put(EksClusterOutput.TAGS, new ProvisionedTags(config.getTags()));

    return builder.build();
  }

  @Override
  public StepResult<EksClusterOutput> getCurrentState() {

    String step = this.getClass().getName();
    String arn = (String) metadata.getOutputs().get(EksClusterOutput.ARN);

    try {

      Map<String, String> tags = eks.getTags(arn);
      log.info("Fetched tags for EKS cluster '{}'", arn);

      return StepResult.<EksClusterOutput>builder()
          .stepName(step)
          .put(EksClusterOutput.TAGS, new ProvisionedTags(tags))
          .build();

    } catch (CloudResourceStepException e) {
      if (e.getCause() instanceof EksException eksException) {
        if (eksException.statusCode() == HttpStatus.SC_NOT_FOUND) {
          log.info("EKS cluster '{}' not found when retrieving tags.", arn);
          return StepResult.<EksClusterOutput>builder().stepName(step).build();
        }
      }
      throw e;
    }
  }

  @Override
  public StepResult<EksClusterOutput> destroy(boolean enforcePreventDestroy) {

    String step = this.getClass().getName();
    String arn = (String) metadata.getOutputs().get(EksClusterOutput.ARN);

    eks.deleteTags(arn);
    log.info("Deleted all tags from EKS cluster '{}'", arn);

    return StepResult.<EksClusterOutput>builder()
        .stepName(step)
        .build();
  }

  @Override
  public StepResult<EksClusterOutput> revert(StepResult<EksClusterOutput> previous) {

    String step = this.getClass().getName();
    String arn = (String) metadata.getOutputs().get(EksClusterOutput.ARN);
    ProvisionedTags revert = (ProvisionedTags) previous.getOutputs().get(EksClusterOutput.TAGS);

    if (revert == null || revert.getTags().isEmpty()) {
      eks.deleteTags(arn);
      log.info("Reverted by removing all tags from EKS cluster '{}'", arn);

      return StepResult.<EksClusterOutput>builder()
          .stepName(step)
          .build();
    }

    eks.putTags(arn, revert.getTags());
    log.info("Reverted tags on EKS cluster '{}' to '{}'", arn, revert.getTags());

    Map<String, String> currentTags = eks.getTags(arn);

    return StepResult.<EksClusterOutput>builder()
        .stepName(step)
        .put(EksClusterOutput.TAGS, new ProvisionedTags(currentTags))
        .build();
  }
}
