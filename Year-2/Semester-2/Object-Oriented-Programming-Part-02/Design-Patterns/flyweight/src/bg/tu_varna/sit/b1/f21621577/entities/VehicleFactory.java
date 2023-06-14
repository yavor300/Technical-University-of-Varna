package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.Vehicle;

import java.util.HashMap;
import java.util.Map;

public class VehicleFactory {

  Map<String, Vehicle> vehicles = new HashMap<>();

  public int totalObjectsCreated() {

    return vehicles.size();
  }

  public synchronized Vehicle getVehicleFromFactory(String vehicle) throws Exception {

    Vehicle vehicleType = vehicles.get(vehicle);

    if (vehicleType != null) {
      System.out.println("\n\tUsing an existing vehicle now.");
    } else {
      switch (vehicle) {
        case "car":
          System.out.println("Making a car for first time.");
          vehicleType = new Car("One car is ready");
          vehicles.put("car", vehicleType);
          break;

        case "bus":
          System.out.println("Making a bus for the first time.");
          vehicleType = new Bus("One bus is ready");
          vehicles.put("bus", vehicleType);
          break;

        case "future":
          System.out.println("Making a future vehicle for the first time.");
          vehicleType = new FutureVehicle("One future vehicle (Vehicle 2050) is ready");
          vehicles.put("future", vehicleType);
          break;

        default:
          throw new Exception("Unknown vehicle type.");
      }
    }

    return vehicleType;
  }
}
