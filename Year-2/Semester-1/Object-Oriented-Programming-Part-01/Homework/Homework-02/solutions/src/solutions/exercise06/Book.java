package exercise06;

public class Book {

  private String authorFirstName;
  private String authorLastName;
  private String title;
  private int year;
  private int pages;

  public Book(String authorFirstName, String authorLastName, String title, int year, int pages) {
    this.authorFirstName = authorFirstName;
    this.authorLastName = authorLastName;
    this.title = title;
    this.year = year;
    this.pages = pages;
  }

  @Override
  public String toString() {
    return String.format("Author first name: %s%nAuthor last name: %s%nTitle: %s%nYear published: %d%nPages: %d",
            getAuthorFirstName(), getAuthorLastName(), getTitle(), getYear(), getPages());
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Book other = (Book) o;

    if (!getAuthorFirstName().equals(other.getAuthorFirstName())) return false;
    if (!getAuthorLastName().equals(other.getAuthorLastName())) return false;
    return getTitle().equals(other.getTitle());
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

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public int getPages() {
    return pages;
  }

  public void setPages(int pages) {
    this.pages = pages;
  }
}
