package bg.tuvarna.sit.cloud.core.aws.s3.state;

import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
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
public class S3DesiredStateGenerator implements DesiredStateGenerator<S3Output> {

  private final List<CloudProvisionStep<S3Output>> steps;
  private final CloudStepStrategyExecutor<S3Output> stepExecutor;
  private final StepResult<S3Output> metadata;

  @Inject
  public S3DesiredStateGenerator(@Named(NamedInjections.S3_STEPS) List<CloudProvisionStep<S3Output>> steps,
                                 CloudStepStrategyExecutor<S3Output> stepExecutor, StepResult<S3Output> metadata) {
    this.steps = steps;
    this.stepExecutor = stepExecutor;
    this.metadata = metadata;
  }

  @Override
  public List<StepResult<S3Output>> generate() throws CloudProvisioningTerminationException {

    try {
      return stepExecutor.execute(steps, CloudProvisionStep::generateDesiredState);
    } catch (InterruptedException | ExecutionException e) {

      Throwable cause = e.getCause();

      String bucket = (String) metadata.getOutputs().get(S3Output.NAME);
      String errorMsg = "Failed to generate desired S3 state for bucket '%s'".formatted(bucket);
      log.error(errorMsg, cause != null ? cause : e);
      throw new CloudProvisioningTerminationException(errorMsg, cause != null ? cause : e);
    }
  }
}
