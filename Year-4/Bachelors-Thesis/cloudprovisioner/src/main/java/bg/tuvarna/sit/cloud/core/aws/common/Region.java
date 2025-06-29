package bg.tuvarna.sit.cloud.core.aws.common;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;

@Getter
public enum Region {

  US_EAST_1("us-east-1"),
  US_EAST_2("us-east-2"),
  US_WEST_1("us-west-1"),
  US_WEST_2("us-west-2"),
  CA_CENTRAL_1("ca-central-1"),
  EU_WEST_1("eu-west-1"),
  EU_WEST_2("eu-west-2"),
  EU_WEST_3("eu-west-3"),
  EU_CENTRAL_1("eu-central-1"),
  EU_CENTRAL_2("eu-central-2"),
  EU_NORTH_1("eu-north-1"),
  EU_SOUTH_1("eu-south-1"),
  EU_SOUTH_2("eu-south-2"),
  GLOBAL("aws-global");

  private final String value;

  Region(String value) {
    this.value = value;
  }

  @JsonCreator
  public static Region from(String value) {

    for (Region region : values()) {
      if (region.value.equalsIgnoreCase(value)) {
        return region;
      }
    }

    throw new IllegalArgumentException("Unknown or unsupported AWS region: " + value);
  }
}
