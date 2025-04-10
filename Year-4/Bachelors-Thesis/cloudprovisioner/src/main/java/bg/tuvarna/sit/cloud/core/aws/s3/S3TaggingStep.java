package bg.tuvarna.sit.cloud.core.aws.s3;

import bg.tuvarna.sit.cloud.core.aws.s3.config.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.provisioner.ProvisionAsync;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutBucketTaggingRequest;
import software.amazon.awssdk.services.s3.model.Tag;
import software.amazon.awssdk.services.s3.model.Tagging;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ProvisionAsync
public class S3TaggingStep implements S3ProvisionStep {

  @Override
  public void apply(S3Client s3Client, S3BucketConfig config) {
    if (config.getTags() != null && !config.getTags().isEmpty()) {
      List<Tag> tagList = config.getTags().entrySet().stream()
          .map(e -> Tag.builder().key(e.getKey()).value(e.getValue()).build())
          .collect(Collectors.toList());

      s3Client.putBucketTagging(PutBucketTaggingRequest.builder().bucket(config.getName())
          .tagging(Tagging.builder().tagSet(tagList).build()).build());
      log.info("Applied tags to bucket '{}'", config.getName());
    }
  }
}
