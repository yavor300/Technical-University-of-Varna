package bg.tu_varna.sit.b1.f21621577.implementation.maindish.grill;

import bg.tu_varna.sit.b1.f21621577.base.maindish.grill.Grill;
import bg.tu_varna.sit.b1.f21621577.enums.ToastedLevel;

public class BulgarianGrill extends Grill {

  public BulgarianGrill(String name, double price) {
    super(name, price, ToastedLevel.MEDIUM);
  }
}
