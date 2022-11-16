package bg.tu_varna.sit.task2;

public class Shop extends Property {

  public Shop(double area, double price, boolean isForRent) {
    super(area, price, isForRent);
  }

  @Override
  public double calculateCommission() {

    if (isForRent() && getArea() < 50) {
      return 0.02 * getPrice();
    } else if (getArea() > 100) {
      return 0.01 * getPrice();
    }

    return 0.06 * getPrice();
  }
}
