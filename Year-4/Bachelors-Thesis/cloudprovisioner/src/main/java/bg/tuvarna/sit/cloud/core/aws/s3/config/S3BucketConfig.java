package bg.tuvarna.sit.cloud.core.aws.s3.config;

import bg.tuvarna.sit.cloud.core.aws.AwsBaseCloudResourceConfiguration;
import bg.tuvarna.sit.cloud.core.aws.s3.model.S3AclType;
import bg.tuvarna.sit.cloud.core.aws.s3.model.S3EncryptionType;
import bg.tuvarna.sit.cloud.core.aws.s3.model.S3OwnershipType;
import bg.tuvarna.sit.cloud.core.provisioner.ArnBuilder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import software.amazon.awssdk.services.s3.model.BucketVersioningStatus;

import java.util.List;

// TODO [Implementation] Validate configuration
@Setter
@Getter
public class S3BucketConfig extends AwsBaseCloudResourceConfiguration implements ArnBuilder {

  // TODO [Low] Think whether to use the classes from model package for default values
  private String versioning = BucketVersioningStatus.ENABLED.toString();
  private EncryptionConfig encryption = new EncryptionConfig(S3EncryptionType.AES_256);
  private S3OwnershipType ownershipControls = S3OwnershipType.BUCKET_OWNER_ENFORCED;
  private AclConfig acl = new AclConfig();
  private String policy = DEFAULT_EMPTY_STRING;

  @Override
  public String buildArn() {

    return "arn:aws:s3:::%s".formatted(getName());
  }

  @Getter
  @Setter
  public static class EncryptionConfig {

    private S3EncryptionType type;
    private String kmsKeyId;
    private boolean bucketKeyEnabled = false;

    @SuppressWarnings("unused")
    public EncryptionConfig() {
      // Required by Jackson
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
  @AllArgsConstructor
  @NoArgsConstructor
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
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Owner {
    private String id;
    private String displayName;
  }
}
