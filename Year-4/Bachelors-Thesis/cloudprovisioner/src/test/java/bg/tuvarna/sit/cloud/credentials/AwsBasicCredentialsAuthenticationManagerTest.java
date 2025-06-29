package bg.tuvarna.sit.cloud.credentials;

import bg.tuvarna.sit.cloud.core.provisioner.model.ErrorCode;
import bg.tuvarna.sit.cloud.credentials.provider.vault.VaultAwsCredentialsProvider;
import bg.tuvarna.sit.cloud.exception.AuthenticationException;
import bg.tuvarna.sit.cloud.exception.ConfigurationLoadException;
import bg.tuvarna.sit.cloud.utils.ConfigurationUtil;
import bg.tuvarna.sit.cloud.utils.Slf4jLoggingUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AwsBasicCredentialsAuthenticationManagerTest {

  @Mock
  private ConfigurationUtil config;

  @Mock
  private VaultAwsCredentialsProvider vaultProvider;

  @Mock
  private Slf4jLoggingUtil loggingUtil;

  @InjectMocks
  private AwsBasicCredentialsAuthenticationManager manager;

  private static final AwsBasicCredentials EXPECTED_CREDS =
      AwsBasicCredentials.create("accessKey", "secretKey");

  private AuthenticationConfig authConfig;

  @BeforeEach
  void setup() {
    manager = new AwsBasicCredentialsAuthenticationManager(config, vaultProvider, loggingUtil);
  }

  @Test
  void authenticate_shouldUseVault_whenVaultTokenPresent() {
    AuthenticationConfig.VaultConfig vaultCfg = new AuthenticationConfig.VaultConfig();
    vaultCfg.setToken("TOKEN");
    vaultCfg.setHost("localhost");
    vaultCfg.setPort(8200);
    vaultCfg.setSecretPath("/secret");

    AuthenticationConfig.ProviderConfig provider = new AuthenticationConfig.ProviderConfig();
    provider.setVault(vaultCfg);

    authConfig = new AuthenticationConfig();
    authConfig.setProviders(List.of(provider));

    when(vaultProvider.config(vaultCfg)).thenReturn(vaultProvider);
    when(vaultProvider.fetchCredentials()).thenReturn(EXPECTED_CREDS);

    AwsBasicCredentials result = manager.authenticate(authConfig);

    assertThat(result.accessKeyId()).isEqualTo("accessKey");
    assertThat(result.secretAccessKey()).isEqualTo("secretKey");
  }

  @Test
  void authenticate_shouldUseStaticCredentials_whenConfigured() {
    AuthenticationConfig.StaticConfig staticCfg = new AuthenticationConfig.StaticConfig();
    staticCfg.setAccessKeyId("accessKey");
    staticCfg.setSecretAccessKey("secretKey");

    AuthenticationConfig.ProviderConfig provider = new AuthenticationConfig.ProviderConfig();
    provider.setStaticCredentials(staticCfg);

    authConfig = new AuthenticationConfig();
    authConfig.setProviders(List.of(provider));

    AwsBasicCredentials result = manager.authenticate(authConfig);

    assertThat(result.accessKeyId()).isEqualTo("accessKey");
    assertThat(result.secretAccessKey()).isEqualTo("secretKey");
  }

  @Test
  void authenticate_shouldUseProfile_whenConfigured() {
    AuthenticationConfig.ProfileConfig profileCfg = new AuthenticationConfig.ProfileConfig();
    profileCfg.setProfileName("default");

    AuthenticationConfig.ProviderConfig provider = new AuthenticationConfig.ProviderConfig();
    provider.setProfileCredentials(profileCfg);

    AuthenticationConfig authConfig = new AuthenticationConfig();
    authConfig.setProviders(List.of(provider));

    AwsCredentials mockCreds = AwsBasicCredentials.create("accessKey", "secretKey");

    try (MockedStatic<ProfileCredentialsProvider> mocked = mockStatic(ProfileCredentialsProvider.class)) {
      ProfileCredentialsProvider mockProvider = mock(ProfileCredentialsProvider.class);
      mocked.when(() -> ProfileCredentialsProvider.create("default")).thenReturn(mockProvider);
      when(mockProvider.resolveCredentials()).thenReturn(mockCreds);

      AwsBasicCredentials result = manager.authenticate(authConfig);

      assertThat(result).isNotNull();
      assertThat(result.accessKeyId()).isEqualTo("accessKey");
      assertThat(result.secretAccessKey()).isEqualTo("secretKey");

      verify(mockProvider).resolveCredentials();
    }
  }


  @Test
  void authenticate_shouldThrow_whenNoValidProvider() {
    AuthenticationConfig.ProviderConfig provider = new AuthenticationConfig.ProviderConfig();
    authConfig = new AuthenticationConfig();
    authConfig.setProviders(List.of(provider));

    assertThatThrownBy(() -> manager.authenticate(authConfig))
        .isInstanceOf(AuthenticationException.class)
        .hasMessageContaining("No authentication method succeeded");
  }

  @Test
  void getCredentials_shouldReturnFromConfigFile_whenValid() {
    AuthenticationConfig configFile = new AuthenticationConfig();
    AuthenticationConfig.StaticConfig staticCfg = new AuthenticationConfig.StaticConfig();
    staticCfg.setAccessKeyId("accessKey");
    staticCfg.setSecretAccessKey("secretKey");

    AuthenticationConfig.ProviderConfig provider = new AuthenticationConfig.ProviderConfig();
    provider.setStaticCredentials(staticCfg);
    configFile.setProviders(List.of(provider));

    when(config.load(anyString(), eq(AuthenticationConfig.class))).thenReturn(configFile);

    AwsBasicCredentials creds = manager.getCredentials();
    assertThat(creds.accessKeyId()).isEqualTo("accessKey");
    assertThat(creds.secretAccessKey()).isEqualTo("secretKey");
  }

  @Test
  void getCredentials_shouldLogAndThrow_whenConfigLoadFails() {
    ConfigurationLoadException cause = new ConfigurationLoadException("error");
    when(config.load(anyString(), eq(AuthenticationConfig.class))).thenThrow(cause);

    assertThatThrownBy(() -> manager.getCredentials())
        .isInstanceOf(ConfigurationLoadException.class);

    verify(loggingUtil).logError(any(), eq(ErrorCode.AUTH_CONFIG_LOAD_ERROR), eq(cause));
  }

  @Test
  void getCredentials_shouldLogAndThrow_whenAuthenticationFails() {
    AuthenticationConfig configFile = new AuthenticationConfig();
    AuthenticationConfig.ProviderConfig provider = new AuthenticationConfig.ProviderConfig();
    configFile.setProviders(List.of(provider));

    when(config.load(anyString(), eq(AuthenticationConfig.class))).thenReturn(configFile);

    assertThatThrownBy(() -> manager.getCredentials())
        .isInstanceOf(AuthenticationException.class)
        .hasMessageContaining("No authentication method succeeded");

    verify(loggingUtil).logError(any(), eq(ErrorCode.AUTH_ERROR), any(AuthenticationException.class));
  }
}
