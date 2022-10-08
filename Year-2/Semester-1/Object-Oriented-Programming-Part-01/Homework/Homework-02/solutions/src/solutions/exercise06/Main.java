package exercise06;

import java.util.Scanner;

/**
 * Задача 6
 * <p>
 * Дефинирайте клас Book, който описва книга с атрибути име на автора,
 * фамилия на автора, заглавие, година на издаване, брой страници.
 * Инициализирайте обектите с параметризиран конструктор.
 * Като поведение използвайте методи за четене, текстово описание
 * и равенство (по имена на автора и заглавие). Създайте масив от 10 обекта.
 * Намерете и изведете броя книги, издадени след зададена година.
 */
public class Main {


  private static final Integer BOOKS_COUNT = 10;

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    Book[] books = initializeBooksData(BOOKS_COUNT);
    for (Book book : books) {
      System.out.println(book);
    }

    System.out.print("Get books after year: ");
    int year = scanner.nextInt();
    Book[] booksAfterYear = getBooksAfterYear(year, books);
    if (booksAfterYear == null) {
      System.out.println("No books found.");
    } else {
      System.out.printf("%nDetails about books after year %d:%n", year);
      for (Book book : booksAfterYear) {
        System.out.println(book);
      }

    }
  }

  private static Book[] initializeBooksData(int dataCounter) {

    String[] firstNames = {"Georgi", "Milen", "Marian", "Ivan", "Petar", "Mitko", "Kaloyan"};
    String[] lastNames = {"Petkov", "Georgiev", "Iliev", "Yordanov", "Borisov", "Aleksandrov"};
    String[] titles = {"ULYSSES", "THE GREAT GATSBY", "A PORTRAIT OF THE ARTIST AS A YOUNG MAN", "LOLITA", "1984"};
    Book[] books = new Book[dataCounter];

    for (int i = 0; i < dataCounter; i++) {
      books[i] = new Book(
              firstNames[getRandomIntegerNumber(0, firstNames.length)],
              lastNames[getRandomIntegerNumber(0, lastNames.length)],
              titles[getRandomIntegerNumber(0, titles.length)],
              getRandomIntegerNumber(2010, 2022),
              getRandomIntegerNumber(250, 400)
      );

      for (int j = 0; j < i; j++) {
        if (books[i].equals(books[j])) {
          i--;
          break;
        }
      }
    }

    return books;
  }

  private static int getBooksAfterYearCount(int year, Book[] books) {

    int counter = 0;
    for (Book book : books) {
      if (book.getYear() > year) {
        counter++;
      }
    }

    return counter;
  }

  private static Book[] getBooksAfterYear(int year, Book[] books) {

    int booksAfterYearCount = getBooksAfterYearCount(year, books);
    if (booksAfterYearCount == 0) {
      return null;
    }

    Book[] result = new Book[booksAfterYearCount];
    int resultIndex = 0;
    for (Book book : books) {
      if (book.getYear() > year) {
        result[resultIndex++] = book;
      }
    }

    return result;
  }

  private static int getRandomIntegerNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }
}
