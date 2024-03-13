package bg.tuvarna.sit.models.entities;

public class Player implements Comparable<Player> {

  private long fideId;
  private String firstName;
  private String familyName;

  private int elo;

  public Player(String firstName, String familyName, int elo) {
    this.firstName = firstName;
    this.familyName = familyName;
    this.elo = elo;
  }

  public long getFideId() {
    return fideId;
  }

  public void setFideId(long fideId) {
    this.fideId = fideId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getFamilyName() {
    return familyName;
  }

  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }

  public int getElo() {
    return elo;
  }

  public void setElo(int elo) {
    this.elo = elo;
  }

  @Override
  public int compareTo(Player o) {

    return o.getElo() - this.getElo();
  }
}
