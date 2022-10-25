package bg.tu_varna.sit.task05;

public class Book {

  private final String title;
  private final Author author;
  private final int availableQuantity;

  public Book(String title, Author author, int availableQuantity) {
    this.title = title;
    this.author = author;
    this.availableQuantity = availableQuantity;
  }

  public Author getAuthor() {
    return author;
  }

  public boolean equals(Object other) {

    if (this == other) return true;
    if (other == null || getClass() != other.getClass()) return false;
    Book book = (Book) other;
    return this.title.equals(book.title) && this.author.equals(book.author);
  }

  @Override
  public String toString() {
    return "Book{" +
            "title = " + title +
            ", author = " + author +
            ", availableQuantity = " + availableQuantity +
            '}';
  }
}
