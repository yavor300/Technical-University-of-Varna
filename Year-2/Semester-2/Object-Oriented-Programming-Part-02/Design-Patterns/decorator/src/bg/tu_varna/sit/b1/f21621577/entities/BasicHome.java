package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.Home;

public class BasicHome extends Home {

  public BasicHome() {

    System.out.println("The basic home with some standard facilities is ready.");
    System.out.println("You need to pay $" + this.getPrice() + " for this.");
  }

  @Override
  public double getPrice() {

    return getBasePrice();
  }
}
