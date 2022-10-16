package tuvarna.exercise03;

public class Character {

  private String name;
  private int life;

  protected Character(String name, int life) {
    this.name = name;
    this.life = life;
  }

  @Override
  public String toString() {
    return String.format("Character: %s%nName: %s%nLife: %d",
            getClass().getSimpleName(), getName(), getLife());
  }

  protected String getName() {
    return name;
  }

  protected void setName(String name) {
    this.name = name;
  }

  protected int getLife() {
    return life;
  }

  protected void setLife(int life) {
    this.life = life;
  }
}
