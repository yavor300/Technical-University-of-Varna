package bg.tuvarna.sit.cloud.credentials;

import bg.tuvarna.sit.cloud.utils.ConfigurationUtil;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class BaseAuthenticationManagerTest {

  static class DummyManager extends BaseAuthenticationManager<String> {

    public DummyManager(ConfigurationUtil config) {
      super(config);
    }

    @Override
    public String getCredentials() {
      return null;
    }

    @Override
    protected String getAuthenticationConfigurationPath() {
      return null;
    }

    @Override
    public String resolveEnvPlaceholders(String input) {
      return super.resolveEnvPlaceholders(input);
    }
  }

  @Test
  void resolveEnvPlaceholders_shouldReplaceKnownEnvVars() {
    DummyManager manager = new DummyManager(null);
    String input = "User path is: ${PATH}";
    String resolved = manager.resolveEnvPlaceholders(input);

    assertThat(resolved).doesNotContain("${PATH}");
    assertThat(resolved).contains(System.getenv("PATH"));
  }

  @Test
  void resolveEnvPlaceholders_shouldReplaceUnknownEnvVarsWithEmptyString() {
    DummyManager manager = new DummyManager(null);
    String input = "Unknown var: ${NON_EXISTENT_ENV_VAR}";
    String resolved = manager.resolveEnvPlaceholders(input);

    assertThat(resolved).isEqualTo("Unknown var: ");
  }

  @Test
  void resolveEnvPlaceholders_shouldReturnNullWhenInputIsNull() {
    DummyManager manager = new DummyManager(null);
    assertThat(manager.resolveEnvPlaceholders(null)).isNull();
  }

  @Test
  void resolveEnvPlaceholders_shouldReturnInputWhenNoPlaceholders() {
    DummyManager manager = new DummyManager(null);
    assertThat(manager.resolveEnvPlaceholders("no placeholders here")).isEqualTo("no placeholders here");
  }
}
