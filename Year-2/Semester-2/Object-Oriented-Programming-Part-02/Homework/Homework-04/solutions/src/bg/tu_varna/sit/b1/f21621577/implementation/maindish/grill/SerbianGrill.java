package bg.tu_varna.sit.b1.f21621577.implementation.maindish.grill;

import bg.tu_varna.sit.b1.f21621577.base.maindish.grill.Grill;
import bg.tu_varna.sit.b1.f21621577.enums.ToastedLevel;

public class SerbianGrill extends Grill {

  public SerbianGrill(String name, double price) {
    super(name, price, ToastedLevel.WELL_DONE);
  }
}
