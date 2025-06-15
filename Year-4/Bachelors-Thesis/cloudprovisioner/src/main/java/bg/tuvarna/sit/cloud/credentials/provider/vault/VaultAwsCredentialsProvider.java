package bg.tuvarna.sit.cloud.credentials.provider.vault;

import bg.tuvarna.sit.cloud.credentials.AwsCredentials;
import bg.tuvarna.sit.cloud.credentials.provider.CloudCredentialsProvider;
import bg.tuvarna.sit.cloud.credentials.model.VaultAwsCredentialsData;
import bg.tuvarna.sit.cloud.credentials.model.VaultResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import static bg.tuvarna.sit.Main.isJsonLoggingEnabled;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Slf4j
public class VaultAwsCredentialsProvider implements CloudCredentialsProvider<AwsCredentials> {

  private final VaultClient vaultClient;
  private final ObjectMapper yamlMapper;

  // TODO [Implementation] Inject
  public VaultAwsCredentialsProvider(VaultClient vaultClient, ObjectMapper yamlMapper) {
    this.vaultClient = vaultClient;
    this.yamlMapper = yamlMapper;
  }

  @Override
  public AwsCredentials fetchCredentials() {

    VaultResponse response = vaultClient.getSecrets();
    VaultAwsCredentialsData credentials = response.getData().getData();
    AwsCredentials result = new AwsCredentials(credentials.getAccessKeyId(), credentials.getSecretAccessKey());

    credentials.setAccessKeyId("****");
    credentials.setSecretAccessKey("****");

    if (isJsonLoggingEnabled()) {
      log.info("AWS credentials successfully fetched from Vault", keyValue("response", response));
    } else {
      try {
        log.info("AWS credentials successfully fetched from Vault.\n{}", yamlMapper.writeValueAsString(response));
      } catch (JsonProcessingException e) {
        log.debug("Failed to serialize Vault response to YAML format.", e);
      }
    }

    return result;
  }
}
