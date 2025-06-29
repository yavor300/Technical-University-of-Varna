package bg.tuvarna.sit.cloud.utils;

import lombok.Getter;

@Getter
public enum EnvVar {

  AWS_PROFILE("AWS_PROFILE"),
  LOG_FORMAT("LOG_FORMAT"),
  ENDPOINT_URL("ENDPOINT_URL"),
  S3_ENDPOINT_URL("S3_ENDPOINT_URL");

  private final String key;

  EnvVar(String key) {
    this.key = key;
  }

  public String getValue() {
    return System.getenv(key);
  }

  @SuppressWarnings("unused")
  public String getValueOrDefault(String defaultValue) {
    String value = getValue();
    return value != null ? value : defaultValue;
  }

}
