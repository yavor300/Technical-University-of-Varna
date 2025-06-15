package bg.tuvarna.sit.cloud.exception;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CloudResourceStepException extends RuntimeException {

  private final List<String> messageDetails;

  public CloudResourceStepException(String message, Throwable cause) {
    super(message, cause);
    this.messageDetails = new ArrayList<>();
  }

  public CloudResourceStepException(String message) {
    super(message);
    this.messageDetails = new ArrayList<>();
  }
}
