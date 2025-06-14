package bg.tuvarna.sit.cloud.core.aws.s3.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum S3EncryptionType {

  SSE_S3("sse-s3"),
  AES_256("aes256"),
  AWS_KMS("aws:kms"),
  AWS_KMS_DSSE("aws:kms:dsse");

  private final String value;

  S3EncryptionType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @JsonCreator
  public static S3EncryptionType fromValue(String value) {
    for (S3EncryptionType type : values()) {
      if (type.value.equalsIgnoreCase(value)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Unknown encryption type: " + value);
  }

  @Override
  public String toString() {
    return value;
  }
}
