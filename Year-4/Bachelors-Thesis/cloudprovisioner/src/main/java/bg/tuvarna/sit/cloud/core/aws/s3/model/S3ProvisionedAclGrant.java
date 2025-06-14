package bg.tuvarna.sit.cloud.core.aws.s3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class S3ProvisionedAclGrant {

  private S3ProvisionedAclGrantee grantee;
  private String permission;

  @Override
  public String toString() {
    return "{grantee=%s, permission=%s}".formatted(grantee, permission);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o instanceof S3ProvisionedAclGrant that) {
      return Objects.equals(grantee, that.grantee) &&
          Objects.equals(permission, that.permission);
    }

    if (o instanceof Map<?, ?> map) {
      Object granteeObj = map.get("grantee");
      Object permissionVal = map.get("permission");

      boolean granteeEquals = grantee != null && grantee.equals(granteeObj);
      boolean permissionEquals = Objects.equals(permission, permissionVal);

      return granteeEquals && permissionEquals;
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(grantee, permission);
  }
}
