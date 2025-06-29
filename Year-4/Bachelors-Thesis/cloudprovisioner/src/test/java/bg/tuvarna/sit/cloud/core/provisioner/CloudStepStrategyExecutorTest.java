package bg.tuvarna.sit.cloud.core.provisioner;

import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.provisioner.executor.CloudStepStrategyExecutor;
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
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CloudStepStrategyExecutorTest {

  @Mock
  BaseCloudResourceConfiguration.RetryConfiguration retry;
  @Mock CloudProvisionStep<S3Output> asyncStep;
  @Mock CloudProvisionStep<S3Output> syncStep;
  @Mock StepExecutionStrategy<S3Output> strategy;

  private CloudStepStrategyExecutor<S3Output> executor;

  @BeforeEach
  void setUp() {
    when(retry.getMax()).thenReturn(3);
    when(retry.getBackoffMs()).thenReturn(10L);
    executor = new CloudStepStrategyExecutor<>(retry);
  }

  @Test
  void execute_shouldRunSyncAndAsyncSteps_withStrategy() throws Exception {

    List<CloudProvisionStep<S3Output>> steps = List.of(syncStep, asyncStep);
    StepResult<S3Output> syncResult = StepResult.<S3Output>builder().stepName("sync").build();
    StepResult<S3Output> asyncResult = StepResult.<S3Output>builder().stepName("async").build();

    try (MockedStatic<StepExecutionUtils> utils = mockStatic(StepExecutionUtils.class)) {
      Map<Boolean, List<CloudProvisionStep<S3Output>>> classified = Map.of(
          true, List.of(asyncStep),
          false, List.of(syncStep)
      );
      utils.when(() -> StepExecutionUtils.classifySteps(steps)).thenReturn(classified);

      utils.when(() -> StepExecutionUtils.executeSync(eq(List.of(syncStep)), any()))
          .thenAnswer(inv -> {
            Function<CloudProvisionStep<S3Output>, StepResult<S3Output>> fn = inv.getArgument(1);
            return List.of(fn.apply(syncStep));
          });

      utils.when(() -> StepExecutionUtils.executeAsync(eq(List.of(asyncStep)), any()))
          .thenAnswer(inv -> {
            Function<CloudProvisionStep<S3Output>, Callable<StepResult<S3Output>>> fn = inv.getArgument(1);
            return List.of(fn.apply(asyncStep).call());
          });

      when(strategy.execute(syncStep)).thenReturn(syncResult);
      when(strategy.execute(asyncStep)).thenReturn(asyncResult);

      List<StepResult<S3Output>> result = executor.execute(steps, strategy);

      assertThat(result).containsExactly(syncResult, asyncResult);
    }
  }

  @Test
  void executeWithRetry_shouldRetry_onRetryableException() throws Exception {
    CloudProvisionStep<S3Output> step = mock(CloudProvisionStep.class);
    StepExecutionStrategy<S3Output> retryStrategy = mock(StepExecutionStrategy.class);
    StepResult<S3Output> finalResult = StepResult.<S3Output>builder().stepName("retry").build();

    when(retryStrategy.execute(step))
        .thenThrow(new RetryableCloudResourceStepException("try again", new RuntimeException()))
        .thenThrow(new RetryableCloudResourceStepException("try again", new RuntimeException()))
        .thenReturn(finalResult);

    StepResult<S3Output> result = invokeExecuteWithRetry(step, retryStrategy);

    assertThat(result).isEqualTo(finalResult);
    verify(retryStrategy, times(3)).execute(step);
  }

  @Test
  void executeWithRetry_shouldThrow_onNonRetryableException() {
    CloudProvisionStep<S3Output> step = mock(CloudProvisionStep.class);
    StepExecutionStrategy<S3Output> failStrategy = mock(StepExecutionStrategy.class);

    when(failStrategy.execute(step))
        .thenThrow(new CloudResourceStepException("fail"));

    assertThatThrownBy(() -> invokeExecuteWithRetry(step, failStrategy))
        .isInstanceOf(CloudResourceStepException.class);
  }

  @Test
  void executeWithRetry_shouldThrowWrapped_onUnexpectedException() {
    CloudProvisionStep<S3Output> step = mock(CloudProvisionStep.class);
    StepExecutionStrategy<S3Output> badStrategy = mock(StepExecutionStrategy.class);

    RuntimeException unexpected = new RuntimeException("unexpected");

    when(badStrategy.execute(step)).thenThrow(unexpected);

    assertThatThrownBy(() -> invokeExecuteWithRetry(step, badStrategy))
        .isInstanceOf(CloudResourceStepException.class)
        .hasCause(unexpected);
  }

  private StepResult<S3Output> invokeExecuteWithRetry(CloudProvisionStep<S3Output> step,
                                                      StepExecutionStrategy<S3Output> strategy) {
    try {
      Method method = CloudStepStrategyExecutor.class
          .getDeclaredMethod("executeWithRetry", CloudProvisionStep.class, StepExecutionStrategy.class);
      method.setAccessible(true);
      return (StepResult<S3Output>) method.invoke(executor, step, strategy);
    } catch (InvocationTargetException e) {
      Throwable target = e.getTargetException();
      if (target instanceof RuntimeException re) throw re;
      throw new RuntimeException(target);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
