package bg.tu_varna.sit.b1.f21621577.implementation.maindish.salad;

import bg.tu_varna.sit.b1.f21621577.base.maindish.salad.Salad;
import bg.tu_varna.sit.b1.f21621577.enums.DressingType;

import java.util.List;

public class SeaSalad extends Salad {

  private final List<String> seafood;

  protected SeaSalad(String name, double price, DressingType dressingType, List<String> seafood) {
    super(name, price, dressingType);
    this.seafood = seafood;
  }

  protected List<String> getSeafood() {
    return seafood;
  }
}
