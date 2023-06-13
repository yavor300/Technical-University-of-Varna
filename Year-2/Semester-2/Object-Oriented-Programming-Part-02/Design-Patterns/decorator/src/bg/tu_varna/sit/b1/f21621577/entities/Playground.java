package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.Home;
import bg.tu_varna.sit.b1.f21621577.base.Luxury;

public class Playground extends Luxury {

  public Playground(Home home) {

    super(home);
    setLuxuryCost(20000);
    System.out.println("For a playground, you pay an  extra $" + getLuxuryCost());
  }

  @Override
  public double getPrice() {

    return getHome().getPrice() + getLuxuryCost();
  }
}
