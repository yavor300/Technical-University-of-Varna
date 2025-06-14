package bg.tuvarna.sit.cloud.core.aws.s3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class S3ProvisionedAcl {

  private S3ProvisionedAclOwner owner;
  private List<S3ProvisionedAclGrant> grants;
  private S3AclType cannedAcl;

  @Override
  public String toString() {
    return "{owner=%s, grants=%s, cannedAcl=%s}".formatted(owner, grants,
        cannedAcl != null ? cannedAcl.getValue() : "null");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof S3ProvisionedAcl that)) return false;
    return (Objects.equals(owner, that.owner)
        && Objects.equals(grants, that.grants))
        || (cannedAcl != null && Objects.equals(cannedAcl, that.cannedAcl));
  }

  @Override
  public int hashCode() {
    return Objects.hash(owner, grants, cannedAcl);
  }
}
