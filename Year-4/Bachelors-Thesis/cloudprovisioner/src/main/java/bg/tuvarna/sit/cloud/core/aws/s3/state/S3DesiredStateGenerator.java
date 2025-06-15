package bg.tuvarna.sit.cloud.core.aws.s3.state;

import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.CloudStepExecutor;
import bg.tuvarna.sit.cloud.core.provisioner.DesiredStateGenerator;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudProvisioningTerminationException;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
public class S3DesiredStateGenerator implements DesiredStateGenerator<S3Output> {

  private final List<CloudProvisionStep<S3Output>> steps;
  private final CloudStepExecutor<S3Output> stepExecutor;
  private final StepResult<S3Output> metadata;

  @Inject
  public S3DesiredStateGenerator(@Named("s3Steps") List<CloudProvisionStep<S3Output>> steps,
                                 CloudStepExecutor<S3Output> stepExecutor, StepResult<S3Output> metadata) {
    this.steps = steps;
    this.stepExecutor = stepExecutor;
    this.metadata = metadata;
  }

  @Override
  public List<StepResult<S3Output>> generate() throws CloudProvisioningTerminationException {

    try {
      return stepExecutor.execute(steps, CloudProvisionStep::generateDesiredState);
    } catch (InterruptedException | ExecutionException e) {

      String bucket = (String) metadata.getOutputs().get(S3Output.NAME);
      String errorMsg = "Failed to generate desired S3 state for bucket '%s'".formatted(bucket);
      log.error(errorMsg, e);
      throw new CloudProvisioningTerminationException(errorMsg, e);
    }
  }
}
