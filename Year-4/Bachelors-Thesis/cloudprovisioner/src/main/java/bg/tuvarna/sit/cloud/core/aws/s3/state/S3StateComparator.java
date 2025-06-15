package bg.tuvarna.sit.cloud.core.aws.s3.state;

import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.provisioner.StateComparator;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class S3StateComparator implements StateComparator<S3Output> {

  @Override
  public List<StepResult<S3Output>> diff(
      List<StepResult<S3Output>> desired,
      List<StepResult<S3Output>> actual
  ) {
    Map<String, StepResult<S3Output>> actualMap = actual.stream()
        .collect(Collectors.toMap(StepResult::getStepName, Function.identity()));

    List<StepResult<S3Output>> changed = new ArrayList<>();

    for (StepResult<S3Output> desiredStep : desired) {
      StepResult<S3Output> actualStep = actualMap.get(desiredStep.getStepName());

      if (actualStep != null && !actualStep.equals(desiredStep)) {
        changed.add(desiredStep);
      }
    }

    return changed;
  }
}
