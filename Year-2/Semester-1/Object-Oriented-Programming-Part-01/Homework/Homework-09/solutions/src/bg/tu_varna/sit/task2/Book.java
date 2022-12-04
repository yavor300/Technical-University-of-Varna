package bg.tu_varna.sit.task2;

public class Book implements Comparable<Book> {

  private final String title;
  private final Author author;
  private final int publishingYear;
  private int availableQuantity;

  public Book(String title, Author author, int publishingYear, int availableQuantity) {
    this.title = title;
    this.author = author;
    this.publishingYear = publishingYear;
    this.availableQuantity = availableQuantity;
  }

  @Override
  public int compareTo(Book o) {

    if (author.equals(o.author) && title.equalsIgnoreCase(o.getTitle())) {
      return 0;
    } else if (author.compareTo(o.author) > 0
            || (author.compareTo(o.author) == 0
            && title.toLowerCase().compareTo(o.getTitle().toLowerCase()) > 0)) {
      return 1;
    } else {
      return -1;
    }
  }

  @Override
  public String toString() {
    return "Book{" +
            "title='" + title + '\'' +
            ", author=" + author +
            ", publishingYear=" + publishingYear +
            ", availableQuantity=" + availableQuantity +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Book book = (Book) o;

    if (!title.toLowerCase().equals(book.title.toLowerCase())) return false;
    return author.equals(book.author);
  }

  @Override
  public int hashCode() {
    int result = title.toLowerCase().hashCode();
    result = 31 * result + author.hashCode();
    return result;
  }

  public String getTitle() {
    return title;
  }

  public Author getAuthor() {
    return author;
  }

  public int getPublishingYear() {
    return publishingYear;
  }

  public int getAvailableQuantity() {
    return availableQuantity;
  }

  public void setAvailableQuantity(int availableQuantity) {
    this.availableQuantity = availableQuantity;
  }
}
