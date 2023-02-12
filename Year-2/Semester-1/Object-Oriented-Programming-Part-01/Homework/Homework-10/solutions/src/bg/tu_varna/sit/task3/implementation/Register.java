package bg.tu_varna.sit.task3.implementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Register {

  private String unit;
  private final Map<Owner, List<Car>> registeredCars;

  public Register(String unit) {
    this.unit = unit;
    registeredCars = new HashMap<>();
  }

  public void addRegistration(Owner owner, Car car) {

    registeredCars.computeIfAbsent(owner, k -> new ArrayList<>());
    registeredCars.get(owner).add(car);
  }


  public void printRegisteredCars() {

    for (Map.Entry<Owner, List<Car>> entry : registeredCars.entrySet()) {
      for (Car c : entry.getValue()) {
        System.out.println(c);
      }
    }
  }

  public String findOwnerByRegistrationNumber(String number) {

    String result = "Not found";
    for (Map.Entry<Owner, List<Car>> entry : registeredCars.entrySet()) {
      for (Car car : entry.getValue()) {
        if (car.getRegistrationNumber().equalsIgnoreCase(number)) {
          return String.format("%s %s", entry.getKey().getFirstName(), entry.getKey().getLastName());
        }
      }
    }

    return result;
  }

  public void printLicensedDrivers() {

    for (Map.Entry<Owner, List<Car>> entry : registeredCars.entrySet()) {

      if (!entry.getKey().getDrivingLicense().equalsIgnoreCase("none")) {
        System.out.println(entry.getKey());
      }
    }
  }
}
