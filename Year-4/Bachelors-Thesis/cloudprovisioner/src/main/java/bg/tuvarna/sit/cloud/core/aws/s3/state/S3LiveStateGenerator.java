package bg.tuvarna.sit.cloud.core.aws.s3.state;

import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.provisioner.LiveStateGenerator;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.executor.CloudStepStrategyExecutor;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudProvisioningTerminationException;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;
import bg.tuvarna.sit.cloud.utils.NamedInjections;
import bg.tuvarna.sit.cloud.utils.Slf4jLoggingUtil;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Singleton
public class S3LiveStateGenerator implements LiveStateGenerator<S3Output> {

  private final CloudStepStrategyExecutor<S3Output> stepExecutor;
  private final List<CloudProvisionStep<S3Output>> provisionSteps;

  @Inject
  public S3LiveStateGenerator(CloudStepStrategyExecutor<S3Output> stepExecutor,
                              @Named(NamedInjections.S3_STEPS) List<CloudProvisionStep<S3Output>> provisionSteps) {
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
        log.debug(Slf4jLoggingUtil.DEBUG_PREFIX + "{}", msg, cause != null ? cause : e);
        // TODO Check to set e.getCause instead of e
        throw new CloudProvisioningTerminationException(msg, cause != null ? cause : e);
      }

      throw new CloudProvisioningTerminationException(cause.getMessage(), cause);
    }
  }
}
