package bg.tuvarna.sit.cloud.core.aws.s3;

import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.DesiredStateGenerator;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class S3DesiredStateGenerator implements DesiredStateGenerator<S3Client, S3BucketConfig, S3Output> {

  private final List<CloudProvisionStep<S3Client, S3BucketConfig, S3Output>> steps;

  public S3DesiredStateGenerator(List<CloudProvisionStep<S3Client, S3BucketConfig, S3Output>> steps) {
    this.steps = steps;
  }

  @Override
  public List<StepResult<S3Output>> generate(S3Client client, S3BucketConfig config) {

    List<StepResult<S3Output>> results = new ArrayList<>();

    List<Callable<StepResult<S3Output>>> tasks = steps.stream()
        .map(step -> (Callable<StepResult<S3Output>>) () -> step.generateDesiredState(config))
        .toList();

    try (ExecutorService executor = Executors.newFixedThreadPool(4)) {
      List<Future<StepResult<S3Output>>> futures = executor.invokeAll(tasks);
      for (Future<StepResult<S3Output>> future : futures) {
        StepResult<S3Output> result = future.get();
        if (!result.isVoid()) {
          results.add(result);
        }
      }

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Thread interrupted during desired state generation", e);
    } catch (ExecutionException e) {
      throw new RuntimeException("Error while generating desired state asynchronously", e);
    }

    return results;
  }
}
