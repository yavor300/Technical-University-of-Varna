package bg.tuvarna.sit.cloud.core.provisioner;

import bg.tuvarna.sit.cloud.config.RetryConfiguration;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;
import bg.tuvarna.sit.cloud.exception.RetryableCloudResourceStepException;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Slf4j
public class CloudStepStrategyExecutor<K extends Enum<K>> {

  private final RetryConfiguration retry;

  public CloudStepStrategyExecutor(RetryConfiguration retry) {
    this.retry = retry;
  }

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
        CloudExceptionHandler.handleRetryableException(e, step, attempts, retry.getMax(), retry.getBackoffMs());
      } catch (CloudResourceStepException e) {
        // Propagate the non-retryable exception
        throw e;
      } catch (Exception ex) {
        throw CloudExceptionHandler.handleUnexpectedException(ex, step, attempts);
      }
    }
  }
}
