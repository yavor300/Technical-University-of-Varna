package bg.tu_varna.sit.b1.f21621577.base.maindish.salad;

import bg.tu_varna.sit.b1.f21621577.base.maindish.MainDish;
import bg.tu_varna.sit.b1.f21621577.enums.DressingType;

public class Salad extends MainDish {

  private final DressingType dressingType;

  protected Salad(String name, double price, DressingType dressingType) {
    super(name, price);
    this.dressingType = dressingType;
  }

  protected DressingType getDressingType() {
    return dressingType;
  }
}
