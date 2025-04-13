package bg.tuvarna.sit.cloud.config;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationConfig {

  private VaultConfig vault;

  @Setter
  @Getter
  public static class VaultConfig {

    private String scheme;
    private String host;
    private Integer port;
    private String secretPath;
    private String token;

  }
}
