package bg.tuvarna.sit.cloud.core.provisioner;

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
  EKS_CONFIG_LOAD_ERROR("EKS_1000", "Failed to load EKS configuration"),
  EKS_PROVISION_ERROR("EKS_1100", "EKS provisioning failed"),
  CONFIG_LOAD_ERROR("SYS_1000", "Failed to load configuration list file"),
  SERIALIZATION_ERROR("SYS_1200", "Failed to serialize provisioning response"),
  ASYNC_EXECUTION_ERROR("SYS_1300", "Unexpected exception occurred during asynchronous task execution");

  private final String code;
  private final String message;

  ErrorCode(String code, String message) {
    this.code = code;
    this.message = message;
  }

}
