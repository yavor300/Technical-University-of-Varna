package bg.tu_varna.sit.b1.f21621577.implementation.maindish.pizza;

import bg.tu_varna.sit.b1.f21621577.base.maindish.pizza.Pizza;
import bg.tu_varna.sit.b1.f21621577.enums.DoughType;

import java.util.List;

public class VegetarianPizza extends Pizza {

  private final List<String> vegetables;

  public VegetarianPizza(String name, double price, DoughType dough, List<String> vegetables) {
    super(name, price, dough);
    this.vegetables = vegetables;
  }

  public List<String> getVegetables() {
    return vegetables;
  }
}
