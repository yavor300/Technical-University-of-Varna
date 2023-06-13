package bg.tu_varna.sit.b1.f21621577.entities.car;

import bg.tu_varna.sit.b1.f21621577.base.Builder;
import bg.tu_varna.sit.b1.f21621577.base.Vehicle;

public class CarBuilder implements Builder {

  private final Car car;

  public CarBuilder() {

    this.car = new Car("Ford");
  }


  @Override
  public void addBrandName() {

   car.add(" Adding the car brand: " + car.getBrandName());
  }

  @Override
  public void buildBody() {

    car.add(" Making the car body.");
  }

  @Override
  public void insertWheels() {

    car.add(" 4 wheels are added to the car.");
  }

  @Override
  public Vehicle getVehicle() {

    return car;
  }
}
