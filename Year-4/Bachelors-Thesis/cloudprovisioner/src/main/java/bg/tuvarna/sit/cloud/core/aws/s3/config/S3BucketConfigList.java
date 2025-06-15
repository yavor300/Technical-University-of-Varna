package bg.tuvarna.sit.cloud.core.aws.s3;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class S3BucketConfigList {

    private List<S3BucketConfig> buckets = new ArrayList<>();
}
