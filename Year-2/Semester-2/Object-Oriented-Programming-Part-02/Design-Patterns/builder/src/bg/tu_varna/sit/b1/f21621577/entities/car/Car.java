package bg.tu_varna.sit.b1.f21621577.entities.car;

import bg.tu_varna.sit.b1.f21621577.base.Vehicle;

class Car extends Vehicle {

  private final String brandName;

  Car(String brandName) {

    this.brandName = brandName;
    System.out.println("\n We are about to make a " + brandName + " car.");
  }

  @Override
  protected String getBrandName() {

    return brandName;
  }
}
