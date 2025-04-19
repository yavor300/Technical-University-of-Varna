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
  public StepResult apply(S3Client s3Client, S3BucketConfig config) {

    StepResult.Builder result = StepResult.builder()
        .stepName(this.getClass().getName());

    if (config.getTags() != null && !config.getTags().isEmpty()) {
      List<Tag> tagList = config.getTags().entrySet().stream()
          .map(e -> Tag.builder().key(e.getKey()).value(e.getValue()).build())
          .collect(Collectors.toList());

      s3Client.putBucketTagging(PutBucketTaggingRequest.builder().bucket(config.getName())
          .tagging(Tagging.builder().tagSet(tagList).build()).build());

      log.info("Applied tags to bucket '{}'", config.getName());

      for (Map.Entry<String, String> entry : config.getTags().entrySet()) {
        result.put(entry.getKey(), entry.getValue());
      }
    }

    return result.build();
  }
}
