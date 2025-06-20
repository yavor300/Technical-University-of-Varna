package bg.tuvarna.sit.cloud.core.provisioner;

import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;
import bg.tuvarna.sit.cloud.exception.RetryableCloudResourceStepException;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Slf4j
public class CloudStepRevertExecutor<K extends Enum<K>> {

  private static final int MAX_RETRIES = 3;
  private static final long BACKOFF_MS = 1000;

  public List<StepResult<K>> execute(List<CloudProvisionStep<K>> steps, List<StepResult<K>> previousResults)
      throws InterruptedException, ExecutionException {

    List<StepResult<K>> results = new ArrayList<>();

    Map<Boolean, List<CloudProvisionStep<K>>> haveAsyncAnnotation = StepExecutionUtils.classifySteps(steps);
    List<CloudProvisionStep<K>> async = haveAsyncAnnotation.get(true);
    List<CloudProvisionStep<K>> sync = haveAsyncAnnotation.get(false);

    results.addAll(StepExecutionUtils.executeAsync(async, step -> () -> executeWithRetry(step, previousResults)));
    results.addAll(StepExecutionUtils.executeSyncDescending(sync, step -> executeWithRetry(step, previousResults)));

    return results;
  }

  private StepResult<K> executeWithRetry(CloudProvisionStep<K> step, List<StepResult<K>> previousResults) {

    // Find previous result for the step, if any
    StepResult<K> previous = previousResults.stream()
        .filter(r -> r.getStepName().equals(step.getClass().getName()))
        .findFirst()
        .orElse(null);

    int attempts = 0;
    while (true) {
      try {
        return step.revert(previous);
      } catch (RetryableCloudResourceStepException e) {
        attempts++;
        StepRetryHandler.handleRetryableException(e, step, attempts, MAX_RETRIES, BACKOFF_MS);
      } catch (CloudResourceStepException e) {
        // Propagate the non-retryable exception
        throw e;
      } catch (Exception ex) {
        throw StepRetryHandler.handleUnexpectedException(ex, step, attempts);
      }
    }
  }
}
