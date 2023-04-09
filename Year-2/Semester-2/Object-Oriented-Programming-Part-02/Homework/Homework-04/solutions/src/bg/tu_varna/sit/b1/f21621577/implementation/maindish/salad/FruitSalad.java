package bg.tu_varna.sit.b1.f21621577.implementation.maindish.salad;

import bg.tu_varna.sit.b1.f21621577.base.maindish.salad.Salad;
import bg.tu_varna.sit.b1.f21621577.enums.DressingType;

import java.util.List;

public class FruitSalad extends Salad {

  private final List<String> fruits;

  protected FruitSalad(String name, double price, DressingType dressingType, List<String> fruits) {
    super(name, price, dressingType);
    this.fruits = fruits;
  }

  protected List<String> getFruits() {
    return fruits;
  }
}
