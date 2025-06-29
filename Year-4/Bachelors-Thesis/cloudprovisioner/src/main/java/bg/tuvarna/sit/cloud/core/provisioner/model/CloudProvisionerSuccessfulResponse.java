package bg.tuvarna.sit.cloud.core.provisioner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CloudProvisionerSuccessfulResponse<K extends Enum<K>> {

  private final CloudResourceType resourceType;
  private final String resourceName;
  private final String resourceId;

  @Setter
  private List<StepResult<K>> results;
}
