package bg.tuvarna.sit.cloud.exception;

public class CloudProvisioningTerminationException extends Exception {
  
  public CloudProvisioningTerminationException(String message, Throwable cause) {
    super(message, cause);
  }

  public CloudProvisioningTerminationException(String message) {
    super(message);
  }
}
