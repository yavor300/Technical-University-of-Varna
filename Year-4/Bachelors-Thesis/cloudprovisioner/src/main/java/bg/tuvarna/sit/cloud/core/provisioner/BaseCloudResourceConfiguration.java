package bg.tuvarna.sit.cloud.core.provisioner;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseCloudResourceConfiguration implements IdentifiableConfig {

    private String id;
    private boolean toDelete = false;
}
