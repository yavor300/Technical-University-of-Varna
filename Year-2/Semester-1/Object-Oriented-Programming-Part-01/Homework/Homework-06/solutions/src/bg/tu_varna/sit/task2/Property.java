package bg.tu_varna.sit.task2;

public abstract class Property implements Commission {

  private final double area;
  private final double price;
  private final boolean isForRent;

  protected Property(double area, double price, boolean isForRent) {
    this.area = area;
    this.price = price;
    this.isForRent = isForRent;
  }

  public boolean isForRent() {
    return isForRent;
  }

  public double getPrice() {
    return price;
  }

  public double getArea() {
    return area;
  }
}
