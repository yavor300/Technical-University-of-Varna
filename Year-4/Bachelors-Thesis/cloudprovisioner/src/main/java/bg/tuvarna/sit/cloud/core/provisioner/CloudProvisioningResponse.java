package bg.tuvarna.sit.cloud.core.provisioner;

import lombok.Getter;

@Getter
public class CloudProvisioningResponse {

  private final String resourceType;
  private final String resourceName;
  private final String arn;

  public CloudProvisioningResponse(String resourceType, String resourceName, String arn) {
    this.resourceType = resourceType;
    this.resourceName = resourceName;
    this.arn = arn;
  }
}
