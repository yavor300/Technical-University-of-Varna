package tuvarna.exercise03;

public class Attacker extends Character {

  private int efficiency;

  public Attacker(String name, int efficiency) {
    super(name, 150);
    this.efficiency = efficiency;
  }

  @Override
  public String toString() {
    return String.format("%s%nEfficiency: %d",
            super.toString(), getEfficiency());
  }

  public int getEfficiency() {
    return efficiency;
  }

  public void setEfficiency(int efficiency) {
    this.efficiency = efficiency;
  }
}
