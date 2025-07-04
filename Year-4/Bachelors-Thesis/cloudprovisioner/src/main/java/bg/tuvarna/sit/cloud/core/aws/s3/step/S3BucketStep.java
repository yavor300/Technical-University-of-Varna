package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.config.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.step.base.S3ProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.provisioner.MainResource;
import bg.tuvarna.sit.cloud.core.provisioner.PreventModification;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionOrder;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import lombok.extern.slf4j.Slf4j;

import software.amazon.awssdk.services.s3.model.NoSuchBucketException;

import java.time.Duration;

@Slf4j
@MainResource
@ProvisionOrder(1)
@PreventModification
@Singleton
public class S3BucketStep extends S3ProvisionStep {

  private final StepResult<S3Output> metadata;
  
  @Inject
  public S3BucketStep(S3SafeClient s3, S3BucketConfig config, StepResult<S3Output> metadata) {
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
    s3.waitUntilBucketExists(bucketName, Duration.ofMinutes(5));

    return StepResult.<S3Output>builder()
        .stepName(step)
        .put(S3Output.NAME, bucketName)
        .put(S3Output.REGION, config.getRegion().getValue())
        .build();
  }

  @Override
  public StepResult<S3Output> generateDesiredState() {

    String step = this.getClass().getName();

    if (config.isToDelete()) {
      return StepResult.<S3Output>builder()
          .stepName(step)
          .build();
    }

    return StepResult.<S3Output>builder()
        .stepName(step)
        .put(S3Output.NAME, config.getName())
        .put(S3Output.REGION, config.getRegion().getValue())
        .build();
  }

  @Override
  public StepResult<S3Output> getCurrentState() {

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);
    String region = (String) metadata.getOutputs().get(S3Output.REGION);
    StepResult.Builder<S3Output> builder = StepResult.<S3Output>builder().stepName(S3BucketStep.class.getName());

    try {
      s3.head(bucket);
      log.info("Successfully fetched details for bucket '{}'", bucket);
    } catch (CloudResourceStepException e) {
      if (e.getCause() instanceof NoSuchBucketException) {
        return builder.build();
      }
      throw e;
    }

    return builder.put(S3Output.NAME, bucket).put(S3Output.REGION, region).build();
  }

  @Override
  public StepResult<S3Output> destroy(boolean enforcePreventDestroy) {

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);
    Boolean preventDestroy = (Boolean) metadata.getOutputs().get(S3Output.PREVENT_DESTROY);

    if (enforcePreventDestroy && preventDestroy) {
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

    log.warn("Revert not supported for %s step: bucket properties are immutable and cannot be reverted once created."
        .formatted(this.getClass().getName()));

    return StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .put(S3Output.NAME, config.getName())
        .put(S3Output.REGION, config.getRegion().getValue())
        .build();
  }

}
