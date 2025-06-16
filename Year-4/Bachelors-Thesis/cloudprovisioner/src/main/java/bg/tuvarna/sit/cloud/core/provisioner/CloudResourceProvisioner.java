package bg.tuvarna.sit.cloud.core.provisioner;

import bg.tuvarna.sit.cloud.exception.CloudProvisioningTerminationException;

import java.util.List;

public interface CloudResourceProvisioner<K extends Enum<K>> {

  CloudProvisionerSuccessfulResponse<K> provision(List<CloudProvisionStep<K>> resources)
      throws CloudProvisioningTerminationException;
}
