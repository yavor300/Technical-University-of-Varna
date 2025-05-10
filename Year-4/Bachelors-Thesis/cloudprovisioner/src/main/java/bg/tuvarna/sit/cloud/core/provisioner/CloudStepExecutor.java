package bg.tuvarna.sit.cloud.core.provisioner;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@AllArgsConstructor
public class CloudStepExecutor<K extends Enum<K>> {

  private final List<CloudProvisionStep<K>> steps;

  public List<StepResult<K>> execute(StepExecutionStrategy<K> strategy) throws InterruptedException {

    List<StepResult<K>> results = new ArrayList<>();

    List<CloudProvisionStep<K>> async = new ArrayList<>();
    List<CloudProvisionStep<K>> sync = new ArrayList<>();

    for (CloudProvisionStep<K> step : steps) {
      if (step.getClass().isAnnotationPresent(ProvisionAsync.class)) {
        async.add(step);
      } else {
        sync.add(step);
      }
    }

    sync.stream()
        .sorted(Comparator.comparingInt(s -> s.getClass().getAnnotation(ProvisionOrder.class).value()))
        .forEach(step -> results.add(strategy.execute(step)));

    List<Callable<StepResult<K>>> tasks = async.stream()
        .map(step -> (Callable<StepResult<K>>) () -> strategy.execute(step))
        .toList();

    try (ExecutorService executor = Executors.newFixedThreadPool(4)) {
      List<Future<StepResult<K>>> futures = executor.invokeAll(tasks);
      for (Future<StepResult<K>> future : futures) {
        results.add(future.get());
      }
    } catch (ExecutionException e) {
      throw new RuntimeException("Async step failed", e);
    }

    return results;
  }
}
