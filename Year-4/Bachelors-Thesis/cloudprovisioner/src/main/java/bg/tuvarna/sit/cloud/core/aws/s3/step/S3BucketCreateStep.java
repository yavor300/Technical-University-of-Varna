package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionOrder;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import bg.tuvarna.sit.cloud.exception.BucketCreationException;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ProvisionOrder(0)
public class S3BucketCreateStep extends S3ProvisionStep {
  
  @Inject
  public S3BucketCreateStep(S3SafeClient s3, S3BucketConfig config) {
    super(s3, config);
  }

  @Override
  public StepResult<S3Output> apply() throws BucketCreationException {

    String bucketName = config.getName();

    s3.create(bucketName);
    s3.head(bucketName);

    return StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .put(S3Output.NAME, bucketName)
        .put(S3Output.REGION, config.getRegion())
        .build();
  }

  @Override
  public StepResult<S3Output> generateDesiredState() {

    return StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .put(S3Output.NAME, config.getName())
        .put(S3Output.REGION, config.getRegion())
        .build();
  }

  @Override
  public StepResult<S3Output> getCurrentState() {

    return StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .put(S3Output.NAME, config.getName())
        .put(S3Output.REGION, config.getRegion())
        .build();
  }
}
