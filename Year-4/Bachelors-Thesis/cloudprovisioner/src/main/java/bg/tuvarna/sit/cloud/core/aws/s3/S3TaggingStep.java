package bg.tuvarna.sit.cloud.core.aws.s3;

import bg.tuvarna.sit.cloud.core.provisioner.ProvisionAsync;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutBucketTaggingRequest;
import software.amazon.awssdk.services.s3.model.Tag;
import software.amazon.awssdk.services.s3.model.Tagging;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ProvisionAsync
public class S3TaggingStep implements S3ProvisionStep {

  @Override
  public StepResult<S3Output> apply(S3Client s3Client, S3BucketConfig config) {

    Map<String, String> tags = config.getTags();

    if (tags != null && !tags.isEmpty()) {
      List<Tag> tagList = tags.entrySet().stream()
          .map(e -> Tag.builder().key(e.getKey()).value(e.getValue()).build())
          .collect(Collectors.toList());

      String bucketName = config.getName();
      s3Client.putBucketTagging(PutBucketTaggingRequest.builder().bucket(bucketName)
          .tagging(Tagging.builder().tagSet(tagList).build()).build());

      log.info("Applied tags to bucket '{}'", bucketName);
    }

    return buildTaggingStepResult(tags);
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
