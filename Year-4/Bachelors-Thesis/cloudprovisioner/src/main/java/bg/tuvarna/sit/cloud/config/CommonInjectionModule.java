package bg.tuvarna.sit.cloud.config;

import bg.tuvarna.sit.cloud.core.aws.s3.StateComparator;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import jakarta.inject.Named;

public class CommonInjectionModule extends AbstractModule {

  @Override
  protected void configure() {}

  @Provides
  @Named("yamlMapper")
  ObjectMapper provideYamlMapper() {

    YAMLFactory factory = new YAMLFactory();
    factory.disable(YAMLGenerator.Feature.SPLIT_LINES);
    factory.enable(YAMLGenerator.Feature.MINIMIZE_QUOTES);
    factory.enable(YAMLGenerator.Feature.LITERAL_BLOCK_STYLE);

    return new ObjectMapper(factory);
  }

  @Provides
  @Named("jsonMapper")
  ObjectMapper provideJsonMapper() {
    return new ObjectMapper();
  }

  @Provides
  @Named("defaultPrettyPrinter")
  ObjectWriter provideJsonMapperPrettyPrinter() {
    return new ObjectMapper().writerWithDefaultPrettyPrinter();
  }

  @Provides
  @Named("jsonMapperAcceptSingleValueAsArray")
  ObjectMapper provideYamlMapperPrettyPrinter() {
    return new ObjectMapper()
        .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
  }

  @Provides
  StateComparator provideStateComparator() {
    return new StateComparator();
  }
}
