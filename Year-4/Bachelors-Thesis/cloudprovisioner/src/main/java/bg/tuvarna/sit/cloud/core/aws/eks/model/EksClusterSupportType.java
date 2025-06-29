package bg.tuvarna.sit.cloud.core.aws.eks.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

// TODO [Maybe] Make enums case-insensitive
// TODO [Maybe] Check for getter?
@Getter
public enum EksClusterSupportType {

  STANDARD("STANDARD"),
  EXTENDED("EXTENDED");

  private final String value;

  EksClusterSupportType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator
  public static EksClusterSupportType fromValue(String input) {
    for (EksClusterSupportType type : values()) {
      if (type.value.equalsIgnoreCase(input)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Unknown support type: " + input);
  }
}