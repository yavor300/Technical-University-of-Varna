package bg.tuvarna.sit.cloud.credentials.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VaultAwsCredentialsData {

  @JsonProperty("AWS_ACCESS_KEY_ID")
  private String accessKeyId;

  @JsonProperty("AWS_SECRET_ACCESS_KEY")
  private String secretAccessKey;
}
