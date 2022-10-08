package exercise06;

public class Book {
  private String authorFirstName;
  private String authorLastName;
  private String title;
  private Integer year;
  private Integer pages;

  public Book(String authorFirstName, String authorLastName, String title, Integer year, Integer pages) {
    this.authorFirstName = authorFirstName;
    this.authorLastName = authorLastName;
    this.title = title;
    this.year = year;
    this.pages = pages;
  }

  public String getAuthorFirstName() {
    return authorFirstName;
  }

  public void setAuthorFirstName(String authorFirstName) {
    this.authorFirstName = authorFirstName;
  }

  public String getAuthorLastName() {
    return authorLastName;
  }

  public void setAuthorLastName(String authorLastName) {
    this.authorLastName = authorLastName;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public Integer getPages() {
    return pages;
  }

  public void setPages(Integer pages) {
    this.pages = pages;
  }

  @Override
  public String toString() {
    return String.format("Book:%n\tAuthor first name: %s%n\tAuthor last name: %s%n\tTitle: %s%n\tYear published: %d%n\tPages: %d",
            authorFirstName, authorLastName, title, year, pages);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Book book = (Book) o;

    if (!authorFirstName.equals(book.authorFirstName)) return false;
    if (!authorLastName.equals(book.authorLastName)) return false;
    return title.equals(book.title);
  }
}
