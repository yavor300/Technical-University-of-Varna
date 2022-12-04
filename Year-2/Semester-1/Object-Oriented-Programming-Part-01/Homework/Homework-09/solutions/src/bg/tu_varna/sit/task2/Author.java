package bg.tu_varna.sit.task2;

public class Author implements Comparable<Author> {

  private final String firstName;
  private final String lastName;
  private final String country;

  public Author(String firstName, String lastName, String country) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.country = country;
  }

  @Override
  public int compareTo(Author other) {

    if (equals(other)) {
      return 0;
    } else if (firstName.toLowerCase().compareTo(other.firstName.toLowerCase()) > 0 ||
            (firstName.toLowerCase().compareTo(other.firstName.toLowerCase()) == 0
                    && lastName.toLowerCase().compareTo(other.lastName.toLowerCase()) > 0) ||
            (firstName.toLowerCase().compareTo(other.firstName.toLowerCase()) == 0
                    && lastName.toLowerCase().compareTo(other.lastName.toLowerCase()) == 0
                    && country.toLowerCase().compareTo(other.country.toLowerCase()) > 0)) {
      return 1;
    } else {
      return -1;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Author author = (Author) o;

    if (!firstName.equalsIgnoreCase(author.firstName.toLowerCase())) return false;
    if (!lastName.equalsIgnoreCase(author.lastName.toLowerCase())) return false;
    return country.equalsIgnoreCase(author.country.toLowerCase());
  }

  @Override
  public int hashCode() {
    int result = firstName.toLowerCase().hashCode();
    result = 31 * result + lastName.toLowerCase().hashCode();
    result = 31 * result + country.toLowerCase().hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Author{" +
            "firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", country='" + country + '\'' +
            '}';
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getCountry() {
    return country;
  }
}
