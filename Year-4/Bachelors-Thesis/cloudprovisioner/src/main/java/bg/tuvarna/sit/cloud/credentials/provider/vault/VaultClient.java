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

import static bg.tuvarna.sit.cloud.credentials.constants.VaultErrorMessages.MISSING_TOKEN;

@Slf4j
public class VaultClient {

  private static final String VAULT_TOKEN_HEADER = "X-Vault-Token";

  private final URI vaultUri;
  private final String token;
  private final ObjectMapper mapper;

  public VaultClient(AuthenticationConfig.VaultConfig config, ObjectMapper mapper) {
    this.vaultUri = buildUri(config);
    this.token = extractToken(config);
    this.mapper = mapper;
  }

  public VaultResponse getSecrets() {

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

      return mapper.readValue(json, VaultResponse.class);

    } catch (IOException e) {

      String message = ("Failed to retrieve secrets from Vault at URI: %s."
          + "Check connectivity, Vault availability, or authentication token.").formatted(vaultUri);
      log.debug(message, e);
      throw new VaultAuthenticationException(message, e);
    }
  }

  private URI buildUri(AuthenticationConfig.VaultConfig config) {

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

      String message = "Invalid Vault URI components provided: %s. Failed to construct Vault URI.".formatted(config);
      log.debug(message, e);
      throw new VaultAuthenticationException(message, e);
    }
  }

  private String extractToken(AuthenticationConfig.VaultConfig config) {

    String token = config.getToken();

    if (token == null || token.isBlank()) {

      String message = MISSING_TOKEN.formatted(config);
      log.error(message);
      throw new VaultAuthenticationException(message);
    }

    return token;
  }
}
