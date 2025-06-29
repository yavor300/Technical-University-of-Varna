package bg.tuvarna.sit.cloud.core.aws.eks.step.base;

import bg.tuvarna.sit.cloud.core.aws.eks.EksClusterOutput;
import bg.tuvarna.sit.cloud.core.aws.eks.client.EksSafeClient;
import bg.tuvarna.sit.cloud.core.aws.eks.config.EksClusterConfig;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;

public abstract class EksClusterProvisionStep implements CloudProvisionStep<EksClusterOutput> {

  protected final EksSafeClient eks;
  protected final EksClusterConfig config;

  protected EksClusterProvisionStep(EksSafeClient eks, EksClusterConfig config) {
    this.eks = eks;
    this.config = config;
  }
}
