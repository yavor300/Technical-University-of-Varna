package bg.tuvarna.sit.cloud.core.provisioner;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StepExecutionUtils {

  public static <K extends Enum<K>> Map<Boolean, List<CloudProvisionStep<K>>> classifySteps(
      List<CloudProvisionStep<K>> steps) {
    return steps.stream().collect(Collectors.partitioningBy(
        step -> step.getClass().isAnnotationPresent(ProvisionAsync.class)));
  }

  public static <K extends Enum<K>> List<StepResult<K>> executeAsync(
      List<CloudProvisionStep<K>> steps,
      Function<CloudProvisionStep<K>, Callable<StepResult<K>>> toCallable
  ) throws InterruptedException, ExecutionException {

    List<Callable<StepResult<K>>> tasks = steps.stream().map(toCallable).toList();

    try (ExecutorService executor = Executors.newFixedThreadPool(4)) {
      List<Future<StepResult<K>>> futures = executor.invokeAll(tasks);
      List<StepResult<K>> results = new ArrayList<>();
      for (Future<StepResult<K>> future : futures) {
        results.add(future.get());
      }
      return results;
    }
  }

  public static <K extends Enum<K>> List<StepResult<K>> executeSync(
      List<CloudProvisionStep<K>> steps,
      Function<CloudProvisionStep<K>, StepResult<K>> executor
  ) {

    return steps.stream()
        .sorted(Comparator.comparingInt(s -> s.getClass().getAnnotation(ProvisionOrder.class).value()))
        .map(executor)
        .toList();
  }

  public static <K extends Enum<K>> List<StepResult<K>> executeSyncDescending(
      List<CloudProvisionStep<K>> steps,
      Function<CloudProvisionStep<K>, StepResult<K>> executor
  ) {

    return steps.stream()
        .sorted((s1, s2) -> {
          int order1 = s1.getClass().getAnnotation(ProvisionOrder.class).value();
          int order2 = s2.getClass().getAnnotation(ProvisionOrder.class).value();
          return Integer.compare(order2, order1);
        })
        .map(executor)
        .toList();
  }
}
