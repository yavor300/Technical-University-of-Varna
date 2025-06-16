package bg.tuvarna.sit.cloud.core.aws.s3.state;

import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.provisioner.LiveStateGenerator;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.CloudStepStrategyExecutor;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudProvisioningTerminationException;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
public class S3LiveStateGenerator implements LiveStateGenerator<S3Output> {

  private final CloudStepStrategyExecutor<S3Output> stepExecutor;
  private final List<CloudProvisionStep<S3Output>> provisionSteps;

  @Inject
  public S3LiveStateGenerator(CloudStepStrategyExecutor<S3Output> stepExecutor,
                              @Named("s3Steps") List<CloudProvisionStep<S3Output>> provisionSteps) {
    this.stepExecutor = stepExecutor;
    this.provisionSteps = provisionSteps;
  }

  @Override
  public List<StepResult<S3Output>> generate() throws CloudProvisioningTerminationException {

    try {
      return stepExecutor.execute(provisionSteps, CloudProvisionStep::getCurrentState);

    } catch (CloudResourceStepException e) {
      throw new CloudProvisioningTerminationException(e.getMessage(), e);

    } catch (InterruptedException | ExecutionException e) {

      Throwable cause = e.getCause();

      if (!(cause instanceof CloudResourceStepException)) {
        String msg = "Unexpected exception occurred during async execution: %s"
            .formatted(cause != null ? cause.getClass().getSimpleName() + " - " + cause.getMessage() : e.getMessage());
        log.debug(msg, e);
        throw new CloudProvisioningTerminationException(msg, e);
      }

      throw new CloudProvisioningTerminationException(cause.getMessage(), cause);
    }
  }
}
