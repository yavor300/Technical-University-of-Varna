package bg.tuvarna.sit.cloud.core.aws.eks.config;

import bg.tuvarna.sit.cloud.core.provisioner.BaseCloudResourceConfigList;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EksClusterConfigList extends BaseCloudResourceConfigList<EksClusterConfig> {

  private List<EksClusterConfig> clusters = new ArrayList<>();

  @Override
  public List<EksClusterConfig> getItems() {
    return clusters;
  }

  @Override
  public void setItems(List<EksClusterConfig> items) {
    this.clusters = items;
  }
}
