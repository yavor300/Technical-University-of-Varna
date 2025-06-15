package bg.tuvarna.sit.cloud.core.aws.s3;

import lombok.AllArgsConstructor;
import lombok.Getter;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;

@AllArgsConstructor
@Getter
public class S3ProvisioningContext {

  private final AwsBasicCredentials credentials;
  private final String endpoint;
  private final Region region;
}
