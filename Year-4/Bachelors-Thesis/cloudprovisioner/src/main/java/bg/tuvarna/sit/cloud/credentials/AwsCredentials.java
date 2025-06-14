package bg.tuvarna.sit.cloud.credentials;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

public class AwsCredentials implements CloudCredentials {

  private final String accessKeyId;
  private final String secretAccessKey;

  public AwsCredentials(String accessKeyId, String secretAccessKey) {
    this.accessKeyId = accessKeyId;
    this.secretAccessKey = secretAccessKey;
  }

  public AwsBasicCredentials toAwsBasicCredentials() {
    return AwsBasicCredentials.create(accessKeyId, secretAccessKey);
  }
}
