package bg.tuvarna.sit.cloud.core.provisioner;

import bg.tuvarna.sit.cloud.exception.CloudProvisioningTerminationException;

import java.util.List;

public interface CloudResourceDestroyer<K extends Enum<K>> {

  CloudProvisionerSuccessfulResponse<K> destroy(List<CloudProvisionStep<K>> steps, boolean enforcePreventDestroy)
      throws CloudProvisioningTerminationException;
}
