package bg.tuvarna.sit.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "animal")
@XmlAccessorType(XmlAccessType.FIELD)
public class AnimalResponseReadDto {

  @XmlElement(name = "id")
  private String id;

  @XmlElement(name = "type")
  private String type;

  @XmlElement(name = "name")
  private String name;

  @XmlElement(name = "birthYear")
  private String birthYear;

  public AnimalResponseReadDto() {
  }

  public AnimalResponseReadDto(String id, String type, String name, String birthYear) {
    this.id = id;
    this.type = type;
    this.name = name;
    this.birthYear = birthYear;
  }

}
