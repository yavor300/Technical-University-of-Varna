package bg.tuvarna.sit.cloud.core.aws.eks.state;

import bg.tuvarna.sit.cloud.core.aws.eks.EksClusterOutput;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.executor.CloudStepStrategyExecutor;
import bg.tuvarna.sit.cloud.core.provisioner.DesiredStateGenerator;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudProvisioningTerminationException;
import bg.tuvarna.sit.cloud.utils.NamedInjections;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Singleton
public class EksClusterDesiredStateGenerator implements DesiredStateGenerator<EksClusterOutput> {

  private final List<CloudProvisionStep<EksClusterOutput>> steps;
  private final CloudStepStrategyExecutor<EksClusterOutput> stepExecutor;
  private final StepResult<EksClusterOutput> metadata;

  @Inject
  public EksClusterDesiredStateGenerator(@Named(NamedInjections.EKS_STEPS) List<CloudProvisionStep<EksClusterOutput>> steps,
                                         CloudStepStrategyExecutor<EksClusterOutput> stepExecutor, StepResult<EksClusterOutput> metadata) {
    this.steps = steps;
    this.stepExecutor = stepExecutor;
    this.metadata = metadata;
  }

  @Override
  public List<StepResult<EksClusterOutput>> generate() throws CloudProvisioningTerminationException {

    try {
      return stepExecutor.execute(steps, CloudProvisionStep::generateDesiredState);

    } catch (InterruptedException | ExecutionException e) {

      Throwable cause = e.getCause();

      String bucket = (String) metadata.getOutputs().get(EksClusterOutput.NAME);
      String errorMsg = "Failed to generate desired EKS state for cluster '%s'".formatted(bucket);
      log.error(errorMsg, cause != null ? cause : e);
      throw new CloudProvisioningTerminationException(errorMsg, cause != null ? cause : e);
    }
  }
}
