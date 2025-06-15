package bg.tuvarna.sit.cloud.core.provisioner;

import java.util.List;

public interface StateComparator<K extends Enum<K>> {

  List<StepResult<K>> diff(List<StepResult<K>> desired, List<StepResult<K>> actual);
}
