package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.provisioner.MainResource;
import bg.tuvarna.sit.cloud.core.provisioner.PreventModification;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionOrder;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;

import jakarta.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import software.amazon.awssdk.services.s3.model.NoSuchBucketException;

@Slf4j
@ProvisionOrder(0)
@PreventModification
@MainResource
public class S3BucketCreateStep extends S3ProvisionStep {

  private final StepResult<S3Output> metadata;
  
  @Inject
  public S3BucketCreateStep(S3SafeClient s3, S3BucketConfig config, StepResult<S3Output> metadata) {
    super(s3, config);
    this.metadata = metadata;
  }

  @Override
  public StepResult<S3Output> apply() {

    String bucketName = config.getName();
    String step = this.getClass().getName();

    if (bucketName == null || bucketName.isEmpty()) {
      return StepResult.<S3Output>builder()
          .stepName(step)
          .build();
    }

    s3.create(bucketName);
    log.info("Successfully created bucket '{}'", bucketName);

    s3.head(bucketName);
    log.info("Successfully verified existence of bucket '{}'", bucketName);

    return StepResult.<S3Output>builder()
        .stepName(step)
        .put(S3Output.NAME, bucketName)
        .put(S3Output.REGION, config.getRegion())
        .build();
  }

  @Override
  public StepResult<S3Output> generateDesiredState() {

    String bucketName = config.isToDelete() ? null : config.getName();
    String step = this.getClass().getName();

    if (bucketName == null || bucketName.isEmpty()) {
      return StepResult.<S3Output>builder()
          .stepName(step)
          .build();
    }

    return StepResult.<S3Output>builder()
        .stepName(step)
        .put(S3Output.NAME, bucketName)
        .put(S3Output.REGION, config.getRegion())
        .build();
  }

  @Override
  public StepResult<S3Output> getCurrentState() {

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);
    StepResult.Builder<S3Output> builder = StepResult.<S3Output>builder().stepName(S3BucketCreateStep.class.getName());

    try {
      s3.head(bucket);
      log.info("Successfully fetched details for bucket '{}'", bucket);
    } catch (CloudResourceStepException e) {
      if (e.getCause() instanceof NoSuchBucketException) {
        return builder.build();
      }
      throw e;
    }

    return builder.put(S3Output.NAME, bucket).put(S3Output.REGION, config.getRegion()).build();
  }

  @Override
  public StepResult<S3Output> destroy() {

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);
    Boolean preventDestroy = (Boolean) metadata.getOutputs().get(S3Output.PREVENT_DESTROY);

    if (preventDestroy) {
      String msg = "Destruction of bucket '%s' is prevented by configuration.".formatted(bucket);
      log.warn(msg);
      throw new CloudResourceStepException(msg);
    }

    s3.delete(bucket);
    log.info("Deleted bucket '{}'", bucket);

    return StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .build();
  }

  @Override
  public StepResult<S3Output> revert(StepResult<S3Output> previous) throws CloudResourceStepException {

    // TODO [Implementation] Think for a revert when PreventModification
    if (this.getClass().isAnnotationPresent(PreventModification.class)) {
      String stepName = this.getClass().getSimpleName();
      String message = "Revert operation is not supported for step '%s' due to @PreventModification".formatted(stepName);
      throw new CloudResourceStepException(message);
    }

    return null;
  }

}
