package bg.tuvarna.sit.cloud.core.provisioner;

public interface CloudProvisionStep<K extends Enum<K>> {

    StepResult<K> apply();

    StepResult<K> generateDesiredState();

    StepResult<K> getCurrentState();

    StepResult<K> destroy(boolean enforcePreventDestroy);

    StepResult<K> revert(StepResult<K> previous);
}
