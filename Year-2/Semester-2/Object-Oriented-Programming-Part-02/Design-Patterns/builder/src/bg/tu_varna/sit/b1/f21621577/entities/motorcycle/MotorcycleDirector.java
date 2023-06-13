package bg.tu_varna.sit.b1.f21621577.entities.motorcycle;

import bg.tu_varna.sit.b1.f21621577.base.Builder;
import bg.tu_varna.sit.b1.f21621577.base.Director;
import bg.tu_varna.sit.b1.f21621577.base.Vehicle;

public class MotorcycleDirector implements Director {

  @Override
  public Vehicle instruct(Builder builder) {

    builder.addBrandName();
    builder.buildBody();
    builder.insertWheels();

    return builder.getVehicle();
  }
}
