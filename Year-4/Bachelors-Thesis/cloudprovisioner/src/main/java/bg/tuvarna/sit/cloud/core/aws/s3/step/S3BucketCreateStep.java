package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionOrder;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import bg.tuvarna.sit.cloud.exception.BucketCreationException;
import bg.tuvarna.sit.cloud.exception.BucketVerificationException;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;

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
    s3.head(bucketName, false);

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

    try {
      s3.head(config.getName(), true);
    } catch (BucketVerificationException e) {
      if (e.getCause() instanceof NoSuchBucketException) {
        return StepResult.<S3Output>builder()
            .stepName(S3BucketCreateStep.class.getName())
            .build();
      }
      return null;
    }

    // TODO Consider writing the return outside the try-catch block
    return StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .put(S3Output.NAME, config.getName())
        // TODO Check for region mismatch from the current config
        .put(S3Output.REGION, config.getRegion())
        .build();
  }
}
