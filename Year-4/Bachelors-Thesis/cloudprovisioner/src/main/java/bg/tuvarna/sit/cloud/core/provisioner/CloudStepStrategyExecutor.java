package bg.tuvarna.sit.cloud.core.provisioner;

import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;
import bg.tuvarna.sit.cloud.exception.RetryableCloudResourceStepException;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Slf4j
public class CloudStepExecutor<K extends Enum<K>> {

  // TODO [Implementation] Add as configuration
  private static final int MAX_RETRIES = 3;
  private static final long BACKOFF_MS = 1000;

  public List<StepResult<K>> execute(List<CloudProvisionStep<K>> steps, StepExecutionStrategy<K> strategy)
      throws InterruptedException, ExecutionException {

    List<StepResult<K>> results = new ArrayList<>();

    Map<Boolean, List<CloudProvisionStep<K>>> haveAsyncAnnotation = StepExecutionUtils.classifySteps(steps);
    List<CloudProvisionStep<K>> async = haveAsyncAnnotation.get(true);
    List<CloudProvisionStep<K>> sync = haveAsyncAnnotation.get(false);

    // TODO [Documentation] Document usage of strategy pattern
    results.addAll(StepExecutionUtils.executeSync(sync, step -> executeWithRetry(step, strategy)));
    results.addAll(StepExecutionUtils.executeAsync(async, step -> () -> executeWithRetry(step, strategy)));

    return results;
  }

  private StepResult<K> executeWithRetry(CloudProvisionStep<K> step, StepExecutionStrategy<K> strategy) {

    int attempts = 0;
    while (true) {
      try {
        return strategy.execute(step);
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
