package bg.tu_varna.sit.b1.f21621577.entities;

import bg.tu_varna.sit.b1.f21621577.base.PriceType;

public class ShowroomPrice implements PriceType {

  @Override
  public void displayProductPrice(String productType, double cost) {

    System.out.println("The " + productType + "'s showroom price is $" + cost);
  }
}
