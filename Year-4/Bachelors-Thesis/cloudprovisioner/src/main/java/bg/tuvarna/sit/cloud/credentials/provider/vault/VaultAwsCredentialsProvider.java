package bg.tuvarna.sit.cloud.credentials.provider.vault;

import bg.tuvarna.sit.cloud.credentials.AuthenticationConfig;
import bg.tuvarna.sit.cloud.credentials.provider.CloudCredentialsProvider;
import bg.tuvarna.sit.cloud.credentials.model.VaultAwsCredentialsData;
import bg.tuvarna.sit.cloud.credentials.model.VaultResponse;
import bg.tuvarna.sit.cloud.utils.NamedInjections;
import bg.tuvarna.sit.cloud.utils.Slf4jLoggingUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Slf4j
@Singleton
public class VaultAwsCredentialsProvider implements CloudCredentialsProvider<AwsBasicCredentials> {

  private final VaultClient vaultClient;
  private final ObjectMapper yamlMapper;

  private AuthenticationConfig.VaultConfig config;

  @Inject
  public VaultAwsCredentialsProvider(VaultClient vaultClient, @Named(NamedInjections.YAML_MAPPER) ObjectMapper yamlMapper) {
    this.vaultClient = vaultClient;
    this.yamlMapper = yamlMapper;
  }

  @Override
  public AwsBasicCredentials fetchCredentials() {

    VaultResponse response = vaultClient.getSecrets(config);
    VaultAwsCredentialsData credentials = response.getData().getData();

    AwsBasicCredentials result =
        AwsBasicCredentials.create(credentials.getAccessKeyId(), credentials.getSecretAccessKey());

    credentials.setAccessKeyId("****");
    credentials.setSecretAccessKey("****");

    if (Slf4jLoggingUtil.isJsonLoggingEnabled()) {
      log.info("AWS credentials successfully fetched from Vault", keyValue("response", response));
    } else {
      try {
        log.info("AWS credentials successfully fetched from Vault.\n{}", yamlMapper.writeValueAsString(response));
      } catch (JsonProcessingException e) {
        log.debug(Slf4jLoggingUtil.DEBUG_PREFIX + "Failed to serialize Vault response to YAML format.", e);
      }
    }

    return result;
  }

  public VaultAwsCredentialsProvider config(AuthenticationConfig.VaultConfig config) {

    this.config = config;
    return this;
  }
}
