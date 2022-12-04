package bg.tu_varna.sit.task2;

/**
 * Задача 2
 * <p>
 * Подберете подходящи модификатори за достъп в задачата и
 * поставете сорс файловете в пакет bg.tu_varna.sit.task2.
 * <p>
 * Да се състави програма за книжен каталог. За целта са необходими:
 * <p>
 * - Изключение за невалидни данни (InvalidDataException);
 * <p>
 * - Клас автор (Author), който имплементира интерфейс Comparable.
 * Класът е с атрибути за име (firstName), фамилия (lastName) и държава (country).
 * Дефинирайте конструктор по всички полета, методи за достъп, текстово описание и
 * равенство (по всички полета). Интерфейсният метод сравнява по име, фамилия и държава;
 * <p>
 * - Клас книга (Book), имплементиращ интерфейс Comparable, който има
 * атрибути за заглавие (title), автор (author), година на издаване (publishingYear)
 * и налично количество (availableQuantity). Дефинирайте методи за достъп, метод за
 * модификация на наличното количество, метод за равенство (по автор и заглавие),
 * метод за сравнение (по автор и заглавие) и метод за текстово описание;
 * <p>
 * - Клас книжен каталог (BookCatalogue), който има като атрибут
 * колекция от уникални книги и конструктор по подразбиране.
 * <p>
 * Методи:
 * <p>
 * -- за добавяне на книга (addBook);
 * <p>
 * -- за премахване на книга (removeBook);
 * <p>
 * -- за заемане на книга (borrowBook), който проверява дали
 * има такава книга в каталога и променя наличното количество.
 * Ако заеманата книга е последна бройка, тя се премахва от колекцията;
 * <p>
 * -- за връщане на книга (returnBook), който увеличава наличното количество.
 * Ако книгата не присъства в каталога, тя се добавя като първа бройка;
 * <p>
 * -- за изчисляване и връщане на брой книги по зададен автор (countBooksByAuthor);
 * <p>
 * -- за сортиране на книгите по автор (sortCatalogueByAuthor);
 * <p>
 * -- за сортиране на книгите по налично количество (sortCatalogueByAvailableQuantity);
 * <p>
 * -- за сортиране на книгите по заглавие (sortCatalogueByTitle);
 * <p>
 * -- за намиране и връщане на броя книги, издадени
 * след дадена година (countBooksPublishedAfterYear);
 * <p>
 * -- за текстово описание.
 * <p>
 * Дефинирайте клас Application с главна функция и тествайте описаните функционалности.
 */
public class Application {

  public static void main(String[] args) {

    Author author = new Author("first", "last", "BG");
    Author author2 = new Author("first", "Zlast", "BG");

    Book book0 = new Book("c_title", author2, 1999, 3);
    Book book1 = new Book("title", author, 2000, 5);
    Book book2 = new Book("a_title", author, 2000, 99);
    Book book3 = new Book("B_title", author, 2000, 1);
    Book book4 = new Book("b_title", author, 2000, 1);

    BookCatalogue bookCatalogue = new BookCatalogue();
    bookCatalogue.addBook(book0);
    bookCatalogue.addBook(book1);
    bookCatalogue.addBook(book2);
    bookCatalogue.addBook(book3);
    bookCatalogue.addBook(book4);

    for (Book book : bookCatalogue.sortCatalogueByTitle()) {
      System.out.println(book);
    }

    try {
      Book bookToBorrow = new Book("b_TiTle", author, 2000, 1);
      bookCatalogue.borrowBook(bookToBorrow);
      for (Book book : bookCatalogue.getBooks()) {
        System.out.println(book);
      }

    } catch (InvalidDataException e) {
      System.out.println(e.getMessage());
    }

    System.out.println(bookCatalogue.countBooksByAuthor(author));

    System.out.println(bookCatalogue.countBooksPublishedAfterYear(1999));

    bookCatalogue.returnBook(new Book("b_TiTle", author, 2000, 1));
    for (Book book : bookCatalogue.sortCatalogueByTitle()) {
      System.out.println(book);
    }

    for (Book book : bookCatalogue.sortCatalogueByAvailableQuantity()) {
      System.out.println(book);
    }

    System.out.println("---");

    for (Book book : bookCatalogue.sortCatalogueByAuthor()) {
      System.out.println(book);
    }


  }
}
