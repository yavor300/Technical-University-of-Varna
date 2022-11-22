package bg.tu_varna.sit.task1;

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