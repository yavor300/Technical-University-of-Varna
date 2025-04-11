package bg.tuvarna.sit.cloud.core.provisioner;

import lombok.Getter;

@Getter
public class CloudProvisioningResponse {

  private final String resourceType;
  private final String resourceName;
  private final String resourceId;

  public CloudProvisioningResponse(String resourceType, String resourceName, String resourceId) {
    this.resourceType = resourceType;
    this.resourceName = resourceName;
    this.resourceId = resourceId;
  }
}
