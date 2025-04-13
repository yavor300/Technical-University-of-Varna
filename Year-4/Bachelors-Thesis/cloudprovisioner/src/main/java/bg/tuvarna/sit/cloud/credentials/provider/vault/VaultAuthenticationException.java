package bg.tuvarna.sit.cloud.credentials.provider.vault;

public class VaultAuthenticationException extends RuntimeException {

  public VaultAuthenticationException(String message) {
    super(message);
  }

  public VaultAuthenticationException(String message, Throwable cause) {
    super(message, cause);
  }
}
