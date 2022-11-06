package bg.tu_varna.sit.task3;

abstract class Animal implements Move {

  private int energy;

  protected Animal(int energy) {
    this.energy = energy;
  }

  abstract void sleep();

  abstract void eat();

  protected int getEnergy() {
    return energy;
  }

  protected void setEnergy(int energy) {
    this.energy = energy;
  }
}
