package tu.varna.sit.test.impl;

import tu.varna.sit.test.base.BaseCar;
import tu.varna.sit.test.base.Sellable;

public class Seat extends BaseCar implements Sellable {

  private final double price;

  Seat(String model, String color, int horsePower, String countryProduces, double price) {
    super(model, color, horsePower, countryProduces);
    this.price = price;
  }

  @Override
  public String toString() {
    return String.format("%s продава се за %.2f лв.",
            super.toString(), price);
  }

  @Override
  public double getPrice() {
    return price;
  }
}
