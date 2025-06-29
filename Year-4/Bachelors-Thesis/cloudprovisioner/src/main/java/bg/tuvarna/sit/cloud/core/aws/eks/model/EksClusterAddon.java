package bg.tuvarna.sit.cloud.core.aws.eks.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EksClusterAddon {

  private String name;
  private String version;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof EksClusterAddon that)) return false;
    return Objects.equals(name, that.name) &&
        Objects.equals(version, that.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, version);
  }

  @Override
  public String toString() {
    return "{name='%s', version='%s'}".formatted(name, version);
  }

}
