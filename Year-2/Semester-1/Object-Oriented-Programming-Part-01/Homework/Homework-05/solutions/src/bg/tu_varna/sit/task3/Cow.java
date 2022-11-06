package bg.tu_varna.sit.task3;

class Cow extends Animal {

  Cow(int energy) {
    super(energy);
  }

  @Override
  void sleep() {

    setEnergy(getEnergy() + 10);
  }

  @Override
  void eat() {

    setEnergy(getEnergy() + 5);
  }

  @Override
  public void moving() {

    setEnergy(getEnergy() - 20);
  }
}
