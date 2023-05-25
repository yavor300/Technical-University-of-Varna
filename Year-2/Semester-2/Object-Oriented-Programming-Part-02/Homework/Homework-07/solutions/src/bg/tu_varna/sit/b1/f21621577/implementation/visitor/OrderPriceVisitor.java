package bg.tu_varna.sit.b1.f21621577.implementation.visitor;

import bg.tu_varna.sit.b1.f21621577.base.visitor.OrderVisitor;
import bg.tu_varna.sit.b1.f21621577.implementation.entities.BoardGame;
import bg.tu_varna.sit.b1.f21621577.implementation.entities.Book;
import bg.tu_varna.sit.b1.f21621577.implementation.entities.Toy;

public class OrderPriceVisitor implements OrderVisitor {

  private double totalPrice = 0;

  @Override
  public void visit(Book book) {
    totalPrice += book.getPrice();
  }

  @Override
  public void visit(Toy toy) {
    totalPrice += toy.getPrice();
  }

  @Override
  public void visit(BoardGame boardGame) {
    totalPrice += boardGame.getPrice();
  }


  public double getTotalPrice() {
    return totalPrice;
  }
}
