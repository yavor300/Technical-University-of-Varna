package bg.tu_varna.sit.task1.impl;

import bg.tu_varna.sit.task1.base.Book;

public class Bookstore {

  private final Book[] books;

  public Bookstore(Book[] books) {
    this.books = books;
  }

  public double calculateTotalBookPrice() {

    double result = 0;

    for (Book book : books) {
      result += book.getPrice();
    }

    return result;
  }

  public double calculateAverageBookPrice() {

    return calculateTotalBookPrice() / books.length;
  }
}
