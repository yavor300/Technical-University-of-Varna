package bg.tuvarna.sit.cloud.core.provisioner;

import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;
import bg.tuvarna.sit.cloud.exception.RetryableCloudResourceStepException;

import bg.tuvarna.sit.cloud.utils.Slf4jLoggingUtil;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.exception.SdkClientException;

@Slf4j
public class CloudExceptionHandler {

  public static <K extends Enum<K>> void handleRetryableException(RetryableCloudResourceStepException retryable,
                                                                  CloudProvisionStep<K> step,
                                                                  int attempts,
                                                                  int maxRetries,
                                                                  long backoffMs) {

    if (attempts >= maxRetries) {
      String message = "Step '%s' failed after %s attempts".formatted(step.getClass().getSimpleName(), attempts);
      log.debug(Slf4jLoggingUtil.DEBUG_PREFIX + "{}", message, retryable);
      retryable.getMessageDetails().add(message);
      throw retryable;
    }
    log.warn("Step '{}' failed on attempt {}. Retrying...", step.getClass().getName(), attempts);
    try {
      Thread.sleep(backoffMs * attempts);
    } catch (InterruptedException ex) {

      String stepName = step.getClass().getName();
      String message = "Retry sleep interrupted for step '%s' on attempt %d".formatted(stepName, attempts);
      log.debug(Slf4jLoggingUtil.DEBUG_PREFIX + "{}", message, ex);

      throw new CloudResourceStepException(message, ex);
    }
  }

  public static <K extends Enum<K>> CloudResourceStepException handleUnexpectedException(Exception ex,
                                                                                         CloudProvisionStep<K> step,
                                                                                         int attempts) {

    String message = "Unexpected error in step '%s' on attempt %d".formatted(step.getClass().getSimpleName(),
        attempts);
    log.debug(Slf4jLoggingUtil.DEBUG_PREFIX + "{}", message, ex);

    CloudResourceStepException unexpected = new CloudResourceStepException(message, ex);
    Throwable cause = ex.getCause();
    if (cause != null) {
      unexpected.getMessageDetails().add(cause.getMessage());
    }

    return unexpected;
  }

  public static RetryableCloudResourceStepException handleSdkClientException(String resource, String message,
                                                                       SdkClientException e)
      throws RetryableCloudResourceStepException {

    log.debug(Slf4jLoggingUtil.DEBUG_PREFIX +
        "Client-side error for resource '{}'. Possible causes: network issues, invalid credentials, or local " +
        "misconfiguration.", resource, e);

    RetryableCloudResourceStepException exception = new RetryableCloudResourceStepException(message, e);
    exception.getMessageDetails().add(e.getMessage());
    Throwable cause = e.getCause();
    if (cause != null) {
      exception.getMessageDetails().add(cause.getMessage());
    }

    return exception;
  }

  public static CloudResourceStepException wrapToCloudResourceStepException(String message, String details, Exception e) {

    CloudResourceStepException ex = new CloudResourceStepException(message, e);
    ex.getMessageDetails().add(details);
    return ex;
  }

}
