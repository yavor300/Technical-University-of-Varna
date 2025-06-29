package bg.tuvarna.sit.cloud.credentials.provider;

import java.io.IOException;

public interface CloudCredentialsProvider<K> {

  K fetchCredentials() throws IOException;
}