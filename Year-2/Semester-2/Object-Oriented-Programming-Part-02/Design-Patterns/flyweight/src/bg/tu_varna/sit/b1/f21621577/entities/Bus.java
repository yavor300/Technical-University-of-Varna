package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.Vehicle;

class Bus implements Vehicle {

  private final String description;

  Bus(String description) {
    this.description = description;
  }

  @Override
  public void aboutMe(String color) {

    System.out.println(description + " with " + color + " color.");
  }
}
