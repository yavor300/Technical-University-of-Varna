package bg.tu_varna.sit.task4;

class Attacker extends Character {

  private final int efficiency;

  Attacker(String name, int efficiency) {
    super(name, 250);
    this.efficiency = efficiency;
  }

  @Override
  int striking() {

    return efficiency;
  }

  @Override
  void defense(int takenDamage) {

    setCurrentLife(getCurrentLife() - takenDamage);

    if (getCurrentLife() < 0) {
      setCurrentLife(0);
    }
  }
}
