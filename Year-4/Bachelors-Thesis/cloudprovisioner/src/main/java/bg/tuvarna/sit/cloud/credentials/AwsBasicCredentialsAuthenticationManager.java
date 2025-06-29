package bg.tuvarna.sit.cloud.credentials;

import bg.tuvarna.sit.cloud.core.provisioner.model.ErrorCode;
import bg.tuvarna.sit.cloud.credentials.provider.vault.VaultAwsCredentialsProvider;
import bg.tuvarna.sit.cloud.exception.AuthenticationException;
import bg.tuvarna.sit.cloud.exception.ConfigurationLoadException;
import bg.tuvarna.sit.cloud.utils.ConfigurationUtil;
import bg.tuvarna.sit.cloud.utils.EnvVar;
import bg.tuvarna.sit.cloud.utils.Slf4jLoggingUtil;

import jakarta.inject.Inject;

import jakarta.inject.Singleton;

import lombok.extern.slf4j.Slf4j;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;

@Slf4j
@Singleton
public class AwsBasicCredentialsAuthenticationManager extends BaseAuthenticationManager<AwsBasicCredentials> {

  private final VaultAwsCredentialsProvider vaultAwsCredentialsProvider;
  private final Slf4jLoggingUtil loggingUtil;

  @Inject
  public AwsBasicCredentialsAuthenticationManager(ConfigurationUtil config,
                                                  VaultAwsCredentialsProvider vaultAwsCredentialsProvider,
                                                  Slf4jLoggingUtil loggingUtil) {
    super(config);
    this.vaultAwsCredentialsProvider = vaultAwsCredentialsProvider;
    this.loggingUtil = loggingUtil;
  }

  @Override
  protected String getAuthenticationConfigurationPath() {

    return "src/main/resources/cloud/%s/authentication.yml".formatted(EnvVar.AWS_PROFILE.getValue());
  }

  @Override
  public AwsBasicCredentials getCredentials() {

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

  public AwsBasicCredentials authenticate(AuthenticationConfig config) {

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

            return vaultAwsCredentialsProvider.config(vault).fetchCredentials();
          }
        }

        // Static credentials authentication
        if (provider.getStaticCredentials() != null) {
          AuthenticationConfig.StaticConfig staticCfg = provider.getStaticCredentials();
          String accessKeyId = resolveEnvPlaceholders(staticCfg.getAccessKeyId());
          String secretAccessKey = resolveEnvPlaceholders(staticCfg.getSecretAccessKey());
          if (!accessKeyId.isBlank() && !secretAccessKey.isBlank()) {
            log.info("Authenticating using static credentials");
            return AwsBasicCredentials.create(accessKeyId, secretAccessKey);
          }
        }

        // Profile authentication
        if (provider.getProfileCredentials() != null) {
          AuthenticationConfig.ProfileConfig profileCfg = provider.getProfileCredentials();
          String profileName = resolveEnvPlaceholders(profileCfg.getProfileName());
          if (!profileName.isBlank()) {
            try (ProfileCredentialsProvider profileCredentials = ProfileCredentialsProvider.create(profileName)) {
              log.info("Authenticating using AWS profile '{}'", profileName);
              AwsCredentials credentials = profileCredentials.resolveCredentials();
              return AwsBasicCredentials.create(credentials.accessKeyId(), credentials.secretAccessKey());
            }
          }
        }

      } catch (AuthenticationException e) {
        log.warn("Authentication attempt failed using available configuration: {}", e.getMessage());
      }
    }

    throw new AuthenticationException("No authentication method succeeded");
  }

}
