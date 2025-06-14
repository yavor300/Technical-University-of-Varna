package bg.tuvarna.sit.cloud.exception;

public class ConfigurationLoadException extends RuntimeException {

  public ConfigurationLoadException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConfigurationLoadException(String message) {
    super(message);
  }
}
