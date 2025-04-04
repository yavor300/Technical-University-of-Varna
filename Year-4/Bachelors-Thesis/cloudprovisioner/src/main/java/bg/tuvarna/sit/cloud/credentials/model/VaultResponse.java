package bg.tuvarna.sit.cloud.credentials.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class VaultResponse {

  private VaultDataWrapper data;

  @Override
  public String toString() {

    ObjectMapper mapper = new ObjectMapper();

    try {

      VaultResponse copy = mapper.readValue(mapper.writeValueAsString(this), VaultResponse.class);
      VaultAwsCredentialsData credentials = copy.getData().getData();

      if (credentials != null) {
        credentials.setAccessKeyId("****");
        credentials.setSecretAccessKey("****");
      }

      return mapper.writeValueAsString(copy);

    } catch (JsonProcessingException e) {
      return "{ \"error\": \"Failed to serialize VaultResponse\" }";
    }
  }

}
