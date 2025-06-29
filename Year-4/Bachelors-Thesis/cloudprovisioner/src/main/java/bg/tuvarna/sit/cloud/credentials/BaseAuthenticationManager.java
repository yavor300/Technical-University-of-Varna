package bg.tuvarna.sit.cloud.credentials;

import bg.tuvarna.sit.cloud.utils.ConfigurationUtil;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public abstract class BaseCredentialsProvider<K> {

  protected final ConfigurationUtil config;

  protected BaseCredentialsProvider(ConfigurationUtil config) {
    this.config = config;
  }

  public abstract K getCredentials();

  protected abstract String getAuthenticationConfigurationPath();

  protected String resolveEnvPlaceholders(String input) {

    if (input == null) return null;

    Pattern pattern = Pattern.compile("\\$\\{(.+?)}");
    Matcher matcher = pattern.matcher(input);
    StringBuilder buffer = new StringBuilder();
    while (matcher.find()) {
      String envVar = matcher.group(1);
      String value = System.getenv(envVar);
      if (value == null) {
        log.warn("Environment variable '{}' not found. Replacing with empty value.", envVar);
        value = "";
      }
      matcher.appendReplacement(buffer, Matcher.quoteReplacement(value));
    }
    matcher.appendTail(buffer);

    return buffer.toString();
  }
}
