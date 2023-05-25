package bg.tu_varna.sit.b1.f21621577.implementation.entities;

import bg.tu_varna.sit.b1.f21621577.base.entities.Product;
import bg.tu_varna.sit.b1.f21621577.base.visitor.OrderVisitor;

public class Book extends Product {

  public Book(double price) {
    super(price);
  }

  @Override
  public void accept(OrderVisitor visitor) {
    visitor.visit(this);
  }
}
