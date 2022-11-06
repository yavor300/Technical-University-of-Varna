package bg.tu_varna.sit.task4;

class Defender extends Character {

  private String defensiveSkill;
  private int armor;
  private final int healingSkill;

  Defender(String name, String defensiveSkill, int armor, int healingSkill) {
    super(name, 250);
    this.defensiveSkill = defensiveSkill;
    this.armor = armor;
    this.healingSkill = healingSkill;
  }

  @Override
  int striking() {

    return 10;
  }

  @Override
  void defense(int takenDamage) {

    armor -= takenDamage;

    if (armor <= 0) {

      setCurrentLife(getCurrentLife() - armor);
      if (getCurrentLife() < 0) {
        setCurrentLife(0);
      }
      armor = 0;

      setCurrentLife(0.01 * healingSkill * (getLife() / 100));
    }
  }
}
