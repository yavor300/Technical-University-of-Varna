package bg.tuvarna.sit.cloud.core.provisioner.executor;

import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Slf4j
public class CloudStepDeleteExecutor<K extends Enum<K>> {

  public List<StepResult<K>> execute(List<CloudProvisionStep<K>> steps, boolean enforcePreventDestroy)
      throws InterruptedException, ExecutionException {

    List<StepResult<K>> results = new ArrayList<>();

    Map<Boolean, List<CloudProvisionStep<K>>> haveAsyncAnnotation = StepExecutionUtils.classifySteps(steps);
    List<CloudProvisionStep<K>> async = haveAsyncAnnotation.get(true);
    List<CloudProvisionStep<K>> sync = haveAsyncAnnotation.get(false);

    results.addAll(StepExecutionUtils.executeAsync(async, step -> () -> step.destroy(enforcePreventDestroy)));
    results.addAll(StepExecutionUtils.executeSyncDescending(sync, step -> step.destroy(enforcePreventDestroy)));

    return results;
  }
}
