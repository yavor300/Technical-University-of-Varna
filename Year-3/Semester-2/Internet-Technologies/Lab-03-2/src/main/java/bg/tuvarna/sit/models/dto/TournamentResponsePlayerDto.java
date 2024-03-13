package bg.tuvarna.sit.models.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "player")
public class TournamentResponsePlayerDto {

  @XmlElement(name = "fideId")
  private String fideId;

  @XmlElement(name = "firstName")
  private String firstName;

  @XmlElement(name = "lastName")
  private String lastName;

  @XmlElement(name = "elo")
  private String elo;

  public TournamentResponsePlayerDto() {
  }

  public TournamentResponsePlayerDto(String fideId, String firstName, String lastName, String elo) {
    this.fideId = fideId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.elo = elo;
  }
}
