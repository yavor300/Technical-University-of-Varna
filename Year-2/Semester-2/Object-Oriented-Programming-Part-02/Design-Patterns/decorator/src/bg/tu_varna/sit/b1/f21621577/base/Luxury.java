package bg.tu_varna.sit.b1.f21621577.base;

public abstract class Luxury extends Home {

  private final Home home;
  private double luxuryCost;

  protected Luxury(Home home) {

    this.home = home;
  }

  protected void setLuxuryCost(double luxuryCost) {

    this.luxuryCost = luxuryCost;
  }

  protected double getLuxuryCost() {

    return luxuryCost;
  }

  protected Home getHome() {

    return home;
  }
}
