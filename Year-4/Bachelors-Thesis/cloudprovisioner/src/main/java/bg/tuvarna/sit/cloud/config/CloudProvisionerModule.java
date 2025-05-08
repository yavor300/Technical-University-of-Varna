package bg.tuvarna.sit.cloud.config;

import bg.tuvarna.sit.cloud.core.aws.s3.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisioningContext;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import lombok.AllArgsConstructor;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;

@AllArgsConstructor
public class CloudProvisionerModule extends AbstractModule {

  private final S3ProvisioningContext s3Context;
  private final S3BucketConfig s3BucketConfig;

  @Override
  protected void configure() {

    bind(S3BucketConfig.class).toInstance(s3BucketConfig);
  }

  @Provides
  S3SafeClient s3SafeClient() {

    return new S3SafeClient(S3Client.builder()
        .credentialsProvider(StaticCredentialsProvider.create(s3Context.getCredentials()))
        .endpointOverride(s3Context.getEndpoint())
        .region(s3Context.getRegion())
        .forcePathStyle(true)
        .build());
  }
}
