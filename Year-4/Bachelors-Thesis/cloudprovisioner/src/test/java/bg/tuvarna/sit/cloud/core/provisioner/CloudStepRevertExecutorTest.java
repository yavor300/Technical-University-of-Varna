package bg.tuvarna.sit.cloud.core.provisioner;

import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.provisioner.executor.CloudStepRevertExecutor;
import bg.tuvarna.sit.cloud.core.provisioner.executor.StepExecutionUtils;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;
import bg.tuvarna.sit.cloud.exception.RetryableCloudResourceStepException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CloudStepRevertExecutorTest {

  @Mock
  BaseCloudResourceConfiguration.RetryConfiguration retry;

  private CloudStepRevertExecutor<S3Output> executor;

  @BeforeEach
  void setUp() {
    when(retry.getMax()).thenReturn(3);
    when(retry.getBackoffMs()).thenReturn(10L);
    executor = new CloudStepRevertExecutor<>(retry);
  }

  @Test
  void execute_shouldRunSyncAndAsyncSteps_withRetry() throws Exception {

    AsyncTestStep asyncStep = new AsyncTestStep("async");
    SyncTestStep syncStep = new SyncTestStep("sync");

    List<CloudProvisionStep<S3Output>> steps = List.of(syncStep, asyncStep);
    List<StepResult<S3Output>> previous = List.of();

    try (MockedStatic<StepExecutionUtils> utils = mockStatic(StepExecutionUtils.class)) {
      Map<Boolean, List<CloudProvisionStep<S3Output>>> classified = Map.of(
          true, List.of(asyncStep),
          false, List.of(syncStep)
      );

      utils.when(() -> StepExecutionUtils.classifySteps(steps)).thenReturn(classified);

      utils.when(() -> StepExecutionUtils.executeSyncDescending(eq(List.of(syncStep)), any()))
          .thenAnswer(inv -> {
            Function<CloudProvisionStep<S3Output>, StepResult<S3Output>> fn = inv.getArgument(1);
            return List.of(fn.apply(syncStep));
          });

      utils.when(() -> StepExecutionUtils.executeAsync(eq(List.of(asyncStep)), any()))
          .thenAnswer(inv -> {
            Function<CloudProvisionStep<S3Output>, Callable<StepResult<S3Output>>> fn = inv.getArgument(1);
            return List.of(fn.apply(asyncStep).call());
          });

      List<StepResult<S3Output>> result = executor.execute(steps, previous);

      assertThat(result).hasSize(2);
      assertThat(result.get(0).getStepName()).isEqualTo("async");
      assertThat(result.get(1).getStepName()).isEqualTo("sync");
    }
  }

  @Test
  void executeWithRetry_shouldRetry_onRetryableException_usingConcreteStep() {
    RetryStep retryStep = new RetryStep(2, "retry-success");

    StepResult<S3Output> result = invokeExecuteWithRetry(retryStep, List.of());

    assertThat(result.getStepName()).isEqualTo("retry-success");
    assertThat(retryStep.getAttemptCount()).isEqualTo(3);
  }

  @Test
  void executeWithRetry_shouldThrow_onNonRetryableException() {
    CloudProvisionStep<S3Output> step = mock(CloudProvisionStep.class);
    when(step.revert(null)).thenThrow(new CloudResourceStepException("fail"));

    assertThatThrownBy(() -> invokeExecuteWithRetry(step, List.of()))
        .isInstanceOf(CloudResourceStepException.class);
  }

  @Test
  void executeWithRetry_shouldWrapUnexpectedException() {
    CloudProvisionStep<S3Output> step = mock(CloudProvisionStep.class);
    RuntimeException unexpected = new RuntimeException("boom");
    when(step.revert(null)).thenThrow(unexpected);

    assertThatThrownBy(() -> invokeExecuteWithRetry(step, List.of()))
        .isInstanceOf(CloudResourceStepException.class)
        .hasCause(unexpected);
  }

  @SuppressWarnings("unchecked")
  private StepResult<S3Output> invokeExecuteWithRetry(CloudProvisionStep<S3Output> step,
                                                      List<StepResult<S3Output>> previous) {
    try {
      Method method = CloudStepRevertExecutor.class
          .getDeclaredMethod("executeWithRetry", CloudProvisionStep.class, List.class);
      method.setAccessible(true);
      return (StepResult<S3Output>) method.invoke(executor, step, previous);
    } catch (InvocationTargetException e) {
      Throwable target = e.getTargetException();
      if (target instanceof RuntimeException re) throw re;
      throw new RuntimeException(target);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  // --------- Test classes to simulate real @ProvisionOrder/@ProvisionAsync ----------------

  @ProvisionAsync
  static class AsyncTestStep implements CloudProvisionStep<S3Output> {
    private final StepResult<S3Output> result;

    public AsyncTestStep(String name) {
      this.result = StepResult.<S3Output>builder().stepName(name).build();
    }

    @Override
    public StepResult<S3Output> apply() {
      return null;
    }

    @Override
    public StepResult<S3Output> generateDesiredState() {
      return null;
    }

    @Override
    public StepResult<S3Output> getCurrentState() {
      return null;
    }

    @Override
    public StepResult<S3Output> destroy(boolean enforcePreventDestroy) {
      return null;
    }

    @Override
    public StepResult<S3Output> revert(StepResult<S3Output> previous) {
      return result;
    }
  }

  @ProvisionOrder(2)
  static class SyncTestStep implements CloudProvisionStep<S3Output> {
    private final StepResult<S3Output> result;

    public SyncTestStep(String name) {
      this.result = StepResult.<S3Output>builder().stepName(name).build();
    }

    @Override
    public StepResult<S3Output> apply() {
      return null;
    }

    @Override
    public StepResult<S3Output> generateDesiredState() {
      return null;
    }

    @Override
    public StepResult<S3Output> getCurrentState() {
      return null;
    }

    @Override
    public StepResult<S3Output> destroy(boolean enforcePreventDestroy) {
      return null;
    }

    @Override
    public StepResult<S3Output> revert(StepResult<S3Output> previous) {
      return result;
    }
  }

  @ProvisionOrder(3)
  static class RetryStep implements CloudProvisionStep<S3Output> {
    private final AtomicInteger counter = new AtomicInteger(0);
    private final int failAttempts;
    private final StepResult<S3Output> finalResult;

    RetryStep(int failAttempts, String stepName) {
      this.failAttempts = failAttempts;
      this.finalResult = StepResult.<S3Output>builder().stepName(stepName).build();
    }

    @Override public StepResult<S3Output> apply() { return null; }
    @Override public StepResult<S3Output> generateDesiredState() { return null; }
    @Override public StepResult<S3Output> getCurrentState() { return null; }
    @Override public StepResult<S3Output> destroy(boolean enforcePreventDestroy) { return null; }

    @Override
    public StepResult<S3Output> revert(StepResult<S3Output> previous) {
      int attempt = counter.getAndIncrement();
      if (attempt < failAttempts) {
        throw new RetryableCloudResourceStepException("Fail attempt " + attempt, new RuntimeException("fail"));
      }
      return finalResult;
    }

    public int getAttemptCount() {
      return counter.get();
    }
  }

}
