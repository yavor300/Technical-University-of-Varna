package bg.tuvarna.sit.cloud.core.aws.s3.step;

import bg.tuvarna.sit.cloud.core.aws.s3.ProvisionedTags;
import bg.tuvarna.sit.cloud.core.aws.s3.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionAsync;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.model.Tag;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ProvisionAsync
public class S3TaggingStep implements S3ProvisionStep {

  @Override
  public StepResult<S3Output> apply(S3SafeClient s3Client, S3BucketConfig config) {

    Map<String, String> tags = config.getTags();

    if (tags == null || tags.isEmpty()) {
      return buildTaggingStepResult(null);
    }

    List<Tag> tagList = tags.entrySet().stream()
        .map(e -> Tag.builder().key(e.getKey()).value(e.getValue()).build())
        .collect(Collectors.toList());

    String bucketName = config.getName();
    s3Client.putBucketTagging(bucketName, tagList);

    Map<String, String> actualTags = s3Client.getBucketTagging(bucketName).tagSet().stream()
        .collect(Collectors.toMap(Tag::key, Tag::value));

    return buildTaggingStepResult(actualTags);
  }

  @Override
  public StepResult<S3Output> generateDesiredState(S3BucketConfig config) {

    return buildTaggingStepResult(config.getTags());
  }

  private StepResult<S3Output> buildTaggingStepResult(Map<String, String> tags) {

    StepResult.Builder<S3Output> result = StepResult.<S3Output>builder()
        .stepName(this.getClass().getName());

    if (tags != null && !tags.isEmpty()) {
      result.put(S3Output.VALUE_NODE, new ProvisionedTags(tags));
    }

    return result.build();
  }
}
