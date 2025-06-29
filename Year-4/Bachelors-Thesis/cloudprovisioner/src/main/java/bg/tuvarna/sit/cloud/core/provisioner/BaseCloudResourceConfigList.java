package bg.tuvarna.sit.cloud.core.provisioner;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseCloudResourceConfigList<K extends BaseCloudResourceConfiguration> implements CloudResourceConfigList<K> {

  private FixedThreadPoolConfiguration fixedThreadPool = new FixedThreadPoolConfiguration();

  @Getter
  public static class FixedThreadPoolConfiguration {

    int maxSize = 5;
  }
}
