package bg.tu_varna.sit.b1.f21621577.base.maindish;

public class MainDish {

  private final String name;
  private final double price;

  protected MainDish(String name, double price) {
    this.name = name;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }
}
