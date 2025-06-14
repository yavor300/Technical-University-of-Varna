package bg.tuvarna.sit.cloud.core.aws.s3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class S3ProvisionedAclOwner {

  private String id;
  private String displayName;

  @Override
  public String toString() {
    return "{id=%s, displayName=%s}".formatted(id, displayName);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o instanceof S3ProvisionedAclOwner that) {
      return Objects.equals(id, that.id) && Objects.equals(displayName, that.displayName);
    }

    if (o instanceof Map<?, ?> map) {
      Object mapId = map.get("id");
      Object mapDisplayName = map.get("displayName");
      return Objects.equals(id, mapId) && Objects.equals(displayName, mapDisplayName);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, displayName);
  }
}
