package bg.tu_varna.sit.task9;

class Car {

  private String brand;
  private String model;
  private String color;
  private int power;
  private String engineType;
  private String transmission;
  private int yearOfManufacture;
  private int mileage;

  Car(String brand, String model, int power, String engineType, String transmission, int yearOfManufacture) {
    this.brand = brand;
    this.model = model;
    this.power = power;
    this.engineType = engineType;
    this.transmission = transmission;
    this.yearOfManufacture = yearOfManufacture;
    this.color = "CCCCCCC";
  }

  boolean startup() {

    return true;
  }
}
