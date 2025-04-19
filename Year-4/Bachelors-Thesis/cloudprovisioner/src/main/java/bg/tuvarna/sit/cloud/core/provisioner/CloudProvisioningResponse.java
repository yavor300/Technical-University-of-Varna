package bg.tuvarna.sit.cloud.core.provisioner;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CloudProvisioningResponse {

  private final String resourceType;
  private final String resourceName;
  private final String resourceId;
}
