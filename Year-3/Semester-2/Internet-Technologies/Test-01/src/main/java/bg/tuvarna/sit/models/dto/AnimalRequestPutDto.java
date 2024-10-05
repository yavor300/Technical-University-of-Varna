package bg.tuvarna.sit.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "animal")
@XmlAccessorType(XmlAccessType.FIELD)
public class AnimalRequestPutDto {

  @XmlElement(name = "id")
  private String id;
  @XmlElement(name = "name")
  private String name;

  public AnimalRequestPutDto() {
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

}
