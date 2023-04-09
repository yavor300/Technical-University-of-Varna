package bg.tu_varna.sit.b1.f21621577.implementation.extratopping;

import bg.tu_varna.sit.b1.f21621577.base.extratopping.ToppingDecorator;
import bg.tu_varna.sit.b1.f21621577.base.maindish.MainDish;

public class BaconDecorator extends ToppingDecorator {

  public BaconDecorator(String name, double price, MainDish mainDish) {
    super(name, price, mainDish);
  }

  @Override
  public String getFullMealName() {
    return getMainDish().getName() + " with bacon.";
  }
}
