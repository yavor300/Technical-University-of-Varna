package bg.tuvarna.sit.cloud.core.aws;

import bg.tuvarna.sit.cloud.core.aws.common.Region;
import bg.tuvarna.sit.cloud.core.provisioner.BaseCloudResourceConfiguration;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public abstract class AwsBaseCloudResourceConfiguration extends BaseCloudResourceConfiguration {

  private Region region = Region.GLOBAL;
  private Map<String, String> tags = new HashMap<>();

}
