package bg.tuvarna.sit.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@XmlRootElement(name = "animals")
@XmlAccessorType(XmlAccessType.FIELD)
public class AnimalResponseGetAllDto {

  @XmlElement(name = "animal")
  private Collection<AnimalResponseReadDto> animals;

  public AnimalResponseGetAllDto() {
  }

  public AnimalResponseGetAllDto(Collection<AnimalResponseReadDto> animals) {
    this.animals = animals;
  }
}
