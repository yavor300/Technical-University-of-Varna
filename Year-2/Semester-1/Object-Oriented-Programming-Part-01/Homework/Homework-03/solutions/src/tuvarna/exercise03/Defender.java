package tuvarna.exercise03;

public class Defender extends Character {

  private String defendingSkill;
  private int armor;
  private String healthSkill;

  protected Defender(String name, String defendingSkill, int armor, String healthSkill) {
    super(name, 250);
    this.defendingSkill = defendingSkill;
    this.armor = armor;
    this.healthSkill = healthSkill;
  }

  @Override
  public String toString() {
    return String.format("%s%nDefending skill: %s%nArmor: %d%nHealth skill: %s",
            super.toString(), getDefendingSkill(), getArmor(), getHealthSkill());
  }

  public String getDefendingSkill() {
    return defendingSkill;
  }

  public void setDefendingSkill(String defendingSkill) {
    this.defendingSkill = defendingSkill;
  }

  public int getArmor() {
    return armor;
  }

  public void setArmor(int armor) {
    this.armor = armor;
  }

  public String getHealthSkill() {
    return healthSkill;
  }

  public void setHealthSkill(String healthSkill) {
    this.healthSkill = healthSkill;
  }
}
