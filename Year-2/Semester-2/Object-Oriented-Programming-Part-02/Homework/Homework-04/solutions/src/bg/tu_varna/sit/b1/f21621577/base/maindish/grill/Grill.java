package bg.tu_varna.sit.b1.f21621577.base.maindish.grill;

import bg.tu_varna.sit.b1.f21621577.base.maindish.MainDish;
import bg.tu_varna.sit.b1.f21621577.enums.ToastedLevel;

public class Grill extends MainDish {

  private final ToastedLevel toastedLevel;

  protected Grill(String name, double price, ToastedLevel toastedLevel) {
    super(name, price);
    this.toastedLevel = toastedLevel;
  }

  protected ToastedLevel getToastedLevel() {
    return toastedLevel;
  }
}
