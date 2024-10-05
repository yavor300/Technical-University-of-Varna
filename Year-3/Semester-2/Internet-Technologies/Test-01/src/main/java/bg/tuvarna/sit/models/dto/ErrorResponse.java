package bg.tuvarna.sit.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class ErrorResponse {

  @XmlElement(name = "error")
  private String errorMessage;

  public ErrorResponse() {
  }

  public ErrorResponse(String errorMessage) {
    this.errorMessage = errorMessage;
  }
}
