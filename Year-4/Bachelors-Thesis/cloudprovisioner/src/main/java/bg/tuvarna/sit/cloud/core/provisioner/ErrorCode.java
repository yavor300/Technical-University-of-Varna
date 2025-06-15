package bg.tuvarna.sit.cloud.common;

import lombok.Getter;

@Getter
public enum ErrorCode {

  AUTH_CONFIG_LOAD_ERROR("AUTH_1000", "Failed to load authentication configuration"),
  AUTH_ERROR("AUTH_1100", "Provider authentication failed"),
  S3_CONFIG_LOAD_ERROR("S3_1000", "Failed to load S3 configuration"),
  S3_PROVISION_ERROR("S3_1100", "S3 provisioning failed"),
  S3_RECONCILIATION_ERROR("S3_1200", "S3 reconciliation failed"),
  S3_LIVE_STATE_ERROR("S3_1300", "Failed to fetch S3 live state"),
  S3_DESIRED_STATE_ERROR("S3_1400", "Failed to generate S3 desired state"),
  S3_STORED_STATE_ERROR("S3_1500", "Failed to load stored S3 state"),
  SERIALIZATION_ERROR("SYS_1000", "Failed to serialize provisioning response"),
  ASYNC_EXECUTION_ERROR("SYS_1100", "Unexpected exception occurred during asynchronous task execution");

  private final String code;
  private final String message;

  ErrorCode(String code, String message) {
    this.code = code;
    this.message = message;
  }

}
