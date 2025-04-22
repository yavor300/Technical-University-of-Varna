package bg.tuvarna.sit.cloud.core.provisioner;

public interface CloudProvisionStep<TClient, TConfig, K extends Enum<K>> {

    StepResult<K> apply(TClient client, TConfig config);
}
