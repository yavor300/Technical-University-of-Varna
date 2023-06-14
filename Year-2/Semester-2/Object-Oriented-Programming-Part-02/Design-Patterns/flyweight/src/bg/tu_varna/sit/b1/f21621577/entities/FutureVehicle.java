package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.Vehicle;

class FutureVehicle implements Vehicle {

  private final String description;

  FutureVehicle(String description) {
    this.description = description;
  }

  @Override
  public void aboutMe(String color) {

    System.out.println(description + " with " + "a blue (default) color.");
  }
}
