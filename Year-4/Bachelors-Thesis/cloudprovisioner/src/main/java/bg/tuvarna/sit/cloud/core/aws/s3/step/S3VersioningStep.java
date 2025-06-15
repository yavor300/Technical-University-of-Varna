package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.config.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.aws.s3.util.S3VersioningResultBuilder;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionOrder;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;

import jakarta.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.apache.hc.core5.http.HttpStatus;

import software.amazon.awssdk.services.s3.model.BucketVersioningStatus;
import software.amazon.awssdk.services.s3.model.GetBucketVersioningResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import static software.amazon.awssdk.services.s3.model.BucketVersioningStatus.ENABLED;
import static software.amazon.awssdk.services.s3.model.BucketVersioningStatus.SUSPENDED;

@Slf4j
@ProvisionOrder(2)
public class S3VersioningStep extends S3ProvisionStep {

  private final StepResult<S3Output> metadata;

  @Inject
  public S3VersioningStep(S3SafeClient s3, S3BucketConfig config, StepResult<S3Output> metadata) {
    super(s3, config);
    this.metadata = metadata;
  }

  @Override
  public StepResult<S3Output> apply() {

    String versioning = config.getVersioning();

    BucketVersioningStatus status = ENABLED.toString().equalsIgnoreCase(versioning) ? ENABLED : SUSPENDED;
    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);

    s3.putVersioning(bucket, status);
    log.info("Successfully applied versioning status '{}' to bucket '{}'", status, bucket);

    GetBucketVersioningResponse response = s3.getVersioning(bucket);
    log.info("Successfully verified versioning status '{}' for bucket '{}'", response.status().toString(), bucket);

    return S3VersioningResultBuilder.fromResponse(response);
  }

  @Override
  public StepResult<S3Output> generateDesiredState() {

    return buildVersioningStepResult(config.getVersioning());
  }

  private StepResult<S3Output> buildVersioningStepResult(String versioning) {

    BucketVersioningStatus status = ENABLED.toString().equalsIgnoreCase(versioning) ? ENABLED : SUSPENDED;

    return StepResult.<S3Output>builder()
        .put(S3Output.VERSIONING_STATUS, status.toString())
        .stepName(this.getClass().getName())
        .build();
  }

  @Override
  public StepResult<S3Output> getCurrentState() {

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);

    try {
      GetBucketVersioningResponse response = s3.getVersioning(bucket);
      log.info("Retrieved versioning status for bucket '{}'", bucket);
      return S3VersioningResultBuilder.fromResponse(response);

    } catch (CloudResourceStepException e) {
      if (e.getCause() instanceof S3Exception s3Exception) {
        if (s3Exception.statusCode() == HttpStatus.SC_NOT_FOUND) {
          return StepResult.<S3Output>builder()
              .stepName(this.getClass().getName())
              .build();
        }
      }
      throw e;
    }
  }

  @Override
  public StepResult<S3Output> destroy(boolean enforcePreventDestroy) {

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);

    s3.putVersioning(bucket, SUSPENDED);
    log.info("Set versioning for bucket '{}' to default value SUSPENDED", bucket);

    GetBucketVersioningResponse response = s3.getVersioning(bucket);
    return S3VersioningResultBuilder.fromResponse(response);
  }

  @Override
  public StepResult<S3Output> revert(StepResult<S3Output> previous) throws CloudResourceStepException {

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);

    String previousStatus = (String) previous.getOutputs().get(S3Output.VERSIONING_STATUS);

    BucketVersioningStatus status = ENABLED.toString().equalsIgnoreCase(previousStatus) ? ENABLED : SUSPENDED;
    s3.putVersioning(bucket, status);

    GetBucketVersioningResponse response = s3.getVersioning(bucket);
    return S3VersioningResultBuilder.fromResponse(response);
  }

}
