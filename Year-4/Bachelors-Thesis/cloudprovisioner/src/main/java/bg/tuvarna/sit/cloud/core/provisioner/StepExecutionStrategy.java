package bg.tuvarna.sit.cloud.core.provisioner;

@FunctionalInterface
public interface StepExecutionStrategy<K extends Enum<K>> {

  StepResult<K> execute(CloudProvisionStep<K> step);
}
