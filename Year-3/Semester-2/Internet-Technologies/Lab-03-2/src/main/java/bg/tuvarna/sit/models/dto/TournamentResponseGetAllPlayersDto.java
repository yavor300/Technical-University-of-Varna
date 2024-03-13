package bg.tuvarna.sit.models.dto;

import java.util.Collection;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "players")
@XmlAccessorType(XmlAccessType.FIELD)
public class TournamentResponseGetAllPlayersDto {

  @XmlElement(name = "player")
  private Collection<TournamentResponsePlayerDto> players;

  public TournamentResponseGetAllPlayersDto() {
  }

  public TournamentResponseGetAllPlayersDto(Collection<TournamentResponsePlayerDto> players) {
    this.players = players;
  }
}
