package bg.tuvarna.sit.cloud.core.aws.eks.config;

import bg.tuvarna.sit.cloud.core.aws.AwsBaseCloudResourceConfiguration;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterAmiType;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterAuthenticationMode;
import bg.tuvarna.sit.cloud.core.aws.eks.model.EksClusterSupportType;
import bg.tuvarna.sit.cloud.core.provisioner.ArnBuilder;
import bg.tuvarna.sit.cloud.utils.EnvVar;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class EksClusterConfig extends AwsBaseCloudResourceConfiguration implements ArnBuilder {

  private String version;
  private String roleArn;
  private List<String> subnets;
  private boolean bootstrapClusterCreatorAdminPermissions = true;
  private EksClusterAuthenticationMode authenticationMode = EksClusterAuthenticationMode.API;
  // TODO [Enhancement] @SkipDiff annotation?
  private boolean bootstrapSelfManagedAddons = true;
  private EksClusterSupportType supportType = EksClusterSupportType.STANDARD;
  private boolean enableZonalShift = false;
  private String ownedEncryptionKMSKeyArn;
  private List<EksAddonConfig> addons;
  private List<EksNodeGroupConfig> nodeGroups = new ArrayList<>();

  @Override
  public String buildArn() {

    return "arn:aws:eks:%s:%s:cluster/%s".formatted(getRegion().getValue(), EnvVar.AWS_PROFILE.getValue(), getName());
  }

  @Getter
  @Setter
  public static class EksAddonConfig {
    private String name;
    private String version;
  }

  @Getter
  @Setter
  public static class EksNodeGroupConfig {

    private String name;
    private String instanceType;
    private int minSize;
    private int maxSize;
    private int desiredSize;
    private String nodeRoleArn;
    private List<String> subnets;
    private Map<String, String> labels = new HashMap<>();
    private Map<String, String> tags = new HashMap<>();
    private EksClusterAmiType amiType = EksClusterAmiType.AL2_X86_64;
    private String releaseVersion;
    private Integer diskSize = 20;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof EksNodeGroupConfig that)) return false;

      return minSize == that.minSize &&
          maxSize == that.maxSize &&
          desiredSize == that.desiredSize &&
          Objects.equals(labels, that.labels) &&
          Objects.equals(releaseVersion, that.releaseVersion);
    }

    @Override
    public int hashCode() {
      return Objects.hash(minSize, maxSize, desiredSize, labels, releaseVersion);
    }
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) return true;
    if (!(o instanceof EksClusterConfig that)) return false;

    return Objects.equals(getName(), that.getName()) &&
        Objects.equals(version, that.version) &&
        Objects.equals(subnets, that.subnets) &&
        authenticationMode == that.authenticationMode &&
        supportType == that.supportType &&
        enableZonalShift == that.enableZonalShift;
  }

}
