package bg.tuvarna.sit.cloud.core.aws.eks.step;

import bg.tuvarna.sit.cloud.core.aws.eks.EksClusterOutput;
import bg.tuvarna.sit.cloud.core.aws.eks.client.EksSafeClient;
import bg.tuvarna.sit.cloud.core.aws.eks.config.EksClusterConfig;
import bg.tuvarna.sit.cloud.core.aws.eks.step.base.EksClusterProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionOrder;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@ProvisionOrder(0)
@Singleton
public class EksClusterPersistentMetadataStep extends EksClusterProvisionStep {

  private final StepResult<EksClusterOutput> metadata;

  @Inject
  public EksClusterPersistentMetadataStep(EksSafeClient eks, EksClusterConfig config, StepResult<EksClusterOutput> metadata) {
    super(eks, config);
    this.metadata = metadata;
  }

  @Override
  public StepResult<EksClusterOutput> apply() {

    return buildStepResult();
  }

  @Override
  public StepResult<EksClusterOutput> generateDesiredState() {

    return buildStepResult();
  }

  @Override
  public StepResult<EksClusterOutput> getCurrentState() {

    String bucket = (String) metadata.getOutputs().get(EksClusterOutput.NAME);
    String region = (String) metadata.getOutputs().get(EksClusterOutput.REGION);
    String arn = (String) metadata.getOutputs().get(EksClusterOutput.ARN);
    Boolean preventDestroy = (Boolean) metadata.getOutputs().get(EksClusterOutput.PREVENT_DESTROY);

    return StepResult.<EksClusterOutput>builder()
        .stepName(this.getClass().getName())
        .put(EksClusterOutput.NAME, bucket)
        .put(EksClusterOutput.REGION, region)
        .put(EksClusterOutput.ARN, arn)
        .put(EksClusterOutput.PREVENT_DESTROY, preventDestroy)
        .build();
  }

  // TODO [Enhancement] Impossible to reach handling
  @Override
  public StepResult<EksClusterOutput> destroy(boolean enforcePreventDestroy) {

    return null;
  }

  @Override
  public StepResult<EksClusterOutput> revert(StepResult<EksClusterOutput> previous) {

    return null;
  }

  private StepResult<EksClusterOutput> buildStepResult() {

    String bucket = (String) metadata.getOutputs().get(EksClusterOutput.NAME);
    String region = (String) metadata.getOutputs().get(EksClusterOutput.REGION);
    String arn = (String) metadata.getOutputs().get(EksClusterOutput.ARN);
    Boolean currentPrevent = (Boolean) metadata.getOutputs().get(EksClusterOutput.PREVENT_DESTROY);

    return StepResult.<EksClusterOutput>builder()
        .stepName(this.getClass().getName())
        .put(EksClusterOutput.NAME, bucket)
        .put(EksClusterOutput.REGION, region)
        .put(EksClusterOutput.ARN, arn)
        .put(EksClusterOutput.PREVENT_DESTROY, config.preventDestroy() != null ? config.preventDestroy() : currentPrevent)
        .build();
  }
}