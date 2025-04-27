package bg.tuvarna.sit.cloud.core.provisioner;

import java.util.List;

public interface DesiredStateGenerator<TClient, TConfig, K extends Enum<K>> {

    List<StepResult<K>> generate(TClient client, TConfig config);
}
