package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.base.Vehicle;
import bg.tu_varna.sit.b1.f21621577.entities.VehicleFactory;
import java.util.Random;

public class Client {

  public static void main(String[] args) throws Exception {

    VehicleFactory vehicleFactory = new VehicleFactory();
    createVehicles("car", 3, vehicleFactory);
    createVehicles("bus", 5, vehicleFactory);
    createVehicles("future", 2, vehicleFactory);
  }

  private static void createVehicles(String vehicleType, int count, VehicleFactory factory) throws Exception {

    int distinctValues;
    for (int i = 0; i < count; i++) {
      Vehicle vehicle = factory.getVehicleFromFactory(vehicleType);
      vehicle.aboutMe(getRandomColor());
      distinctValues = factory.totalObjectsCreated();
      System.out.println("\n\tDistinct vehicles in this application: " + distinctValues);
    }
  }

  private static String getRandomColor() {

    Random random = new Random();
    int number = random.nextInt() + 1;
    if (number % 2 == 0) {
      return "red";
    }

    return "green";
  }
}
