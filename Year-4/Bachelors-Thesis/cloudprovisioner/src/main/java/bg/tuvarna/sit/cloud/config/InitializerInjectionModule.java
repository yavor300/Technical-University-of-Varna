package bg.tuvarna.sit.cloud.config;

import bg.tuvarna.sit.cloud.core.aws.eks.EksClusterCloudBundleRunner;
import bg.tuvarna.sit.cloud.core.aws.eks.state.EksClusterStateComparator;
import bg.tuvarna.sit.cloud.core.aws.s3.S3CloudBundleRunner;
import bg.tuvarna.sit.cloud.core.aws.s3.state.S3StateComparator;
import bg.tuvarna.sit.cloud.core.provisioner.CloudBundleRunner;
import bg.tuvarna.sit.cloud.utils.NamedInjections;

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

@SuppressWarnings({"unused"})
public class InitializerInjectionModule extends AbstractModule {

  @Override
  protected void configure() {}

  @Provides
  @Named(NamedInjections.YAML_MAPPER)
  @Singleton
  ObjectMapper provideYamlMapper() {

    YAMLFactory factory = new YAMLFactory();
    factory.disable(YAMLGenerator.Feature.SPLIT_LINES);
    factory.enable(YAMLGenerator.Feature.MINIMIZE_QUOTES);
    factory.enable(YAMLGenerator.Feature.LITERAL_BLOCK_STYLE);

    return new ObjectMapper(factory);
  }

  @Provides
  @Named(NamedInjections.JSON_MAPPER)
  @Singleton
  ObjectMapper provideJsonMapper() {
    return new ObjectMapper();
  }

  @Provides
  @Named(NamedInjections.DEFAULT_PRETTY_PRINTER)
  @Singleton
  ObjectWriter provideJsonMapperPrettyPrinter() {
    return new ObjectMapper().writerWithDefaultPrettyPrinter();
  }

  @Provides
  @Named(NamedInjections.JSON_MAPPER_SINGLE_VALUE_ARRAY)
  @Singleton
  ObjectMapper provideYamlMapperPrettyPrinter() {
    return new ObjectMapper()
        .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
  }

  @Provides
  @Singleton
  S3StateComparator s3StateComparator() { return new S3StateComparator(); }

  @Provides
  @Singleton
  EksClusterStateComparator eksClusterStateComparator() { return new EksClusterStateComparator(); }

  // TODO [Medium] Use Set in other places as well
  @Provides
  @Named(NamedInjections.BUNDLES)
  @Singleton
  Set<CloudBundleRunner<?>> provideBundles(S3CloudBundleRunner s3, EksClusterCloudBundleRunner eks) {
    return Set.of(s3, eks);
  }

}
