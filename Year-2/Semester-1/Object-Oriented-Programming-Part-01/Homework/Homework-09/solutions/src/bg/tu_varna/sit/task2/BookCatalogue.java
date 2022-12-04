package bg.tu_varna.sit.task2;

import java.util.*;

public class BookCatalogue {

  private final Set<Book> books;

  public BookCatalogue() {
    this.books = new HashSet<>();
  }

  public boolean addBook(Book book) {

    return books.add(book);
  }

  public boolean removeBook(Book book) {

    return books.remove(book);
  }

  public void borrowBook(Book bookToBorrow) throws InvalidDataException {

    if (!books.contains(bookToBorrow)) {
      throw new InvalidDataException("Book does not exist!");
    }

    for (Iterator<Book> bookIterator = books.iterator(); bookIterator.hasNext(); ) {
      Book book = bookIterator.next();
      if (book.equals(bookToBorrow)) {
        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        if (book.getAvailableQuantity() == 0) {
          bookIterator.remove();
        }
      }
    }
  }

  public void returnBook(Book bookToReturn) {

    if (!books.contains(bookToReturn)) {
      bookToReturn.setAvailableQuantity(1);
      books.add(bookToReturn);
    } else {
      for (Book book : books) {
        if (book.equals(bookToReturn)) {
          book.setAvailableQuantity(book.getAvailableQuantity() + 1);
        }
      }
    }
  }

  public int countBooksByAuthor(Author author) {

    int result = 0;
    for (Book book : books) {
      if (book.getAuthor().equals(author)) {
        result++;
      }
    }
    return result;
  }

  public Set<Book> sortCatalogueByTitle() {

    SortedSet<Book> sortedByTitle = new TreeSet<>(
            new Comparator<Book>() {
              @Override
              public int compare(Book first, Book second) {
                if (first.getTitle().toLowerCase().equals(second.getTitle().toLowerCase())) {
                  return 0;
                }

                if (first.getTitle().toLowerCase().compareTo(second.getTitle().toLowerCase()) > 0) {
                  return 1;
                }

                return -1;
              }
            }
    );

    sortedByTitle.addAll(books);
    return sortedByTitle;
  }

  public Set<Book> sortCatalogueByAvailableQuantity() {

    SortedSet<Book> sortedByAvailableQuantity = new TreeSet<>(
            new Comparator<Book>() {
              @Override
              public int compare(Book first, Book second) {
                if (first.getAvailableQuantity() == second.getAvailableQuantity()) {
                  return 0;
                }

                if (first.getAvailableQuantity() > (second.getAvailableQuantity())) {
                  return 1;
                }

                return -1;
              }
            }
    );

    sortedByAvailableQuantity.addAll(books);
    return sortedByAvailableQuantity;
  }

  public Set<Book> sortCatalogueByAuthor() {

    SortedSet<Book> sortedByAuthor = new TreeSet<>(
            new Comparator<Book>() {
              @Override
              public int compare(Book first, Book second) {
                if (first.getAuthor().equals(second.getAuthor())) {
                  return 0;
                }

                if (first.getAuthor().compareTo(second.getAuthor()) > 0) {
                  return 1;
                }

                return -1;
              }
            }
    );

    sortedByAuthor.addAll(books);
    return sortedByAuthor;
  }

  public int countBooksPublishedAfterYear(int year) {

    int result = 0;
    for (Book book : books) {
      if (book.getPublishingYear() > year) {
        result++;
      }
    }
    return result;
  }

  @Override
  public String toString() {

    StringBuilder result = new StringBuilder();

    for (Book book : books) {
      result.append(book).append("\n");
    }

    return result.toString();
  }

  public Set<Book> getBooks() {
    return books;
  }
}
