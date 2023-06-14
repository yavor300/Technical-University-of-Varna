package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.ElectronicItem;
import bg.tu_varna.sit.b1.f21621577.base.PriceType;

public class Television extends ElectronicItem {

  private final double cost;

  public Television(PriceType priceType) {

    super(priceType, "television");
    cost = 2000;
  }

  @Override
  public void showPriceDetail() {

    getPriceType().displayProductPrice(getProductType(), cost);
  }
}
