package tuvarna.test;

public class Antiterrorist extends Player {

  private int skill;

  public Antiterrorist(String username, int health, int armor, Gun gun, int skill) {
    super(username, health, armor, gun);
    this.skill = skill;
  }

  public void setSkill(int skill) {
    if (this.skill == skill - 2) {
      super.setArmor(super.getArmor() + 1);
    }
    this.skill = skill;
  }
}
