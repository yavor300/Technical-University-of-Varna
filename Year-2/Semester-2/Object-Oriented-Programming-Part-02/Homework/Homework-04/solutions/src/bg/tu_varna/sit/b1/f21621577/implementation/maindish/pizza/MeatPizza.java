package bg.tu_varna.sit.b1.f21621577.implementation.maindish.pizza;

import bg.tu_varna.sit.b1.f21621577.base.maindish.pizza.Pizza;
import bg.tu_varna.sit.b1.f21621577.enums.DoughType;
import bg.tu_varna.sit.b1.f21621577.enums.MeatType;

public class MeatPizza extends Pizza {

  private final MeatType meatType;

  public MeatPizza(String name, double price, DoughType dough, MeatType meatType) {
    super(name, price, dough);
    this.meatType = meatType;
  }

  public MeatType getMeatType() {
    return meatType;
  }
}
