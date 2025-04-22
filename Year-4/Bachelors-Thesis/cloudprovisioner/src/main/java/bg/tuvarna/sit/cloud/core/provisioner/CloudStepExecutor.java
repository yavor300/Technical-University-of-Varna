package bg.tuvarna.sit.cloud.core.provisioner;

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
public class CloudStepExecutor<TClient, TConfig, K extends Enum<K>> {

  private final List<CloudProvisionStep<TClient, TConfig, K>> steps;

  public CloudStepExecutor(List<CloudProvisionStep<TClient, TConfig, K>> steps) {
    this.steps = steps;
  }

  public List<StepResult<K>> execute(TClient client, TConfig config) throws InterruptedException {

    List<StepResult<K>> results = new ArrayList<>();

    List<CloudProvisionStep<TClient, TConfig, K>> async = new ArrayList<>();
    List<CloudProvisionStep<TClient, TConfig, K>> sync = new ArrayList<>();

    for (CloudProvisionStep<TClient, TConfig, K> step : steps) {
      if (step.getClass().isAnnotationPresent(ProvisionAsync.class)) {
        async.add(step);
      } else {
        sync.add(step);
      }
    }

    sync.stream()
        .sorted(Comparator.comparingInt(s -> s.getClass().getAnnotation(ProvisionOrder.class).value()))
        .forEach(step -> results.add(step.apply(client, config)));

    List<Callable<StepResult<K>>> tasks = async.stream()
        .map(step -> (Callable<StepResult<K>>) () -> step.apply(client, config))
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
