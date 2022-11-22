package bg.tu_varna.sit.task1;

public class Author {

  private String firstName;
  private String lastName;
  private String country;

  public Author(String firstName, String lastName, String country) {
    setFirstName(firstName);
    setLastName(lastName);
    setCountry(country);
  }

  private void setFirstName(String firstName) {

    if (firstName.trim().length() <= 0) {
      throw new InvalidDataException("Author's first name must not be empty!");
    }
    this.firstName = firstName;
  }

  private void setLastName(String lastName) {

    if (lastName.trim().length() <= 0) {
      throw new InvalidDataException("Author's last name must not be empty!");
    }
    this.lastName = lastName;
  }

  private void setCountry(String country) {

    if (country.trim().length() <= 0) {
      throw new InvalidDataException("County's name must not be empty!");
    }
    this.country = country;
  }
}