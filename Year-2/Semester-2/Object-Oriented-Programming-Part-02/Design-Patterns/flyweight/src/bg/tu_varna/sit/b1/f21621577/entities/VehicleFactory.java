package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.Vehicle;

import java.util.HashMap;
import java.util.Map;

public class VehicleFactory {

  Map<String, Vehicle> vehicles = new HashMap<>();

  int totalObjectsCreated() {

    return vehicles.size();
  }
}
