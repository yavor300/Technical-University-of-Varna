package bg.tuvarna.sit.cloud.common;

import lombok.Getter;

@Getter
public enum ErrorCode {

  AUTH_CONFIG_LOAD_ERROR("AUTH_001", "Failed to load authentication configuration"),
  S3_CONFIG_LOAD_ERROR("S3_001", "Failed to load S3 configuration"),
  VAULT_AUTH_ERROR("AUTH_002", "Vault authentication failed"),
  S3_PROVISION_ERROR("S3_002", "S3 provisioning failed"),
  SERIALIZATION_ERROR("SYS_001", "Failed to serialize provisioning response");

  private final String code;
  private final String message;

  ErrorCode(String code, String message) {
    this.code = code;
    this.message = message;
  }

}
