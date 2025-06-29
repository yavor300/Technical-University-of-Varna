package bg.tuvarna.sit.cloud.credentials;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
public class AuthenticationConfig {

  private List<ProviderConfig> providers;

  @Setter
  @Getter
  public static class ProviderConfig {

    private VaultConfig vault;
    private StaticConfig staticCredentials;
    private ProfileConfig profileCredentials;
  }

  @ToString(exclude = "token")
  @Setter
  @Getter
  public static class VaultConfig {

    private String scheme;
    private String host;
    private Integer port;
    private String secretPath;
    private String token;
  }

  @Setter
  @Getter
  public static class StaticConfig {

    private String accessKeyId;
    private String secretAccessKey;
  }

  @Setter
  @Getter
  public static class ProfileConfig {

    private String profileName;
  }
}
