package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.VehicleBehavior;

public class VehicleSupervisor {

  private VehicleBehavior vehicleBehavior;

  public VehicleSupervisor(VehicleBehavior vehicleBehavior) {
    this.vehicleBehavior = vehicleBehavior;
  }

  public void setVehicleBehavior(VehicleBehavior vehicleBehavior) {
    this.vehicleBehavior = vehicleBehavior;
  }

  public void displayDetail(Vehicle vehicle) {
    vehicleBehavior.showDetail(vehicle);
  }
}
