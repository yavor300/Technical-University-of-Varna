package bg.tuvarna.sit.cloud.core.aws.eks;

import bg.tuvarna.sit.cloud.core.aws.eks.client.EksSafeClient;
import bg.tuvarna.sit.cloud.core.aws.eks.config.EksClusterConfig;
import bg.tuvarna.sit.cloud.core.aws.eks.step.EksClusterAddonStep;
import bg.tuvarna.sit.cloud.core.aws.eks.step.EksClusterNodeGroupStep;
import bg.tuvarna.sit.cloud.core.aws.eks.step.EksClusterPersistentMetadataStep;
import bg.tuvarna.sit.cloud.core.aws.eks.step.EksClusterStep;
import bg.tuvarna.sit.cloud.core.aws.eks.step.EksClusterTaggingStep;
import bg.tuvarna.sit.cloud.core.provisioner.CloudProvisionStep;
import bg.tuvarna.sit.cloud.core.provisioner.executor.CloudStepDeleteExecutor;
import bg.tuvarna.sit.cloud.core.provisioner.executor.CloudStepRevertExecutor;
import bg.tuvarna.sit.cloud.core.provisioner.executor.CloudStepStrategyExecutor;
import bg.tuvarna.sit.cloud.core.provisioner.model.StepResult;
import bg.tuvarna.sit.cloud.utils.NamedInjections;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;

import jakarta.inject.Named;
import jakarta.inject.Singleton;

import lombok.AllArgsConstructor;

import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.eks.EksClient;
import software.amazon.awssdk.services.eks.EksClientBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
public class EksClusterInjectionModule extends AbstractModule {

  private final EksClusterProvisioningContext context;
  private final EksClusterConfig config;
  private final StepResult<EksClusterOutput> metadata;

  @Override
  protected void configure() {

    bind(EksClusterConfig.class).toInstance(config);
    bind(new TypeLiteral<StepResult<EksClusterOutput>>() {}).toInstance(metadata);
  }

  // Do not use @Singleton because connection pool is getting closed on more than one place
  @Provides
  EksSafeClient eksSafeClient() {

    EksClientBuilder clientBuilder = EksClient.builder()
        .credentialsProvider(StaticCredentialsProvider.create(context.getCredentials()))
        .region(context.getRegion());

    if (context.getEndpoint() != null) {
      clientBuilder.endpointOverride(URI.create(context.getEndpoint()));
    }

    return new EksSafeClient(clientBuilder.build());
  }

  @Provides
  @Named(NamedInjections.EKS_STEPS)
  @Singleton
  List<CloudProvisionStep<EksClusterOutput>> steps(
      EksClusterPersistentMetadataStep metadataStep,
      EksClusterStep createStep,
      EksClusterTaggingStep taggingStep,
      EksClusterAddonStep addonStep,
      EksClusterNodeGroupStep nodeGroupStep
  ) {
    return List.of(
        metadataStep,
        createStep,
        taggingStep,
        addonStep,
        nodeGroupStep
    );
  }

  @Provides
  @Singleton
  CloudStepStrategyExecutor<EksClusterOutput> s3StepExecutor() {
    return new CloudStepStrategyExecutor<>(config.getRetry());
  }

  @Provides
  @Singleton
  CloudStepDeleteExecutor<EksClusterOutput> s3DeleteStepExecutor() {
    return new CloudStepDeleteExecutor<>();
  }

  @Provides
  @Singleton
  CloudStepRevertExecutor<EksClusterOutput> s3stepRevertExecutor() {
    return new CloudStepRevertExecutor<>(config.getRetry());
  }

}
