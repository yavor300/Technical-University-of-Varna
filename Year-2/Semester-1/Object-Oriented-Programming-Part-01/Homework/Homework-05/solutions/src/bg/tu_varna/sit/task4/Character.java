package bg.tu_varna.sit.task4;

abstract class Character {

  private String name;
  private final double life;
  private double currentLife;

  protected Character(String name, double life) {
    this.name = name;
    this.life = life;
    this.currentLife = life;
  }

  abstract int striking();

  abstract void defense(int takenDamage);

  public double getLife() {
    return life;
  }

  public double getCurrentLife() {
    return currentLife;
  }

  public void setCurrentLife(double currentLife) {
    this.currentLife = currentLife;
  }
}
