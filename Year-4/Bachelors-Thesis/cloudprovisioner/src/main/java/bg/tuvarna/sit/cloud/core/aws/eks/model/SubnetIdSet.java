package bg.tuvarna.sit.cloud.core.aws.eks.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SubnetIdSet {

  private final List<String> subnetIds;

  @JsonCreator
  public SubnetIdSet(List<String> subnetIds) {
    this.subnetIds = new ArrayList<>(subnetIds);
  }

  @JsonValue
  public List<String> getSubnetIds() {
    return subnetIds;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SubnetIdSet that = (SubnetIdSet) o;
    return Objects.equals(subnetIds, that.subnetIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subnetIds);
  }

  @Override
  public String toString() {
    return subnetIds.toString();
  }
}
