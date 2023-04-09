package bg.tu_varna.sit.b1.f21621577.base.extratopping;

import bg.tu_varna.sit.b1.f21621577.base.maindish.MainDish;

public abstract class ToppingDecorator extends MainDish {

  private final MainDish mainDish;

  protected ToppingDecorator(String name, double price, MainDish mainDish) {
    super(name, price);
    this.mainDish = mainDish;
  }

  public MainDish getMainDish() {
    return mainDish;
  }

  public abstract String getFullMealName();

  public double getFullMealPrice() {
    return mainDish.getPrice() + getPrice();
  }
}
