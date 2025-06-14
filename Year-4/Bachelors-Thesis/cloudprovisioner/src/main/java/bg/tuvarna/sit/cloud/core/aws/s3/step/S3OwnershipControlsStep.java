package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.S3OwnershipType;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.aws.s3.util.S3OwnershipControlsResultBuilder;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionOrder;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;

import jakarta.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import org.apache.hc.core5.http.HttpStatus;

import software.amazon.awssdk.services.s3.model.GetBucketOwnershipControlsResponse;
import software.amazon.awssdk.services.s3.model.ObjectOwnership;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Slf4j
@ProvisionOrder(6)
public class S3OwnershipControlsStep extends S3ProvisionStep {

  private final StepResult<S3Output> metadata;
  
  @Inject
  public S3OwnershipControlsStep(S3SafeClient s3, S3BucketConfig config, StepResult<S3Output> metadata) {
    super(s3, config);
    this.metadata = metadata;
  }

  @Override
  public StepResult<S3Output> apply() {

    S3OwnershipType ownership = config.getOwnershipControls();
    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);

    s3.putOwnershipControls(bucket, ownership.toSdkType());
    log.info("Successfully applied ownership controls '{}' to bucket '{}'", ownership.getValue(), bucket);

    GetBucketOwnershipControlsResponse response = s3.getOwnershipControls(bucket);
    log.info("Successfully verified ownership controls '{}' for bucket '{}'", ownership.getValue(), bucket);

    return S3OwnershipControlsResultBuilder.fromResponse(response);
  }

  @Override
  public StepResult<S3Output> generateDesiredState() {

    StepResult.Builder<S3Output> result = StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .put(S3Output.TYPE, S3OwnershipType.BUCKET_OWNER_ENFORCED.getValue());

    S3OwnershipType configOwnershipControls = config.getOwnershipControls();
    if (configOwnershipControls != null) {
      result.put(S3Output.TYPE, configOwnershipControls.getValue());
    }

    return result.build();
  }

  @Override
  public StepResult<S3Output> getCurrentState() {

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);

    try {
      GetBucketOwnershipControlsResponse response = s3.getOwnershipControls(bucket);
      log.info("Fetched ownership controls for bucket '{}'", bucket);
      return S3OwnershipControlsResultBuilder.fromResponse(response);

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

    s3.putOwnershipControls(bucket, ObjectOwnership.BUCKET_OWNER_ENFORCED);
    log.info("Set object ownership for bucket '{}' to the default BucketOwnerEnforced", bucket);

    GetBucketOwnershipControlsResponse response = s3.getOwnershipControls(bucket);
    return S3OwnershipControlsResultBuilder.fromResponse(response);
  }

  @Override
  public StepResult<S3Output> revert(StepResult<S3Output> previous) throws CloudResourceStepException {

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);
    String typeStr = (String) previous.getOutputs().get(S3Output.TYPE);
    S3OwnershipType type = S3OwnershipType.fromValue(typeStr);

    s3.putOwnershipControls(bucket, type.toSdkType());

    GetBucketOwnershipControlsResponse response = s3.getOwnershipControls(bucket);
    return S3OwnershipControlsResultBuilder.fromResponse(response);
  }

}
