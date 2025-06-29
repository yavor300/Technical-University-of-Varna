package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.common.model.ProvisionedTags;
import bg.tuvarna.sit.cloud.core.aws.s3.config.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.step.base.S3ProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.aws.s3.util.S3TaggingResultBuilder;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionOrder;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.exception.CloudResourceStepException;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import lombok.extern.slf4j.Slf4j;

import org.apache.hc.core5.http.HttpStatus;

import software.amazon.awssdk.services.s3.model.GetBucketTaggingResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.Tag;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// TODO [Implementation] Validate if Provision annotation is present. Maybe common validator module ?
@Slf4j
@ProvisionOrder(4)
@Singleton
public class S3TaggingStep extends S3ProvisionStep {

  private final StepResult<S3Output> metadata;

  @Inject
  public S3TaggingStep(S3SafeClient s3, S3BucketConfig config, StepResult<S3Output> metadata) {
    super(s3, config);
    this.metadata = metadata;
  }

  @Override
  public StepResult<S3Output> apply() {

    Map<String, String> tags = config.getTags();

    if (tags.isEmpty()) {
      return buildTaggingStepResult(Collections.emptyMap());
    }

    List<Tag> tagList = tags.entrySet().stream()
        .map(e -> Tag.builder().key(e.getKey()).value(e.getValue()).build())
        .collect(Collectors.toList());

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);
    s3.putTags(bucket, tagList);
    log.info("Successfully applied tags '{}' to bucket '{}'", tagList, bucket);

    GetBucketTaggingResponse response = s3.getTags(bucket);
    log.info("Successfully verified tags '{}' for bucket '{}'", tagList, bucket);

    return S3TaggingResultBuilder.fromResponse(response);
  }

  @Override
  public StepResult<S3Output> generateDesiredState() {

    return buildTaggingStepResult(config.getTags());
  }

  @Override
  public StepResult<S3Output> getCurrentState() {

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);

    try {
      GetBucketTaggingResponse response = s3.getTags(bucket);
      log.info("Fetched tags for bucket '{}'", bucket);
      return S3TaggingResultBuilder.fromResponse(response);

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
    s3.deleteTags(bucket);
    log.info("Deleted all tags from bucket '{}'", bucket);

    return StepResult.<S3Output>builder()
        .stepName(this.getClass().getName())
        .build();
  }

  @Override
  public StepResult<S3Output> revert(StepResult<S3Output> step) throws CloudResourceStepException {

    String bucket = (String) metadata.getOutputs().get(S3Output.NAME);
    ProvisionedTags revert = (ProvisionedTags) step.getOutputs().get(S3Output.VALUE_NODE);

    if (revert == null || revert.getTags().isEmpty()) {
      s3.deleteTags(bucket);
      log.info("Reverted all newly created tags for bucket '{}'", bucket);

      return StepResult.<S3Output>builder()
          .stepName(this.getClass().getName())
          .build();
    }

    List<Tag> tagList = revert.getTags().entrySet().stream()
        .map(e -> Tag.builder().key(e.getKey()).value(e.getValue()).build())
        .collect(Collectors.toList());

    s3.putTags(bucket, tagList);

    GetBucketTaggingResponse response = s3.getTags(bucket);
    return S3TaggingResultBuilder.fromResponse(response);
  }

  private StepResult<S3Output> buildTaggingStepResult(Map<String, String> tags) {

    StepResult.Builder<S3Output> result = StepResult.<S3Output>builder()
        .stepName(this.getClass().getName());

    if (tags.isEmpty()) {
      return result.build();
    }

    return result.put(S3Output.VALUE_NODE, new ProvisionedTags(tags)).build();
  }

}
