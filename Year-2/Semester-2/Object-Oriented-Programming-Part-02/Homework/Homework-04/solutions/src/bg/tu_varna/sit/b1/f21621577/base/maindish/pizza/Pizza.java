package bg.tu_varna.sit.b1.f21621577.base.maindish.pizza;

import bg.tu_varna.sit.b1.f21621577.base.maindish.MainDish;
import bg.tu_varna.sit.b1.f21621577.enums.DoughType;

public class Pizza extends MainDish {

  private final DoughType dough;

  public Pizza(String name, double price, DoughType dough) {
    super(name, price);
    this.dough = dough;
  }

  public DoughType getDough() {
    return dough;
  }
}
