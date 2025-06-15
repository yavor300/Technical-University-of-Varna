package bg.tuvarna.sit.cloud.core.provisioner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CloudProvisioningSuccessfulResponse<K extends Enum<K>> {

  private final CloudResourceType resourceType;
  private final String resourceName;
  private final String resourceId;

  @Setter
  private List<StepResult<K>> results;
}
