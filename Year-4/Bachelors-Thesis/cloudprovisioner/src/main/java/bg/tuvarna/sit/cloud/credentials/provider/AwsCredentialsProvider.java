package bg.tuvarna.sit.cloud.credentials.provider;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

import java.io.IOException;

public interface AwsCredentialsProvider {

  AwsBasicCredentials fetchAwsCredentials() throws IOException;
}
