package bg.tuvarna.sit.cloud.core.aws.s3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class S3ProvisionedAclGrantee {

  private String type;
  private String identifier;
  private String uri;
  private String emailAddress;

  @Override
  public String toString() {
    return "{type=%s, identifier=%s, uri=%s, email=%s}".formatted(type, identifier, uri, emailAddress);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o instanceof S3ProvisionedAclGrantee that) {
      return Objects.equals(type, that.type)
          && Objects.equals(identifier, that.identifier)
          && Objects.equals(uri, that.uri)
          && Objects.equals(emailAddress, that.emailAddress);
    }

    if (o instanceof Map<?, ?> map) {
      Object mapType = map.get("type");
      Object mapIdentifier = map.get("identifier");
      return Objects.equals(type, mapType) &&
          Objects.equals(identifier, mapIdentifier);
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(type, identifier);
  }
}
