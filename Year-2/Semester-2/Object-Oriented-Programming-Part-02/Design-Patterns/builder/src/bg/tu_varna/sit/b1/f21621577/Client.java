package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.base.Builder;
import bg.tu_varna.sit.b1.f21621577.base.Director;
import bg.tu_varna.sit.b1.f21621577.base.Vehicle;
import bg.tu_varna.sit.b1.f21621577.entities.motorcycle.MotorcycleBuilder;
import bg.tu_varna.sit.b1.f21621577.entities.car.CarBuilder;
import bg.tu_varna.sit.b1.f21621577.entities.car.CarDirector;
import bg.tu_varna.sit.b1.f21621577.entities.motorcycle.MotorcycleDirector;

public class Client {

  public static void main(String[] args) {

    Builder builder = new CarBuilder();
    Director director = new CarDirector();
    Vehicle vehicle = director.instruct(builder);
    vehicle.showProduct();

    builder = new MotorcycleBuilder();
    director = new MotorcycleDirector();
    vehicle = director.instruct(builder);
    vehicle.showProduct();
  }
}
