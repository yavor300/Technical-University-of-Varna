package bg.tuvarna.sit.cloud.core.aws.s3.util;

import bg.tuvarna.sit.cloud.core.aws.s3.ProvisionedTags;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.step.S3TaggingStep;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;

import software.amazon.awssdk.services.s3.model.GetBucketTaggingResponse;
import software.amazon.awssdk.services.s3.model.Tag;

import java.util.Map;
import java.util.stream.Collectors;

public class S3TaggingResultBuilder {

  public static StepResult<S3Output> fromResponse(GetBucketTaggingResponse response) {

    Map<String, String> tags = response.tagSet().stream()
        .collect(Collectors.toMap(Tag::key, Tag::value));

    return StepResult.<S3Output>builder()
        .stepName(S3TaggingStep.class.getName())
        .put(S3Output.VALUE_NODE, new ProvisionedTags(tags))
        .build();
  }
}
