package bg.tuvarna.sit.models.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "animal")
@XmlAccessorType(XmlAccessType.FIELD)
public class AnimalRequestAddDto {

  @XmlElement(name = "type")
  private String type;

  @XmlElement(name = "name")
  private String name;

  @XmlElement(name = "birthYear")
  private Integer birthYear;

  public AnimalRequestAddDto() {
  }

  public String getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public Integer getBirthYear() {
    return birthYear;
  }
}
