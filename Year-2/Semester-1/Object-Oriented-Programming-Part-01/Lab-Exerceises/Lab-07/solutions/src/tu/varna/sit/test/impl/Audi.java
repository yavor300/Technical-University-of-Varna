package tu.varna.sit.test.impl;

import tu.varna.sit.test.base.BaseCar;
import tu.varna.sit.test.base.Rentable;

public class Audi extends BaseCar implements Rentable {

  private final int minRentDay;
  private final double pricePerDay;

  Audi(String model, String color, int horsePower, String countryProduces, int minRentDay, double pricePerDay) {
    super(model, color, horsePower, countryProduces);
    this.minRentDay = minRentDay;
    this.pricePerDay = pricePerDay;
  }

  @Override
  public String toString() {
    return String.format("%s. Минимиален период на наемане от %d дни. Цена на ден %.2f лв.",
            super.toString(), minRentDay, pricePerDay);
  }

  @Override
  public int getMinRentDay() {
    return minRentDay;
  }

  @Override
  public double getPricePerDay() {
    return pricePerDay;
  }
}
