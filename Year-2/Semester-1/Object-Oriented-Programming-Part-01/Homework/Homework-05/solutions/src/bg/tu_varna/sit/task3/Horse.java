package bg.tu_varna.sit.task3;

class Horse extends Animal {

  Horse(int energy) {
    super(energy);
  }

  @Override
  void sleep() {

    setEnergy(getEnergy() + 7);
  }

  @Override
  void eat() {

    setEnergy(getEnergy() + 15);
  }

  @Override
  public void moving() {

    setEnergy(getEnergy() - 15);
  }
}
