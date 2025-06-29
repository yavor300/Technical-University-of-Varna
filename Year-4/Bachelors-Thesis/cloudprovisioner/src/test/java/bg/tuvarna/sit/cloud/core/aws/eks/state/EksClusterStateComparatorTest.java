package bg.tuvarna.sit.cloud.core.aws.eks.state;

import bg.tuvarna.sit.cloud.core.aws.eks.EksClusterOutput;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.gradle.internal.impldep.org.testng.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EksClusterStateComparatorTest {

  private EksClusterStateComparator comparator;

  @BeforeEach
  void setUp() {
    comparator = new EksClusterStateComparator();
  }

  private StepResult<EksClusterOutput> step(String name, EksClusterOutput key, Object value) {
    return StepResult.<EksClusterOutput>builder()
        .stepName(name)
        .put(key, value)
        .build();
  }

  @Test
  void testDiff_NoDifference() {
    StepResult<EksClusterOutput> step = step("step-1", EksClusterOutput.NAME, "cluster-a");

    List<StepResult<EksClusterOutput>> desired = List.of(step);
    List<StepResult<EksClusterOutput>> actual = List.of(step);

    List<StepResult<EksClusterOutput>> diff = comparator.diff(desired, actual);

    assertTrue(diff.isEmpty(), "Expected no diff");
  }

  @Test
  void testDiff_ValueChanged() {
    StepResult<EksClusterOutput> desired = step("step-1", EksClusterOutput.NAME, "cluster-a");
    StepResult<EksClusterOutput> actual = step("step-1", EksClusterOutput.NAME, "cluster-b");

    List<StepResult<EksClusterOutput>> diff = comparator.diff(List.of(desired), List.of(actual));

    assertEquals(1, diff.size());
    assertEquals(desired, diff.getFirst());
  }

  @Test
  void testDiff_ExtraStepInActual_Ignored() {
    StepResult<EksClusterOutput> desired = step("step-1", EksClusterOutput.NAME, "cluster-a");
    StepResult<EksClusterOutput> actual1 = step("step-1", EksClusterOutput.NAME, "cluster-a");
    StepResult<EksClusterOutput> actual2 = step("step-2", EksClusterOutput.NAME, "other");

    List<StepResult<EksClusterOutput>> diff = comparator.diff(List.of(desired), List.of(actual1, actual2));

    assertTrue(diff.isEmpty());
  }

  @Test
  void testDiff_MissingActualStep_Ignored() {
    StepResult<EksClusterOutput> desired = step("step-1", EksClusterOutput.NAME, "cluster-a");

    List<StepResult<EksClusterOutput>> diff = comparator.diff(List.of(desired), List.of());

    assertTrue(diff.isEmpty(), "Missing steps in actual should be ignored by design");
  }

  @Test
  void testDiff_MultipleChanges() {
    StepResult<EksClusterOutput> d1 = step("step-1", EksClusterOutput.NAME, "a");
    StepResult<EksClusterOutput> d2 = step("step-2", EksClusterOutput.NAME, "b");

    StepResult<EksClusterOutput> a1 = step("step-1", EksClusterOutput.NAME, "a");
    StepResult<EksClusterOutput> a2 = step("step-2", EksClusterOutput.NAME, "c");

    List<StepResult<EksClusterOutput>> diff = comparator.diff(List.of(d1, d2), List.of(a1, a2));

    assertEquals(1, diff.size());
    assertEquals(d2, diff.getFirst());
  }

}
