package bg.tuvarna.sit.cloud.config;

import bg.tuvarna.sit.cloud.core.aws.s3.S3CloudBundleRunner;
import bg.tuvarna.sit.cloud.core.aws.s3.state.S3StateComparator;
import bg.tuvarna.sit.cloud.core.provisioner.CloudBundleRunner;
import bg.tuvarna.sit.cloud.core.provisioner.CloudStepDeleteExecutor;
import bg.tuvarna.sit.cloud.core.provisioner.CloudStepRevertExecutor;
import bg.tuvarna.sit.cloud.core.provisioner.CloudStepStrategyExecutor;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.util.Set;

@SuppressWarnings({"rawtypes", "unused"})
public class InitializerInjectionModule extends AbstractModule {

  @Override
  protected void configure() {}

  @Provides
  @Named("yamlMapper")
  @Singleton
  // TODO [Implementation] Add singleton to other classes well
  ObjectMapper provideYamlMapper() {

    YAMLFactory factory = new YAMLFactory();
    factory.disable(YAMLGenerator.Feature.SPLIT_LINES);
    factory.enable(YAMLGenerator.Feature.MINIMIZE_QUOTES);
    factory.enable(YAMLGenerator.Feature.LITERAL_BLOCK_STYLE);

    return new ObjectMapper(factory);
  }

  @Provides
  @Named("jsonMapper")
  @Singleton
  ObjectMapper provideJsonMapper() {
    return new ObjectMapper();
  }

  @Provides
  @Named("defaultPrettyPrinter")
  @Singleton
  ObjectWriter provideJsonMapperPrettyPrinter() {
    return new ObjectMapper().writerWithDefaultPrettyPrinter();
  }

  @Provides
  @Named("jsonMapperAcceptSingleValueAsArray")
  @Singleton
  ObjectMapper provideYamlMapperPrettyPrinter() {
    return new ObjectMapper()
        .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
  }

  @Provides
  @Singleton
  S3StateComparator s3StateComparator() { return new S3StateComparator(); }

  // TODO [Maybe] Might fail if more resource types are added
  @Provides
  @Singleton
  CloudStepStrategyExecutor stepStrategyExecutor() {
    return new CloudStepStrategyExecutor<>();
  }

  @Provides
  @Singleton
  CloudStepDeleteExecutor deleteStepExecutor() {
    return new CloudStepDeleteExecutor<>();
  }

  @Provides
  @Singleton
  CloudStepRevertExecutor stepRevertExecutor() {
    return new CloudStepRevertExecutor<>();
  }

  // TODO [Implementation] Use Set in other places as well
  @Provides
  @Named("bundles")
  Set<CloudBundleRunner> provideBundles(S3CloudBundleRunner s3) {
    return Set.of(s3);
  }
}
