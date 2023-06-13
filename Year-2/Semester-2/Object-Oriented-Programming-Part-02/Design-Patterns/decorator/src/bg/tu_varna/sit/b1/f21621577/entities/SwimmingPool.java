package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.Home;
import bg.tu_varna.sit.b1.f21621577.base.Luxury;

public class SwimmingPool extends Luxury {

  public SwimmingPool(Home home) {

    super(home);
    setLuxuryCost(5000);
    System.out.println("For a swimming pool, you pay an extra $" + getLuxuryCost());
  }

  @Override
  public double getPrice() {

    return getHome().getPrice() + getLuxuryCost();
  }
}
