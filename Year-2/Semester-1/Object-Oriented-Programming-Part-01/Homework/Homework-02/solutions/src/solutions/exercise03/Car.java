package exercise03;

public class Car {
  private String brand;
  private String number;
  private String range;

  public Car(String brand, String number, String range) {
    this.brand = brand;
    this.number = number;
    this.range = range;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getRange() {
    return range;
  }

  public void setRange(String range) {
    this.range = range;
  }

  @Override
  public String toString() {
    return String.format("Car:%n\tBrand: %s%n\tNumber: %s%n\tRange: %s",
            brand, number, range);
  }
}
