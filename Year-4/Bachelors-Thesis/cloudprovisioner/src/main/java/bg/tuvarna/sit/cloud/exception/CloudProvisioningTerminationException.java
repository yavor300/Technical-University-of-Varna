package bg.tuvarna.sit.cloud.exception;

// TODO [High] Check to be Exception again
public class CloudProvisioningTerminationException extends RuntimeException {
  
  public CloudProvisioningTerminationException(String message, Throwable cause) {
    super(message, cause);
  }

  public CloudProvisioningTerminationException(String message) {
    super(message);
  }
}
