package bg.tu_varna.sit.task05;

public class Library {

  private final Book[] books;

  public Library() {
    books = new Book[4];
  }

  public void addBook(Book book) {
    for (int i = 0; i < books.length; i++) {
      if (books[i] == null) {
        books[i] = book;
        break;
      }
    }
  }

  public int countBooksByAuthor(Author author) {
    int count = 0;
    for (int i = 0; i < books.length; i++) {
      if (books[i].getAuthor().equals(author))
        count++;
    }
    return count;
  }

}
