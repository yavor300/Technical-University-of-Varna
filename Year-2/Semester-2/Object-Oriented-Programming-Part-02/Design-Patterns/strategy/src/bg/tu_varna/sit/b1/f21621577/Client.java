package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.entities.FlyBehavior;
import bg.tu_varna.sit.b1.f21621577.entities.InitialBehavior;
import bg.tu_varna.sit.b1.f21621577.entities.Vehicle;
import bg.tu_varna.sit.b1.f21621577.entities.VehicleSupervisor;

public class Client {

  public static void main(String[] args) {

    Vehicle vehicle = new Vehicle("airplane");
    VehicleSupervisor vehicleSupervisor = new VehicleSupervisor(new InitialBehavior());
    vehicleSupervisor.displayDetail(vehicle);
    vehicleSupervisor.setVehicleBehavior(new FlyBehavior());
    vehicleSupervisor.displayDetail(vehicle);
  }
}
