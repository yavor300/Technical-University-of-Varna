package bg.tuvarna.sit.cloud.core.aws.eks.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EksClusterAddons {

  @JsonValue
  private List<EksClusterAddon> addons = new ArrayList<>();

  @JsonCreator
  public static EksClusterAddons fromJson(List<EksClusterAddon> addons) {
    return new EksClusterAddons(addons);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof EksClusterAddons that)) return false;
    return new HashSet<>(addons).equals(new HashSet<>(that.addons));
  }

  @Override
  public int hashCode() {
    return Objects.hash(new HashSet<>(addons));
  }

  @Override
  public String toString() {
    return addons.toString();
  }

}
