package bg.tuvarna.sit.cloud.core.provisioner;

import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.exception.CloudProvisioningTerminationException;

import java.util.List;

public interface CloudResourceProvisioner<K extends Enum<K>> {

  CloudProvisionerSuccessfulResponse<K> provision(List<CloudProvisionStep<S3Output>> resources) throws
      CloudProvisioningTerminationException;
}
