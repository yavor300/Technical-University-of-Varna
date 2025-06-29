package bg.tuvarna.sit.cloud.core.provisioner;

import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudProvisioningTerminationException;

import java.util.List;

public interface DesiredStateGenerator<K extends Enum<K>> {

  List<StepResult<K>> generate() throws CloudProvisioningTerminationException;
}
