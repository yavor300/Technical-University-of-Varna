package bg.tuvarna.sit.cloud.credentials.provider.vault;

import bg.tuvarna.sit.cloud.config.AuthenticationConfig;
import bg.tuvarna.sit.cloud.credentials.model.VaultResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.fluent.Executor;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.util.Timeout;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static bg.tuvarna.sit.cloud.credentials.constants.VaultErrorMessages.INVALID_URI;
import static bg.tuvarna.sit.cloud.credentials.constants.VaultErrorMessages.MISSING_TOKEN;

@Slf4j
public class VaultClient {

  private static final String VAULT_TOKEN_HEADER = "X-Vault-Token";

  private final URI vaultUri;
  private final String token;
  private final ObjectMapper mapper;

  public VaultClient(AuthenticationConfig.VaultConfig config, ObjectMapper mapper) {
    this.vaultUri = buildVaultUri(config);
    this.token = extractToken(config);
    this.mapper = mapper;
  }

  public VaultResponse getVaultSecrets() throws IOException {

    RequestConfig requestConfig = RequestConfig.custom()
        .setConnectTimeout(Timeout.ofSeconds(1))
        .setResponseTimeout(Timeout.ofSeconds(1))
        .build();

    try (CloseableHttpClient httpClient = HttpClients.custom()
        .setDefaultRequestConfig(requestConfig)
        .build()) {

      Executor executor = Executor.newInstance(httpClient);

      String json = executor.execute(
          Request.get(vaultUri)
              .addHeader(VAULT_TOKEN_HEADER, token)
      ).returnContent().asString();
      log.debug("Received response from Vault");

      return mapper.readValue(json, VaultResponse.class);
    }
  }

  private URI buildVaultUri(AuthenticationConfig.VaultConfig config) {

    try {
      return new URI(
          config.getScheme(),
          null,
          config.getHost(),
          config.getPort(),
          config.getSecretPath(),
          null,
          null
      );
    } catch (URISyntaxException e) {
      log.error("Failed to fetch credentials from Vault at {}", vaultUri, e);
      throw new VaultAuthenticationException(INVALID_URI, e);
    }
  }

  private String extractToken(AuthenticationConfig.VaultConfig config) {

    String token = config.getToken();

    if (token == null || token.isBlank()) {
      log.warn(MISSING_TOKEN);
    }

    return token;
  }
}
