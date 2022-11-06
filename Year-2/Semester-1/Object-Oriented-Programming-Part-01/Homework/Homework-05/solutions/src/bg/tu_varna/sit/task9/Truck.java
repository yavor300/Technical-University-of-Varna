package bg.tu_varna.sit.task9;

class Truck extends Car {

  private int capacity;
  private final int elapsedTime;

  Truck(String brand, String model, int power, String engineType, String transmission, int yearOfManufacture, int capacity, int elapsedTime) {
    super(brand, model, power, engineType, transmission, yearOfManufacture);
    this.capacity = capacity;
    this.elapsedTime = elapsedTime;
  }

  @Override
  boolean startup() {

    return elapsedTime <= 6;
  }
}
