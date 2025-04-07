package bg.tuvarna.sit.cloud.credentials.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class VaultResponse implements Cloneable {

  private VaultDataWrapper data;

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
