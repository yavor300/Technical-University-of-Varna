package bg.tuvarna.sit.cloud.credentials.provider.vault;

import bg.tuvarna.sit.cloud.credentials.AuthenticationConfig;
import bg.tuvarna.sit.cloud.credentials.model.VaultResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.fluent.Content;
import org.apache.hc.client5.http.fluent.Executor;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Response;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VaultClientTest {

  @Mock
  private ObjectMapper mapper;

  @InjectMocks
  private VaultClient vaultClient;

  @Test
  void getSecrets_shouldReturnSecrets_whenResponseIsValid() throws Exception {

    AuthenticationConfig.VaultConfig config = createValidVaultConfig();
    VaultResponse expectedResponse = new VaultResponse();

    try (MockedStatic<HttpClients> mockedClients = mockStatic(HttpClients.class)) {
      try (MockedStatic<Executor> mockedExecutor = mockStatic(Executor.class)) {
        CloseableHttpClient mockHttpClient = mock(CloseableHttpClient.class);
        HttpClientBuilder mockBuilder = mock(HttpClientBuilder.class);

        mockedClients.when(HttpClients::custom).thenReturn(mockBuilder);
        when(mockBuilder.setDefaultRequestConfig(any())).thenReturn(mockBuilder);
        when(mockBuilder.build()).thenReturn(mockHttpClient);

        Executor executor = mock(Executor.class);
        mockedExecutor.when(() -> Executor.newInstance(mockHttpClient)).thenReturn(executor);

        Response mockResponse = mock(Response.class);
        Content mockContent = mock(Content.class);

        when(mockContent.asString()).thenReturn("{\"mock\":\"secret\"}");

        when(mockResponse.returnContent()).thenReturn(mockContent);

        when(executor.execute(any(Request.class))).thenReturn(mockResponse);
        when(mapper.readValue(anyString(), eq(VaultResponse.class))).thenReturn(expectedResponse);

        VaultResponse response = vaultClient.getSecrets(config);

        assertThat(response).isEqualTo(expectedResponse);
      }
    }
  }

  @Test
  void getSecrets_shouldThrowVaultAuthenticationException_onIOException() throws Exception {
    AuthenticationConfig.VaultConfig config = createValidVaultConfig();

    try (MockedStatic<HttpClients> clients = mockStatic(HttpClients.class)) {
      try (MockedStatic<Executor> executors = mockStatic(Executor.class)) {
        CloseableHttpClient mockHttpClient = mock(CloseableHttpClient.class);
        HttpClientBuilder mockBuilder = mock(HttpClientBuilder.class);

        clients.when(HttpClients::custom).thenReturn(mockBuilder);
        when(mockBuilder.setDefaultRequestConfig(any())).thenReturn(mockBuilder);
        when(mockBuilder.build()).thenReturn(mockHttpClient);

        Executor executor = mock(Executor.class);
        executors.when(() -> Executor.newInstance(mockHttpClient)).thenReturn(executor);

        when(executor.execute(any(Request.class))).thenThrow(new IOException("Simulated error"));

        assertThatThrownBy(() -> vaultClient.getSecrets(config))
            .isInstanceOf(VaultAuthenticationException.class)
            .hasMessageContaining("Failed to retrieve secrets from Vault");
      }
    }
  }

  @Test
  void getSecrets_shouldThrowVaultAuthenticationException_whenTokenIsMissing() {
    AuthenticationConfig.VaultConfig config = createValidVaultConfig();
    config.setToken("  "); // blank

    assertThatThrownBy(() -> vaultClient.getSecrets(config))
        .isInstanceOf(VaultAuthenticationException.class)
        .hasMessageContaining("Vault token is missing in the configuration");
  }

  @Test
  void getSecrets_shouldThrowVaultAuthenticationException_whenUriInvalid() {
    AuthenticationConfig.VaultConfig config = new AuthenticationConfig.VaultConfig();
    config.setScheme("http");
    config.setHost("in valid host"); // space causes URISyntaxException
    config.setPort(8200);
    config.setSecretPath("/v1/secrets");
    config.setToken("token");

    assertThatThrownBy(() -> vaultClient.getSecrets(config))
        .isInstanceOf(VaultAuthenticationException.class)
        .hasMessageContaining("Invalid Vault URI components");
  }

  private AuthenticationConfig.VaultConfig createValidVaultConfig() {
    AuthenticationConfig.VaultConfig config = new AuthenticationConfig.VaultConfig();
    config.setScheme("http");
    config.setHost("localhost");
    config.setPort(8200);
    config.setSecretPath("/v1/application/data/foo");
    config.setToken("s.token");
    return config;
  }
}
