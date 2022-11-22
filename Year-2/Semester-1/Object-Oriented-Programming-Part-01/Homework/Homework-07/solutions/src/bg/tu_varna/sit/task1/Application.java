package bg.tu_varna.sit.task1;

public class Application {

  public static void main(String[] args) {

    Author author = new Author("firstName", "lastName", "BG");

    Book crimeNovel = new CrimeNovel("Title1", author, 1999, 10.0, CoverType.hardCover);
    Book poetryBook = new PoetryBook("Title2", author, 2006, 15.0, CoverType.paperback);
    Book sciFiNovel = new SciFiNovel("Title3", author, 2010, 20.50, CoverType.hardCover);

    Book[] books = new Book[]{crimeNovel, poetryBook, sciFiNovel};

    Bookstore bookstore = new Bookstore(books);
    System.out.println(bookstore.calculateAverageBookPrice());
    System.out.println(bookstore.calculateTotalBookPrice());

    try {
      Book invalid = new CrimeNovel("Title4", null, 2001, -1, CoverType.paperback);
    } catch (InvalidDataException e) {
      System.out.println(e.getMessage());
    }

  }
}
