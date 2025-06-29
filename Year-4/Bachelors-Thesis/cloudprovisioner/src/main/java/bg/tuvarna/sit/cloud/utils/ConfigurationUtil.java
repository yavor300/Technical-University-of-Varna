package bg.tuvarna.sit.cloud.utils;

import bg.tuvarna.sit.cloud.exception.ConfigurationLoadException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.io.File;
import java.io.IOException;

@Singleton
public class ConfigurationUtil {

  private final ObjectMapper yaml;

  @Inject
  public ConfigurationUtil(@Named(NamedInjections.YAML_MAPPER) ObjectMapper yaml) {
    this.yaml = yaml;
  }

  public <T> T load(String path, Class<T> clazz) {

    File file = new File(path);

    if (!file.exists()) {
      throw new ConfigurationLoadException("Configuration file not found: " + path);
    }

    try {
      return yaml.readValue(file, clazz);
    } catch (IOException e) {
      throw new ConfigurationLoadException("Failed to parse configuration file at path: " + path, e);
    }
  }
}
