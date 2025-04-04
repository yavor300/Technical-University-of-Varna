package bg.tuvarna.sit.cloud.core.config;


import lombok.Getter;
import lombok.Setter;

// TODO Extend the class with more features from the S3
@Setter
@Getter
public class S3BucketConfig {

  private String name;
  private String region;
}
