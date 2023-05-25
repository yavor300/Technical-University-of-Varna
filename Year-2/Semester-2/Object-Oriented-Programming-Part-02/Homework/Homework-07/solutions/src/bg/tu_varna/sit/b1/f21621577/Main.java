package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.implementation.entities.BoardGame;
import bg.tu_varna.sit.b1.f21621577.implementation.entities.Book;
import bg.tu_varna.sit.b1.f21621577.implementation.entities.Toy;
import bg.tu_varna.sit.b1.f21621577.implementation.order.Order;

public class Main {

  public static void main(String[] args) {

    Order order = new Order();

    order.addProduct(new Book(10.0));
    order.addProduct(new Toy(15.0));
    order.addProduct(new BoardGame(25.0));

    order.process();

    double totalPrice = order.getTotalPrice();
    System.out.println("Total price: " + totalPrice);
  }
}

