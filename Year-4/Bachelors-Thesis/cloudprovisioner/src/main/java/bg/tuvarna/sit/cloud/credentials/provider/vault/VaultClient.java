package bg.tuvarna.sit.cloud.credentials.provider.vault;

import bg.tuvarna.sit.cloud.credentials.AuthenticationConfig;
import bg.tuvarna.sit.cloud.credentials.model.VaultResponse;

import bg.tuvarna.sit.cloud.utils.NamedInjections;
import bg.tuvarna.sit.cloud.utils.Slf4jLoggingUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

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
@Singleton
public class VaultClient {

  private static final String VAULT_TOKEN_HEADER = "X-Vault-Token";

  private final ObjectMapper mapper;

  @Inject
  public VaultClient(@Named(NamedInjections.JSON_MAPPER) ObjectMapper mapper) {
    this.mapper = mapper;
  }

  public VaultResponse getSecrets(AuthenticationConfig.VaultConfig config) {

    RequestConfig requestConfig = RequestConfig.custom()
        .setConnectTimeout(Timeout.ofSeconds(1))
        .setResponseTimeout(Timeout.ofSeconds(1))
        .build();

    URI vaultUri = buildUri(config);

    try (CloseableHttpClient httpClient = HttpClients.custom()
        .setDefaultRequestConfig(requestConfig)
        .build()) {

      Executor executor = Executor.newInstance(httpClient);

      String json = executor.execute(
          Request.get(vaultUri)
              .addHeader(VAULT_TOKEN_HEADER, extractToken(config))
      ).returnContent().asString();

      return mapper.readValue(json, VaultResponse.class);

    } catch (IOException e) {

      String message = ("Failed to retrieve secrets from Vault at URI: %s."
          + "Check connectivity, Vault availability, or authentication token.").formatted(vaultUri);
      log.debug(Slf4jLoggingUtil.DEBUG_PREFIX + "{}", message, e);
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
      log.debug(Slf4jLoggingUtil.DEBUG_PREFIX + "{}", message, e);
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
