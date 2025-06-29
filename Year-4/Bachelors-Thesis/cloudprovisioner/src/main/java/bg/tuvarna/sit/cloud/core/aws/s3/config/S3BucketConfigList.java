package bg.tuvarna.sit.cloud.core.aws.s3.config;

import bg.tuvarna.sit.cloud.core.provisioner.BaseCloudResourceConfigList;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class S3BucketConfigList extends BaseCloudResourceConfigList<S3BucketConfig> {

  private List<S3BucketConfig> buckets = new ArrayList<>();

  @Override
  public List<S3BucketConfig> getItems() {

    return buckets;
  }

  @Override
  public void setItems(List<S3BucketConfig> items) {
    this.buckets = items;
  }
}
