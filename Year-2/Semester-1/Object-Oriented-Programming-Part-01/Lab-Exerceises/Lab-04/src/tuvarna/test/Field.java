package tuvarna.test;

public class Field {

  private Terrorist[] terrorists;
  private Antiterrorist[] antiterrorists;

  public Field(Terrorist[] terrorists, Antiterrorist[] antiterrorists) {
    this.terrorists = terrorists;
    this.antiterrorists = antiterrorists;
  }

  public void start() {

    int iteration = 0;

    while (areAllAntiTerroristsDead() || areAllTerroristsDead()) {
      iteration++;
      if (iteration % 2 != 0) {
        for (int i = 0; i < terrorists.length; i++) {
          antiterrorists[i].takeDamage(terrorists[i].getGun().shot());
        }
      } else {
        for (int i = 0; i < antiterrorists.length; i++) {
          terrorists[i].takeDamage(antiterrorists[i].getGun().shot());
        }
      }
    }

    if (areAllAntiTerroristsDead()) {
      System.out.println("Терорисите печелят!");
    } else {
      System.out.println("Антитерорисите печелят!");
    }
  }

  private boolean areAllTerroristsDead() {

    boolean result = true;

    for (Terrorist terrorist : terrorists) {
      if (terrorist.isAlive()) {
        result = false;
        break;
      }
    }

    return result;
  }

  private boolean areAllAntiTerroristsDead() {

    boolean result = true;

    for (Antiterrorist antiterrorist : antiterrorists) {
      if (antiterrorist.isAlive()) {
        result = false;
        break;
      }
    }

    return result;
  }
}
