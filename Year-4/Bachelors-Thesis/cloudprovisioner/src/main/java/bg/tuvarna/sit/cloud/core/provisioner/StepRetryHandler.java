package bg.tuvarna.sit.cloud.core.provisioner;

import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;
import bg.tuvarna.sit.cloud.exception.RetryableCloudResourceStepException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StepRetryHandler {

  public static <K extends Enum<K>> void handleRetryableException(RetryableCloudResourceStepException retryable,
                                                                  CloudProvisionStep<K> step,
                                                                  int attempts,
                                                                  int maxRetries,
                                                                  long backoffMs) {

    if (attempts >= maxRetries) {
      String message = "Step '%s' failed after %s attempts".formatted(step.getClass().getSimpleName(), attempts);
      log.debug(message, retryable);
      retryable.getMessageDetails().add(message);
      throw retryable;
    }
    log.warn("Step '{}' failed on attempt {}. Retrying...", step.getClass().getName(), attempts);
    try {
      Thread.sleep(backoffMs * attempts);
    } catch (InterruptedException ex) {

      String stepName = step.getClass().getName();
      String message = "Retry sleep interrupted for step '%s' on attempt %d".formatted(stepName, attempts);
      log.debug(message, ex);

      throw new CloudResourceStepException(message, ex);
    }
  }

  public static <K extends Enum<K>> CloudResourceStepException handleUnexpectedException(Exception ex,
                                                                                         CloudProvisionStep<K> step,
                                                                                         int attempts) {

    String message = "Unexpected error in step '%s' on attempt %d".formatted(step.getClass().getSimpleName(),
        attempts);
    log.debug(message, ex);

    CloudResourceStepException unexpected = new CloudResourceStepException(message, ex);
    Throwable cause = ex.getCause();
    if (cause != null) {
      unexpected.getMessageDetails().add(cause.getMessage());
    }

    return unexpected;
  }
}
