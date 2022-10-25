package bg.tu_varna.sit.task05;

public class Author {

  private final String firstName;
  private final String lastName;
  private final String genre;

  public Author(String firstName, String lastName, String genre) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.genre = genre;
  }

  public String toString() {
    return "Author{" +
            "First name: " + firstName +
            ", Last name: " + lastName +
            ", Genre: " + genre +
            "}";
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) return true;
    if (other == null || getClass() != other.getClass()) return false;

    Author author = (Author) other;

    if (!firstName.equals(author.firstName)) return false;
    return lastName.equals(author.lastName);
  }
}
