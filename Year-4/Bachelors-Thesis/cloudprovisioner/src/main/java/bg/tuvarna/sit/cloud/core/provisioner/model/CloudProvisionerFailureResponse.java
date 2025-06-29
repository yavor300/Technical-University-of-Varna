package bg.tuvarna.sit.cloud.core.provisioner;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CloudProvisionerFailureResponse {

  private final String code;
  private final List<String> details;

  public CloudProvisionerFailureResponse(ErrorCode errorCode) {
    this.code = errorCode.getCode();
    this.details = new ArrayList<>();
  }
}
