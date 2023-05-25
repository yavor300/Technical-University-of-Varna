package bg.tu_varna.sit.b1.f21621577.base.entities;

import bg.tu_varna.sit.b1.f21621577.base.visitor.OrderVisitor;

public abstract class Product {

  private final double price;

  protected Product(double price) {
    this.price = price;
  }

  public abstract void accept(OrderVisitor visitor);

  public double getPrice() {
    return price;
  }
}
