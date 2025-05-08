package bg.tuvarna.sit.cloud.core.provisioner;

import bg.tuvarna.sit.cloud.exception.CloudProvisioningStepException;

public interface CloudProvisionStep<TClient, TConfig, K extends Enum<K>> {

    StepResult<K> apply(TClient client, TConfig config) throws CloudProvisioningStepException;

    StepResult<K> generateDesiredState(TConfig config);

    StepResult<K> getCurrentState(TClient client, TConfig config);
}
