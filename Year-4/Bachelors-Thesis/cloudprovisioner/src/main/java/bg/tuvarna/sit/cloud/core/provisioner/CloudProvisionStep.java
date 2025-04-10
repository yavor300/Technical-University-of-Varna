package bg.tuvarna.sit.cloud.core.provisioner;

public interface CloudProvisionStep<T, C> {
    void apply(T client, C config);
}
