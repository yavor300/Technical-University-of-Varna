package bg.tuvarna.sit.cloud.core.provisioner;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseCloudResourceConfiguration implements IdentifiableConfig, DestroyProtection {

  protected static final String DEFAULT_EMPTY_STRING = "";

  // TODO [Implementation:Low] @Required?
  private String id;
  private String name;
  private boolean enableReconciliation = true;
  private Boolean preventDestroy = true;
  private boolean toDelete = false;
  private RetryConfiguration retry = new RetryConfiguration();

  @Override
  public Boolean preventDestroy() {

    return preventDestroy;
  }

  @Getter
  public static class RetryConfiguration {

    int max = 3;
    long backoffMs = 1000;
  }

}
