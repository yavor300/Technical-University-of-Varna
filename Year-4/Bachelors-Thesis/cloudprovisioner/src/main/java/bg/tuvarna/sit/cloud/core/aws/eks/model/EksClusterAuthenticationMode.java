package bg.tuvarna.sit.cloud.core.aws.eks.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum EksClusterAuthenticationMode {

  API("API"),
  API_AND_CONFIG_MAP("API_AND_CONFIG_MAP"),
  CONFIG_MAP("CONFIG_MAP");

  private final String value;

  EksClusterAuthenticationMode(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator
  public static EksClusterAuthenticationMode fromValue(String input) {
    for (EksClusterAuthenticationMode mode : values()) {
      if (mode.value.equalsIgnoreCase(input)) {
        return mode;
      }
    }
    throw new IllegalArgumentException("Unknown authentication mode: " + input);
  }
}
