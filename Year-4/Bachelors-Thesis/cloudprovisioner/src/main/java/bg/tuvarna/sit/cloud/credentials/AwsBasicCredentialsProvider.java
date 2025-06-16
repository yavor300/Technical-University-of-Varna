package bg.tuvarna.sit.cloud.credentials;

import bg.tuvarna.sit.cloud.config.AuthenticationConfig;

import bg.tuvarna.sit.cloud.core.provisioner.ErrorCode;
import bg.tuvarna.sit.cloud.credentials.provider.vault.VaultAwsCredentialsProvider;
import bg.tuvarna.sit.cloud.credentials.provider.vault.VaultClient;
import bg.tuvarna.sit.cloud.exception.AuthenticationException;
import bg.tuvarna.sit.cloud.exception.ConfigurationLoadException;
import bg.tuvarna.sit.cloud.utils.ConfigurationUtil;
import bg.tuvarna.sit.cloud.utils.EnvVar;
import bg.tuvarna.sit.cloud.utils.Slf4jLoggingUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import lombok.extern.slf4j.Slf4j;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;

@Slf4j
public class AwsBasicCredentialsProvider extends BaseCredentialsProvider<AwsBasicCredentials> {

  private final ObjectMapper jsonMapper;
  private final ObjectMapper yamlMapper;
  private final Slf4jLoggingUtil loggingUtil;

  @Inject
  public AwsBasicCredentialsProvider(ConfigurationUtil config,
                                     Slf4jLoggingUtil loggingUtil,
                                     @Named("jsonMapper") ObjectMapper jsonMapper,
                                     @Named("yamlMapper") ObjectMapper yamlMapper) {
    super(config);
    this.jsonMapper = jsonMapper;
    this.yamlMapper = yamlMapper;
    this.loggingUtil = loggingUtil;
  }

  @Override
  protected String getAuthenticationConfigurationPath() {

    return "src/main/resources/cloud/%s/authentication.yml".formatted(EnvVar.AWS_PROFILE.getValue());
  }

  @Override
  public CloudCredentials<AwsBasicCredentials> getCredentials() {

    String authenticationConfigurationPath = getAuthenticationConfigurationPath();

    log.info("Loading authentication configuration from: {}", authenticationConfigurationPath);
    AuthenticationConfig authConfig;
    try {
      authConfig = config.load(authenticationConfigurationPath, AuthenticationConfig.class);
    } catch (ConfigurationLoadException e) {
      loggingUtil.logError(log, ErrorCode.AUTH_CONFIG_LOAD_ERROR, e);
      throw e;
    }

    try {
      return authenticate(authConfig);
    } catch (AuthenticationException e) {
      loggingUtil.logError(log, ErrorCode.AUTH_ERROR, e);
      throw e;
    }
  }

  public AwsCredentials authenticate(AuthenticationConfig config) {

    for (AuthenticationConfig.ProviderConfig provider : config.getProviders()) {

      try {

        // Vault authentication
        if (provider.getVault() != null) {
          AuthenticationConfig.VaultConfig vault = provider.getVault();

          if (vault.getToken() != null) {
            String resolvedToken = resolveEnvPlaceholders(vault.getToken());
            vault.setToken(resolvedToken);

            log.info("Authenticating using Vault (host={}, port={}, secretPath={})",
                vault.getHost(), vault.getPort(), vault.getSecretPath());

            VaultAwsCredentialsProvider vaultProvider =
                new VaultAwsCredentialsProvider(new VaultClient(vault, jsonMapper), yamlMapper);

            return vaultProvider.fetchCredentials();
          }
        }

        // Static credentials authentication
        if (provider.getStaticCredentials() != null) {
          AuthenticationConfig.StaticConfig staticCfg = provider.getStaticCredentials();
          String accessKeyId = resolveEnvPlaceholders(staticCfg.getAccessKeyId());
          String secretAccessKey = resolveEnvPlaceholders(staticCfg.getSecretAccessKey());
          if (!accessKeyId.isBlank() && !secretAccessKey.isBlank()) {
            log.info("Authenticating using static credentials");
            return new AwsCredentials(accessKeyId, secretAccessKey);
          }
        }

      } catch (AuthenticationException e) {
        log.warn("Authentication attempt failed using available configuration: {}", e.getMessage());
      }
    }

    throw new AuthenticationException("No authentication method succeeded");
  }
}
