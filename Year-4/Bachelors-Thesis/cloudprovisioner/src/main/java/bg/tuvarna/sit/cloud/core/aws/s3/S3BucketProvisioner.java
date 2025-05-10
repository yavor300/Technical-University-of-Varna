package bg.tuvarna.sit.cloud.core.aws.s3;

import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisioningResponse;
import bg.tuvarna.sit.cloud.core.provisioner.CloudResourceProvisioner;
import bg.tuvarna.sit.cloud.core.provisioner.CloudStepExecutor;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import bg.tuvarna.sit.cloud.core.provisioner.StepResultStateWriter;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class S3BucketProvisioner implements CloudResourceProvisioner {

  private final S3BucketConfig config;
  private final S3SafeClient s3;
  private final CloudStepExecutor<S3Output> stepExecutor;

  @Inject
  public S3BucketProvisioner(S3BucketConfig config, S3SafeClient s3, CloudStepExecutor<S3Output> stepExecutor) {
    this.config = config;
    this.s3 = s3;
    this.stepExecutor = stepExecutor;
  }

  @Override
  public CloudProvisioningResponse provision() throws Exception {

    long startTime = System.nanoTime();
    String bucketName = config.getName();

    try (s3) {

      List<StepResult<S3Output>> results = stepExecutor.execute(CloudProvisionStep::apply);

      long endTime = System.nanoTime();
      long durationMs = (endTime - startTime) / 1_000_000;
      log.info("Provisioning completed for bucket '{}' in {} ms", bucketName, durationMs);

      StepResultStateWriter<S3Output> writer = new StepResultStateWriter<>(".cloudprovisioner/state.json");
      writer.write(results);

      String arn = String.format("arn:aws:s3:::%s", bucketName);

      return new CloudProvisioningResponse("S3", bucketName, arn);

    } catch (Exception e) {
      log.error("Failed to provision S3 bucket '{}'", bucketName, e);
      throw e;
    }
  }
}
