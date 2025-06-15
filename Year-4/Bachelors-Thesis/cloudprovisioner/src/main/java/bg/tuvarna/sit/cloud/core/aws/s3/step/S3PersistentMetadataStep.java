package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.config.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.step.base.S3ProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionOrder;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;

import jakarta.inject.Inject;

@ProvisionOrder(0)
public class S3PersistentMetadataStep extends S3ProvisionStep {

  private final StepResult<S3Output> metadata;

  @Inject
  public S3PersistentMetadataStep(S3SafeClient s3, S3BucketConfig config, StepResult<S3Output> metadata) {
    super(s3, config);
    this.metadata = metadata;
  }

  @Override
  public StepResult<S3Output> apply() {

    return buildStepResult();
  }

  @Override
  public StepResult<S3Output> generateDesiredState() {

    return buildStepResult();
  }

  @Override
  public StepResult<S3Output> getCurrentState() {

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);
    String region = (String) metadata.getOutputs().get(S3Output.REGION);
    String arn = (String) metadata.getOutputs().get(S3Output.ARN);
    Boolean preventDestroy = (Boolean) metadata.getOutputs().get(S3Output.PREVENT_DESTROY);

    return StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .put(S3Output.NAME, bucket)
        .put(S3Output.REGION, region)
        .put(S3Output.ARN, arn)
        .put(S3Output.PREVENT_DESTROY, preventDestroy)
        .build();
  }

  @Override
  public StepResult<S3Output> destroy(boolean enforcePreventDestroy) {

    return null;
  }

  @Override
  public StepResult<S3Output> revert(StepResult<S3Output> previous) {

    return null;
  }

  private StepResult<S3Output> buildStepResult() {

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);
    String region = (String) metadata.getOutputs().get(S3Output.REGION);
    String arn = (String) metadata.getOutputs().get(S3Output.ARN);

    return StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .put(S3Output.NAME, bucket)
        .put(S3Output.REGION, region)
        .put(S3Output.ARN, arn)
        .put(S3Output.PREVENT_DESTROY, config.preventDestroy())
        .build();
  }
}
