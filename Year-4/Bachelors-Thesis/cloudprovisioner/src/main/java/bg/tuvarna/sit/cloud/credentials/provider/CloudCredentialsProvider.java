package bg.tuvarna.sit.cloud.credentials.provider;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

import java.io.IOException;

public interface CloudCredentialsProvider {

  AwsBasicCredentials fetchAwsCredentials() throws IOException;
}
