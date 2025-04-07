package bg.tuvarna.sit.cloud.credentials.provider;

import bg.tuvarna.sit.cloud.credentials.CloudCredentials;

import java.io.IOException;

public interface CloudCredentialsProvider<T extends CloudCredentials> {

  T fetchCredentials() throws IOException;
}