package bg.tuvarna.sit.cloud.core.aws.eks.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

@Getter
public enum EksClusterAmiType {

  AL2_X86_64("AL2_x86_64"),
  AL2_X86_64_GPU("AL2_x86_64_GPU"),
  AL2_ARM_64("AL2_ARM_64"),
  BOTTLEROCKET_ARM_64("BOTTLEROCKET_ARM_64"),
  BOTTLEROCKET_X86_64("BOTTLEROCKET_x86_64"),
  CUSTOM("CUSTOM");
  private final String value;

  EksClusterAmiType(String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator
  public static EksClusterAmiType fromValue(String input) {

    for (EksClusterAmiType type : values()) {
      if (type.value.equalsIgnoreCase(input)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Unknown AMI type: " + input);
  }
}
