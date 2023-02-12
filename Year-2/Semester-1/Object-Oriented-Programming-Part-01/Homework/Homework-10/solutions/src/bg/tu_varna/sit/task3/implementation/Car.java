package bg.tu_varna.sit.task3.implementation;

import bg.tu_varna.sit.task3.enums.Color;
import bg.tu_varna.sit.task3.enums.Fuel;
import bg.tu_varna.sit.task3.exceptions.CarDataException;

public class Car {

  private String registrationNumber;
  private Color color;
  private String brand;
  private String model;
  private Fuel fuel;

  public Car(String registrationNumber, Color color, String brand, String model, Fuel fuel) throws CarDataException {

    setRegistrationNumber(registrationNumber);
    setColor(color);
    setBrand(brand);
    setModel(model);
    setFuel(fuel);
  }

  private void setRegistrationNumber(String registrationNumber) throws CarDataException {

    this.registrationNumber = registrationNumber;

    if (registrationNumber == null || registrationNumber.trim().isEmpty()) {
      throw new CarDataException();
    }
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Car car = (Car) o;

    return registrationNumber.equals(car.registrationNumber);
  }

  @Override
  public int hashCode() {

    return registrationNumber.hashCode();
  }

  @Override
  public String toString() {

    return String.format("Color: %s%nBrand: %s%nModel: %s%nFuel: %s",
            color, brand, model, fuel);
  }

  private void setColor(Color color) throws CarDataException {

    this.color = color;

    if (color == null) {
      throw new CarDataException();
    }
  }

  private void setBrand(String brand) throws CarDataException {

    this.brand = brand;

    if (brand == null || brand.trim().isEmpty()) {
      throw new CarDataException();
    }
  }

  private void setModel(String model) throws CarDataException {

    this.model = model;

    if (model == null || model.trim().isEmpty()) {
      throw new CarDataException();
    }
  }

  private void setFuel(Fuel fuel) throws CarDataException {

    this.fuel = fuel;

    if (fuel == null) {
      throw new CarDataException();
    }
  }

  public String getRegistrationNumber() {
    return registrationNumber;
  }
}
