package bg.tuvarna.sit.cloud.core.provisioner.model;

import lombok.Getter;

@Getter
public enum CloudResourceType {

  S3("Bucket"),
  EKS("Cluster");

  private final String value;

  CloudResourceType(String value) {
    this.value = value;
  }

  public static CloudResourceType from(String value) {

    for (CloudResourceType region : values()) {
      if (region.value.equalsIgnoreCase(value)) {
        return region;
      }
    }

    throw new IllegalArgumentException("Unknown cloud resource type: " + value);
  }
}
