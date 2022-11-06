package bg.tu_varna.sit.task3;

class Sheep extends Animal {

  Sheep(int energy) {
    super(energy);
  }

  @Override
  void sleep() {

    setEnergy(getEnergy() + 5);
  }

  @Override
  void eat() {

    setEnergy(getEnergy() + 10);
  }

  @Override
  public void moving() {

    setEnergy(getEnergy() - 25);
  }
}
