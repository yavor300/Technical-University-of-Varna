package bg.tuvarna.sit.cloud.core.aws.eks.model;

import bg.tuvarna.sit.cloud.core.aws.common.model.ProvisionedTags;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EksClusterNodeGroup {

  private String name;
  private String instanceType;
  private int desiredSize;
  private int minSize;
  private int maxSize;
  private String nodeRoleArn;
  private SubnetIdSet subnetIdSet;
  private EksClusterAmiType amiType;
  private String releaseVersion;
  private Integer diskSize;
  private ProvisionedLabels labels;
  private ProvisionedTags tags;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof EksClusterNodeGroup that)) return false;
    return desiredSize == that.desiredSize &&
        minSize == that.minSize &&
        maxSize == that.maxSize &&
        Objects.equals(name, that.name) &&
        Objects.equals(instanceType, that.instanceType) &&
        Objects.equals(nodeRoleArn, that.nodeRoleArn) &&
        Objects.equals(amiType, that.amiType) &&
        (releaseVersion == null || that.releaseVersion == null || Objects.equals(releaseVersion, that.releaseVersion)) &&
        Objects.equals(diskSize, that.diskSize) &&
        Objects.equals(subnetIdSet, that.subnetIdSet) &&
        Objects.equals(labels, that.labels) &&
        Objects.equals(tags, that.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, instanceType, desiredSize, minSize, maxSize, nodeRoleArn, labels, tags);
  }

  @Override
  public String toString() {
    return "{" +
        "name='" + name + '\'' +
        ", instanceType='" + instanceType + '\'' +
        ", desiredSize=" + desiredSize +
        ", minSize=" + minSize +
        ", maxSize=" + maxSize +
        ", nodeRoleArn='" + nodeRoleArn + '\'' +
        ", subnetIdSet=" + subnetIdSet +
        ", amiType=" + (amiType != null ? amiType.getValue() : null) +
        ", releaseVersion='" + releaseVersion + '\'' +
        ", diskSize=" + diskSize +
        ", labels=" + (labels != null ? labels.getLabels() : null) +
        ", tags=" + (tags != null ? tags.getTags() : null) +
        '}';
  }

}
