package bg.tuvarna.sit.cloud.core.provisioner;

import bg.tuvarna.sit.cloud.exception.CloudProvisioningTerminationException;

import java.util.List;

public interface LiveStateGenerator<K extends Enum<K>> {

    List<StepResult<K>> generate() throws CloudProvisioningTerminationException;
}
