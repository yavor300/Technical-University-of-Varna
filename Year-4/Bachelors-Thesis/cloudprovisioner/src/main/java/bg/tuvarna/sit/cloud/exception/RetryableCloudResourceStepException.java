package bg.tuvarna.sit.cloud.exception;

public class RetryableCloudResourceStepException extends CloudResourceStepException {

  public RetryableCloudResourceStepException(String message, Throwable cause) {
    super(message, cause);
  }
}
