package bg.tuvarna.sit.cloud.core.aws.s3.state;

import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

class S3StateComparatorTest {

  private S3StateComparator comparator;

  @BeforeEach
  void setup() {
    comparator = new S3StateComparator();
  }

  @Test
  void diff_shouldReturnEmptyList_whenDesiredAndActualAreEqual() {
    StepResult<S3Output> step = StepResult.<S3Output>builder()
        .stepName("CreateBucket")
        .put(S3Output.NAME, "test-bucket")
        .build();

    List<StepResult<S3Output>> desired = List.of(step);
    List<StepResult<S3Output>> actual = List.of(step);

    List<StepResult<S3Output>> result = comparator.diff(desired, actual);

    assertThat(result).isEmpty();
  }

  @Test
  void diff_shouldReturnDesiredStep_whenOutputDiffers() {
    StepResult<S3Output> desiredStep = StepResult.<S3Output>builder()
        .stepName("CreateBucket")
        .put(S3Output.NAME, "test-bucket")
        .put(S3Output.VERSIONING_STATUS, true)
        .build();

    StepResult<S3Output> actualStep = StepResult.<S3Output>builder()
        .stepName("CreateBucket")
        .put(S3Output.NAME, "test-bucket")
        .put(S3Output.VERSIONING_STATUS, false)
        .build();

    List<StepResult<S3Output>> result = comparator.diff(List.of(desiredStep), List.of(actualStep));

    assertThat(result).containsExactly(desiredStep);
  }

  @Test
  void diff_shouldReturnEmptyList_whenActualDoesNotContainStep() {
    StepResult<S3Output> desiredStep = StepResult.<S3Output>builder()
        .stepName("PutBucketPolicy")
        .put(S3Output.NAME, "test-bucket")
        .build();

    List<StepResult<S3Output>> result = comparator.diff(List.of(desiredStep), List.of());

    assertThat(result).isEmpty();
  }

  @Test
  void diff_shouldIgnoreExtraStepsInActual() {
    StepResult<S3Output> desiredStep = StepResult.<S3Output>builder()
        .stepName("TagBucket")
        .put(S3Output.NAME, "test-bucket")
        .build();

    StepResult<S3Output> extraActualStep = StepResult.<S3Output>builder()
        .stepName("UnrelatedStep")
        .put(S3Output.NAME, "extra-bucket")
        .build();

    List<StepResult<S3Output>> result = comparator.diff(List.of(desiredStep), List.of(extraActualStep));

    assertThat(result).isEmpty();
  }
}
