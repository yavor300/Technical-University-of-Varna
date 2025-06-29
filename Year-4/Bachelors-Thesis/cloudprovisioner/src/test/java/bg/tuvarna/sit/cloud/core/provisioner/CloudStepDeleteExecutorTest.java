package bg.tuvarna.sit.cloud.core.provisioner;

import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.provisioner.executor.CloudStepDeleteExecutor;
import bg.tuvarna.sit.cloud.core.provisioner.executor.StepExecutionUtils;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class CloudStepDeleteExecutorTest {

  private CloudStepDeleteExecutor<S3Output> executor;

  @Mock
  private CloudProvisionStep<S3Output> asyncStep;

  @Mock
  private CloudProvisionStep<S3Output> syncStep;

  @Mock
  private StepResult<S3Output> asyncResult;

  @Mock
  private StepResult<S3Output> syncResult;

  @BeforeEach
  void setUp() {
    executor = new CloudStepDeleteExecutor<>();
  }

  @Test
  void execute_shouldReturnCombinedResults_fromAsyncAndSyncSteps() throws Exception {
    List<CloudProvisionStep<S3Output>> steps = List.of(asyncStep, syncStep);

    try (MockedStatic<StepExecutionUtils> utils = mockStatic(StepExecutionUtils.class)) {
      Map<Boolean, List<CloudProvisionStep<S3Output>>> classification = new HashMap<>();
      classification.put(true, List.of(asyncStep));
      classification.put(false, List.of(syncStep));

      utils.when(() -> StepExecutionUtils.classifySteps(steps)).thenReturn(classification);

      utils.when(() ->
          StepExecutionUtils.executeAsync(eq(List.of(asyncStep)), any())
      ).thenReturn(List.of(asyncResult));

      utils.when(() ->
          StepExecutionUtils.executeSyncDescending(eq(List.of(syncStep)), any())
      ).thenReturn(List.of(syncResult));

      List<StepResult<S3Output>> results = executor.execute(steps, true);

      assertThat(results).containsExactly(asyncResult, syncResult);
    }
  }

  @Test
  void execute_shouldReturnEmpty_whenNoSteps() throws Exception {
    try (MockedStatic<StepExecutionUtils> utils = mockStatic(StepExecutionUtils.class)) {
      Map<Boolean, List<CloudProvisionStep<S3Output>>> classification = new HashMap<>();
      classification.put(true, List.of());
      classification.put(false, List.of());

      utils.when(() -> StepExecutionUtils.classifySteps(List.of())).thenReturn(classification);
      utils.when(() -> StepExecutionUtils.executeAsync(anyList(), any())).thenReturn(List.of());
      utils.when(() -> StepExecutionUtils.executeSyncDescending(anyList(), any())).thenReturn(List.of());

      List<StepResult<S3Output>> results = executor.execute(List.of(), false);

      assertThat(results).isEmpty();
    }
  }
}
