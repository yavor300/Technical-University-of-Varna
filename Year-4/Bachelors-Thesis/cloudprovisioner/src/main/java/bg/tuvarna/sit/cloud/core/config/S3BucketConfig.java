package bg.tuvarna.sit.cloud.core.config;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

// TODO Extend the class with more features from the S3
@Setter
@Getter
public class S3BucketConfig {

  private String name;
  private String region;

  private Map<String, String> tags;
  private String versioning;
  private EncryptionConfig encryption;
  private String acl;
  private String policy;


  @Getter
  @Setter
  public static class EncryptionConfig {
    private String type;
    private String kmsKeyId;
  }
}
