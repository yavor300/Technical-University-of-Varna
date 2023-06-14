package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.VehicleBehavior;

public class InitialBehavior implements VehicleBehavior {


  @Override
  public void showDetail(Vehicle vehicle) {

    System.out.println("The " + vehicle.getVehicleType() + " is in born state.");
    System.out.println("It cannot do anything special.");
  }
}
