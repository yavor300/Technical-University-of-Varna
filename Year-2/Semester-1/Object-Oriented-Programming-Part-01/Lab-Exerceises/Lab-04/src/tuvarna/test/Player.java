package tuvarna.test;

public class Player {

  private String username;
  private int health;
  private int armor;
  private Gun gun;

  protected Player(String username, int health, int armor, Gun gun) {
    this.username = username;
    this.health = health;
    this.armor = armor;
    this.gun = gun;
  }

  protected void takeDamage(int damage) {
    if (armor - damage < 0) {
      armor = 0;
      health -= damage - armor;
    } else {
      armor -= damage;
    }
  }

  protected boolean isAlive() {
    return health > 0;
  }

  protected int getArmor() {
    return armor;
  }

  protected void setArmor(int armor) {
    this.armor = armor;
  }

  protected Gun getGun() {
    return gun;
  }
}
