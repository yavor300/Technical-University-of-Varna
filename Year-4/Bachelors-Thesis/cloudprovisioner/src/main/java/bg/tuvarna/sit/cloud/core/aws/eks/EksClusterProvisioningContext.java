package bg.tuvarna.sit.cloud.core.aws.eks;

import lombok.AllArgsConstructor;
import lombok.Getter;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;

@AllArgsConstructor
@Getter
public class EksClusterProvisioningContext {

  private final AwsBasicCredentials credentials;
  private final String endpoint;
  private final Region region;
}
