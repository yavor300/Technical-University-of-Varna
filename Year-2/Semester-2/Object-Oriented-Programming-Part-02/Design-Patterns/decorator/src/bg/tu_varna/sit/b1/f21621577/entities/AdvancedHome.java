package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.Home;

public class AdvancedHome extends Home {

  public AdvancedHome() {

    setAdditionalCost(25000);
    System.out.println("It becomes an advanced home with more facilities.");
    System.out.println("You need to pay $" + this.getPrice() + " for this.");
  }

  @Override
  public double getPrice() {

    return getBasePrice() + getAdditionalCost();
  }
}
