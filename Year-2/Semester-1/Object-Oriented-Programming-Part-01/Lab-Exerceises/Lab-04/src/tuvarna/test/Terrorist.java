package tuvarna.test;

public class Terrorist extends Player {

  private int aggressiveness;

  public Terrorist(String username, int health, int armor, Gun gun, int aggressiveness) {
    super(username, health, armor, gun);
    this.aggressiveness = aggressiveness;
  }

  public void setAggressiveness(int aggressiveness) {
    if (this.aggressiveness == aggressiveness + 1000) {
      super.setArmor(super.getArmor() + 1);
    }
    this.aggressiveness = aggressiveness;
  }
}
