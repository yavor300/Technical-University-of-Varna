package bg.tuvarna.sit.cloud.core.aws.s3.config;

import bg.tuvarna.sit.cloud.core.aws.s3.model.S3AclType;
import bg.tuvarna.sit.cloud.core.aws.s3.model.S3EncryptionType;
import bg.tuvarna.sit.cloud.core.aws.s3.model.S3OwnershipType;
import bg.tuvarna.sit.cloud.core.provisioner.ArnBuilder;
import bg.tuvarna.sit.cloud.core.provisioner.DestroyProtection;
import bg.tuvarna.sit.cloud.core.provisioner.BaseCloudResourceConfiguration;

import lombok.Getter;
import lombok.Setter;

import software.amazon.awssdk.services.s3.model.BucketVersioningStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO [Implementation] Validate configuration
@Setter
@Getter
public class S3BucketConfig extends BaseCloudResourceConfiguration implements ArnBuilder, DestroyProtection {

  private String name;
  private String region;
  private Map<String, String> tags = new HashMap<>();
  private String versioning = BucketVersioningStatus.ENABLED.toString();
  private EncryptionConfig encryption = new EncryptionConfig(S3EncryptionType.AES_256);
  private S3OwnershipType ownershipControls = S3OwnershipType.BUCKET_OWNER_ENFORCED;
  private AclConfig acl = new AclConfig();
  private String policy;
  private boolean preventDestroy = false;
  private boolean enableReconciliation = true;

  @Override
  public String buildArn() {

    return "arn:aws:s3:::" + name;
  }

  @Override
  public boolean preventDestroy() {

    return preventDestroy;
  }

  @Getter
  @Setter
  public static class EncryptionConfig {

    private S3EncryptionType type;
    private String kmsKeyId;
    private boolean bucketKeyEnabled = false;

    public EncryptionConfig() {
    }

    public EncryptionConfig(S3EncryptionType type) {
      this.type = type;
    }
  }

  @Getter
  @Setter
  public static class AclConfig {

    private S3AclType canned;
    private AccessControlPolicy accessControlPolicy;

    public AclConfig() {
      this.canned = null;
      this.accessControlPolicy = null;
    }
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
