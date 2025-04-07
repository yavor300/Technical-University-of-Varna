package bg.tuvarna.sit.cloud.credentials.aws;

import bg.tuvarna.sit.cloud.credentials.CloudCredentials;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

import java.util.Map;

public class AwsCredentials implements CloudCredentials {

  private final String accessKeyId;
  private final String secretAccessKey;

  public AwsCredentials(String accessKeyId, String secretAccessKey) {
    this.accessKeyId = accessKeyId;
    this.secretAccessKey = secretAccessKey;
  }

  @Override
  public Map<String, String> asMap() {
    return Map.of(
        "AWS_ACCESS_KEY_ID", accessKeyId,
        "AWS_SECRET_ACCESS_KEY", secretAccessKey
    );
  }

  public AwsBasicCredentials toAwsBasicCredentials() {
    return AwsBasicCredentials.create(accessKeyId, secretAccessKey);
  }
}
