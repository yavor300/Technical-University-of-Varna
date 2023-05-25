package bg.tu_varna.sit.b1.f21621577.implementation.entities;

import bg.tu_varna.sit.b1.f21621577.base.entities.Product;
import bg.tu_varna.sit.b1.f21621577.base.visitor.OrderVisitor;

public class BoardGame extends Product {

  public BoardGame(double price) {
    super(price);
  }

  @Override
  public void accept(OrderVisitor visitor) {
    visitor.visit(this);
  }
}
