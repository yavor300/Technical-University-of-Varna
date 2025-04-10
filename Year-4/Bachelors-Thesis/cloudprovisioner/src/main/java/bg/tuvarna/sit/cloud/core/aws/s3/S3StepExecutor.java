package bg.tuvarna.sit.cloud.core.aws.s3;

import bg.tuvarna.sit.cloud.core.provisioner.ProvisionAsync;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionOrder;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class S3StepExecutor {

  private final List<S3ProvisionStep> steps;

  public S3StepExecutor(List<S3ProvisionStep> steps) {
    this.steps = steps;
  }

  public void execute(S3Client client, S3BucketConfig config) throws InterruptedException {

    List<S3ProvisionStep> async = new ArrayList<>();
    List<S3ProvisionStep> sync = new ArrayList<>();

    for (S3ProvisionStep step : steps) {
      if (step.getClass().isAnnotationPresent(ProvisionAsync.class)) async.add(step);
      else sync.add(step);
    }

    sync.stream()
        .sorted(Comparator.comparingInt(s -> s.getClass().getAnnotation(ProvisionOrder.class).value()))
        .forEach(step -> step.apply(client, config));

    List<Callable<Void>> tasks = async.stream()
        .map(step -> (Callable<Void>) () -> { step.apply(client, config); return null; })
        .toList();

    try (ExecutorService executor = Executors.newFixedThreadPool(4)) {
      executor.invokeAll(tasks);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      log.error("S3 async provisioning interrupted", e);
      throw e;
    }
  }
}
