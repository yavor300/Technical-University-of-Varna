package bg.tuvarna.sit.cloud.core.provisioner;

import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;

@FunctionalInterface
public interface StepExecutionStrategy<K extends Enum<K>> {

  StepResult<K> execute(CloudProvisionStep<K> step);
}
