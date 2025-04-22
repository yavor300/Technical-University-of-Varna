package bg.tuvarna.sit.cloud.core.aws.s3;

public enum S3Output {

  TYPE("type"),
  KMS_KEY_ID("kmsKeyId"),
  VERSIONING_STATUS("status"),
  VALUE_NODE("value"),
  OWNER("owner"),
  GRANTS("grants"),
  NAME("name");

  private final String key;

  S3Output(String key) {
    this.key = key;
  }

  @Override
  public String toString() {
    return key;
  }
}
