package bg.tuvarna.sit.cloud.core.aws.eks;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum EksClusterOutput {

  TAGS("tags"),
  NAME("name"),
  PREVENT_DESTROY("preventDestroy"),
  REGION("region"),
  STATUS("status"),
  ARN("arn"),
  ROLE_ARN("roleArn"),
  VERSION("version"),
  SUBNETS("subnets"),
  CLUSTER_CREATOR_ADMIN_PERMISSIONS("bootstrapClusterCreatorAdminPermissions"),
  AUTHENTICATION_MODE("authenticationMode"),
  SUPPORT_TYPE("supportType"),
  ZONAL_SHIFT_ENABLED("enableZonalShift"),
  OWNED_KMS_KEY_ID("ownedEncryptionKMSKeyArn"),
  SELF_MANAGED_ADDONS("bootstrapSelfManagedAddons"),
  ADDONS("addons"),
  NODE_GROUPS("nodeGroups");

  private final String key;

  EksClusterOutput(String key) {
    this.key = key;
  }

  @Override
  public String toString() {
    return key;
  }

  @JsonCreator
  public static EksClusterOutput fromValue(String value) {
    for (EksClusterOutput output : values()) {
      if (output.key.equalsIgnoreCase(value)) {
        return output;
      }
    }
    throw new IllegalArgumentException("Unknown EksClusterOutput key: " + value);
  }
}
