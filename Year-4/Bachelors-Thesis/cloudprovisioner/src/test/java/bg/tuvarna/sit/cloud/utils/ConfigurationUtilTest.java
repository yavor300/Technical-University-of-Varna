package bg.tuvarna.sit.cloud.utils;

import bg.tuvarna.sit.cloud.exception.ConfigurationLoadException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConfigurationUtilTest {

  @Mock
  ObjectMapper yaml;

  private ConfigurationUtil configUtil;

  @BeforeEach
  void setUp() {
    configUtil = new ConfigurationUtil(yaml);
  }

  static class DummyConfig {
    public String name;
  }

  @Test
  void load_shouldReturnDeserializedObject_whenFileIsValid() throws Exception {
    File tempFile = File.createTempFile("config", ".yml");
    tempFile.deleteOnExit();

    DummyConfig expected = new DummyConfig();
    expected.name = "test";

    when(yaml.readValue(eq(tempFile), eq(DummyConfig.class))).thenReturn(expected);

    DummyConfig result = configUtil.load(tempFile.getAbsolutePath(), DummyConfig.class);

    assertThat(result).isNotNull();
    assertThat(result.name).isEqualTo("test");

    verify(yaml).readValue(tempFile, DummyConfig.class);
  }

  @Test
  void load_shouldThrow_whenFileDoesNotExist() {
    String invalidPath = "non-existent-file.yml";

    assertThatThrownBy(() -> configUtil.load(invalidPath, DummyConfig.class))
        .isInstanceOf(ConfigurationLoadException.class)
        .hasMessageContaining("Configuration file not found");
  }

  @Test
  void load_shouldThrow_whenYamlParsingFails() throws Exception {
    File tempFile = File.createTempFile("invalid", ".yml");
    tempFile.deleteOnExit();

    when(yaml.readValue(eq(tempFile), eq(DummyConfig.class)))
        .thenThrow(new IOException("YAML error"));

    assertThatThrownBy(() -> configUtil.load(tempFile.getAbsolutePath(), DummyConfig.class))
        .isInstanceOf(ConfigurationLoadException.class)
        .hasMessageContaining("Failed to parse configuration file");

    verify(yaml).readValue(tempFile, DummyConfig.class);
  }
}
