package bg.tuvarna.sit.cloud.core.provisioner;

import java.util.List;

public interface CloudResourceConfigList<C extends BaseCloudResourceConfiguration> {

    List<C> getItems();

    void setItems(List<C> items);
}
