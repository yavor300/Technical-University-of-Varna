package bg.tuvarna.sit.cloud.credentials.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class VaultResponse implements Cloneable {

  @JsonValue
  private VaultDataWrapper data = new VaultDataWrapper();

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
