package bg.tu_varna.sit.b1.f21621577.entities.car;

import bg.tu_varna.sit.b1.f21621577.base.Builder;
import bg.tu_varna.sit.b1.f21621577.base.Director;
import bg.tu_varna.sit.b1.f21621577.base.Vehicle;

public class CarDirector implements Director {

  @Override
  public Vehicle instruct(Builder builder) {

    builder.buildBody();
    builder.insertWheels();
    builder.addBrandName();

    return builder.getVehicle();
  }
}
