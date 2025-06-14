package bg.tuvarna.sit.cloud.config;

import bg.tuvarna.sit.cloud.core.aws.s3.S3BucketConfig;
import bg.tuvarna.sit.cloud.core.aws.s3.S3Output;
import bg.tuvarna.sit.cloud.core.aws.s3.S3ProvisioningContext;
import bg.tuvarna.sit.cloud.core.aws.s3.client.S3SafeClient;
import bg.tuvarna.sit.cloud.core.aws.s3.step.S3AclStep;
import bg.tuvarna.sit.cloud.core.aws.s3.step.S3BucketStep;
import bg.tuvarna.sit.cloud.core.aws.s3.step.S3EncryptionStep;
import bg.tuvarna.sit.cloud.core.aws.s3.step.S3OwnershipControlsStep;
import bg.tuvarna.sit.cloud.core.aws.s3.step.S3PersistentMetadataStep;
import bg.tuvarna.sit.cloud.core.aws.s3.step.S3PolicyStep;
import bg.tuvarna.sit.cloud.core.aws.s3.step.S3TaggingStep;
import bg.tuvarna.sit.cloud.core.aws.s3.step.S3VersioningStep;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.CloudStepDeletionExecutor;
import bg.tuvarna.sit.cloud.core.provisioner.CloudStepExecutor;
import bg.tuvarna.sit.cloud.core.provisioner.CloudStepRevertExecutor;
import bg.tuvarna.sit.cloud.core.provisioner.StepResult;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;

import jakarta.inject.Named;

import lombok.AllArgsConstructor;

import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
public class S3InjectionModule extends AbstractModule {

  private final S3ProvisioningContext s3Context;
  private final S3BucketConfig s3BucketConfig;
  private final StepResult<S3Output> s3metadata;

  @Override
  protected void configure() {

    bind(S3BucketConfig.class).toInstance(s3BucketConfig);
    bind(new TypeLiteral<StepResult<S3Output>>() {}).toInstance(s3metadata);
  }

  @Provides
  S3SafeClient s3SafeClient() {

    S3ClientBuilder clientBuilder = S3Client.builder()
        .credentialsProvider(StaticCredentialsProvider.create(s3Context.getCredentials()))
        .region(s3Context.getRegion())
        .forcePathStyle(true);

    if (s3Context.getEndpoint() != null) {
      clientBuilder.endpointOverride(URI.create(s3Context.getEndpoint()));
    }

    return new S3SafeClient(clientBuilder.build());
  }

  @Provides
  @Named("s3Steps")
  List<CloudProvisionStep<S3Output>> s3ProvisionSteps(
      S3PersistentMetadataStep metadataStep,
      S3BucketStep createStep,
      S3PolicyStep policyStep,
      S3VersioningStep versioningStep,
      S3TaggingStep taggingStep,
      S3EncryptionStep encryptionStep,
      S3AclStep aclStep,
      S3OwnershipControlsStep ownershipControlsStep
  ) {
    return List.of(
        metadataStep,
        createStep,
        policyStep,
        versioningStep,
        taggingStep,
        encryptionStep,
        aclStep,
        ownershipControlsStep
    );
  }

  @Provides
  CloudStepExecutor<S3Output> s3StepExecutor() {
    return new CloudStepExecutor<>();
  }

  @Provides
  CloudStepDeletionExecutor<S3Output> s3DeleteStepExecutor() {
    return new CloudStepDeletionExecutor<>();
  }

  @Provides
  CloudStepRevertExecutor<S3Output> s3stepRevertExecutor() {
    return new CloudStepRevertExecutor<>();
  }

}
