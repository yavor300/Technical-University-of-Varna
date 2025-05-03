package bg.tuvarna.sit.cloud.core.aws.s3;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class S3BucketConfig {

  private String name;
  private String region;

  private Map<String, String> tags;
  private String versioning;
  private EncryptionConfig encryption;
  private S3OwnershipType ownershipControls;
  private S3AclType acl;
  private String policy;
  private AccessControlPolicy accessControlPolicy;

  @Getter
  @Setter
  public static class EncryptionConfig {
    private String type;
    private String kmsKeyId;
  }

  @Getter
  @Setter
  public static class AccessControlPolicy {
    private List<GrantConfig> grants;
    private Owner owner;
  }

  @Getter
  @Setter
  public static class GrantConfig {
    private Grantee grantee;
    private String permission;
  }

  @Getter
  @Setter
  public static class Grantee {
    private String type;
    private String id;
    private String uri;
    private String emailAddress;
  }

  @Getter
  @Setter
  public static class Owner {
    private String id;
    private String displayName;
  }
}
