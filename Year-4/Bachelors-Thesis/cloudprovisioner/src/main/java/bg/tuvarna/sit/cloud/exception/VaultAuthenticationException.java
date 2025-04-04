package bg.tuvarna.sit.cloud.exception;

public class VaultAuthenticationException extends RuntimeException {

  public VaultAuthenticationException(String message) {
    super(message);
  }

  public VaultAuthenticationException(String message, Throwable cause) {
    super(message, cause);
  }
}
