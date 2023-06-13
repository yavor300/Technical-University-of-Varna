package bg.tu_varna.sit.b1.f21621577.entities.motorcycle;

import bg.tu_varna.sit.b1.f21621577.base.Vehicle;

class Motorcycle extends Vehicle {

  private final String brandName;

  Motorcycle(String brandName) {

    this.brandName = brandName;
    System.out.println("\n We are about to make a " + brandName + " motorcycle.");
  }

  @Override
  protected String getBrandName() {

    return brandName;
  }
}
