package bg.tuvarna.sit.cloud.core.aws.eks.state;

import bg.tuvarna.sit.cloud.core.aws.eks.EksClusterOutput;
import bg.tuvarna.sit.cloud.core.provisioner.StateComparator;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EksClusterStateComparator implements StateComparator<EksClusterOutput> {

  @Override
  public List<StepResult<EksClusterOutput>> diff(List<StepResult<EksClusterOutput>> desired,
                                                 List<StepResult<EksClusterOutput>> actual) {

    Map<String, StepResult<EksClusterOutput>> actualMap = actual.stream()
        .collect(Collectors.toMap(StepResult::getStepName, Function.identity()));

    List<StepResult<EksClusterOutput>> changed = new ArrayList<>();

    for (StepResult<EksClusterOutput> desiredStep : desired) {
      StepResult<EksClusterOutput> actualStep = actualMap.get(desiredStep.getStepName());

      if (actualStep != null && !actualStep.equals(desiredStep)) {
        changed.add(desiredStep);
      }
    }

    return changed;
  }
}
