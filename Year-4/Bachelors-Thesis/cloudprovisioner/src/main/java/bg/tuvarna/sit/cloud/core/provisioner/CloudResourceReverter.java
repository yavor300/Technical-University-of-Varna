package bg.tuvarna.sit.cloud.core.provisioner;

import bg.tuvarna.sit.cloud.exception.CloudProvisioningTerminationException;

import java.util.List;

public interface CloudResourceReverter<K extends Enum<K>> {

  CloudProvisionerSuccessfulResponse<K> revert(List<CloudProvisionStep<K>> resources, List<StepResult<K>> previous)
      throws CloudProvisioningTerminationException;
}