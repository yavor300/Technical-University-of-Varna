package bg.tu_varna.sit.task05;

/**
 * Анализирайте задачата и определите грешката (без да пренаписвате целия код),
 * така че да се изведат броят книги на един автор
 */

public class Application {

  public static void main(String[] args) {
    Library library = new Library();

    library.addBook(new Book("The Great Gatsby", new Author("F. Scott", "Fitzgerald", "classics"), 4));
    library.addBook(new Book("The Mysterious Affair at Styles", new Author("Agatha", "Christie", "crime"), 12));
    library.addBook(new Book("East of Eden", new Author("John", "Steinbeck", "classics"), 8));
    library.addBook(new Book("Death on the Nile", new Author("Agatha", "Christie", "crime"), 5));

    System.out.println("Number of books by Agatha Christie: " + library.countBooksByAuthor(new Author("Agatha", "Christie", "crime")));
  }
}
