package bg.tuvarna.sit.cloud.core.aws.s3;

import bg.tuvarna.sit.cloud.core.config.S3BucketConfig;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutBucketEncryptionRequest;
import software.amazon.awssdk.services.s3.model.ServerSideEncryption;
import software.amazon.awssdk.services.s3.model.ServerSideEncryptionByDefault;
import software.amazon.awssdk.services.s3.model.ServerSideEncryptionConfiguration;
import software.amazon.awssdk.services.s3.model.ServerSideEncryptionRule;

@Slf4j
@ProvisionOrder(4)
public class S3EncryptionStep implements S3ProvisionStep {

  @Override
  public void apply(S3Client s3Client, S3BucketConfig config) {
    if (config.getEncryption() != null && "SSE-S3".equalsIgnoreCase(config.getEncryption().getType())) {
      s3Client.putBucketEncryption(PutBucketEncryptionRequest.builder()
          .bucket(config.getName())
          .serverSideEncryptionConfiguration(ServerSideEncryptionConfiguration.builder()
              .rules(ServerSideEncryptionRule.builder()
                  .applyServerSideEncryptionByDefault(
                      ServerSideEncryptionByDefault.builder()
                          .sseAlgorithm(ServerSideEncryption.AES256)
                          .build())
                  .build())
              .build())
          .build());
      log.info("Enabled SSE-S3 encryption for bucket '{}'", config.getName());
    }
  }
}
