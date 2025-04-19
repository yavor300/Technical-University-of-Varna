package bg.tuvarna.sit.cloud.core.provisioner;

public interface CloudProvisionStep<T, C> {

    StepResult apply(T client, C config);
}
