package bg.tuvarna.sit.cloud.credentials.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class VaultDataWrapper {

    private VaultAwsCredentialsData data;

}
