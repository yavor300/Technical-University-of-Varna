package bg.tuvarna.sit.cloud.core.aws.eks.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class EksClusterNodeGroups {

  @JsonValue
  private List<EksClusterNodeGroup> nodeGroups = new ArrayList<>();

  @JsonCreator
  public static EksClusterNodeGroups fromJson(List<EksClusterNodeGroup> nodeGroups) {
    return new EksClusterNodeGroups(nodeGroups);
  }

  public EksClusterNodeGroups(List<EksClusterNodeGroup> nodeGroups) {
    this.nodeGroups = nodeGroups;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof EksClusterNodeGroups that)) return false;
    return new HashSet<>(nodeGroups).equals(new HashSet<>(that.nodeGroups));
  }

  @Override
  public int hashCode() {
    return Objects.hash(new HashSet<>(nodeGroups));
  }

  @Override
  public String toString() {
    return nodeGroups.toString();
  }
}
