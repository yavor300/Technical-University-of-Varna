package bg.tuvarna.sit.cloud.core.aws.s3;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum S3Output {

  TYPE("type"),
  KMS_KEY_ID("kmsKeyId"),
  VERSIONING_STATUS("status"),
  VALUE_NODE("value"),
  OWNER("owner"),
  GRANTS("grants"),
  NAME("name"),
  REGION("region");

  private final String key;

  S3Output(String key) {
    this.key = key;
  }

  @Override
  public String toString() {
    return key;
  }

  @JsonCreator
  public static S3Output fromValue(String value) {
    for (S3Output output : values()) {
      if (output.key.equalsIgnoreCase(value)) {
        return output;
      }
    }
    throw new IllegalArgumentException("Unknown S3Output key: " + value);
  }
}
