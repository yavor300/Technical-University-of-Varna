package bg.tuvarna.sit.cloud.credentials.provider.vault;

import bg.tuvarna.sit.cloud.exception.AuthenticationException;

public class VaultAuthenticationException extends AuthenticationException {

  public VaultAuthenticationException(String message) {
    super(message);
  }

  public VaultAuthenticationException(String message, Throwable cause) {
    super(message, cause);
  }
}
