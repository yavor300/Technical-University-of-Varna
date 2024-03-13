package bg.tuvarna.sit.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "player")
@XmlAccessorType(XmlAccessType.FIELD)
public class TournamentRequestAddPlayerDto {

  @XmlElement(name = "firstName")
  private String firstName;

  @XmlElement(name = "lastName")
  private String lastName;

  @XmlElement(name = "elo")
  private Integer elo;

  public TournamentRequestAddPlayerDto() {
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public Integer getElo() {
    return elo;
  }
}
