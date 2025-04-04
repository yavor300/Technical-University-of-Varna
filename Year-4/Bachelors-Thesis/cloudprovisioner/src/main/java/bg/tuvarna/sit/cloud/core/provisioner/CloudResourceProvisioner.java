package bg.tuvarna.sit.cloud.core.provisioner;

public interface CloudResourceProvisioner<T> {

  CloudProvisioningResponse provision(T config) throws Exception;
}
