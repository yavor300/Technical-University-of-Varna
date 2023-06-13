package bg.tu_varna.sit.b1.f21621577.entities.motorcycle;

import bg.tu_varna.sit.b1.f21621577.base.Builder;
import bg.tu_varna.sit.b1.f21621577.base.Vehicle;

public class MotorcycleBuilder implements Builder {

  private final Motorcycle motorcycle;

  public MotorcycleBuilder() {

    this.motorcycle = new Motorcycle("Honda");
  }

  @Override
  public void addBrandName() {

    motorcycle.add(" Adding the brand name: " + motorcycle.getBrandName());
  }

  @Override
  public void buildBody() {

    motorcycle.add(" Making the body of the motorcycle.");
  }

  @Override
  public void insertWheels() {

    motorcycle.add(" 2 wheels are added to the motorcycle.");
  }

  @Override
  public Vehicle getVehicle() {

    return motorcycle;
  }
}
