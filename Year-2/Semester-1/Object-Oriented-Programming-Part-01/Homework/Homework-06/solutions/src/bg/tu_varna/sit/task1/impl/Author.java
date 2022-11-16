package bg.tu_varna.sit.task1.impl;

public class Author {

  private final String firstName;
  private final String lastName;
  private final String country;

  public Author(String firstName, String lastName, String country) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.country = country;
  }

  @Override
  public boolean equals(Object other) {

    if (this == other) return true;
    if (other == null || getClass() != other.getClass()) return false;

    Author author = (Author) other;

    if (!firstName.equals(author.firstName)) return false;
    if (!lastName.equals(author.lastName)) return false;
    return country.equals(author.country);
  }

  @Override
  public int hashCode() {

    int result = firstName.hashCode();
    result = 31 * result + lastName.hashCode();
    result = 31 * result + country.hashCode();

    return result;
  }

  @Override
  public String  toString() {

    return String.format("%s,%s", firstName, lastName);
  }
}
