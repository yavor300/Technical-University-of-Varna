package bg.tu_varna.sit.b1.f21621577.implementation;

public class Architect {

  private final String firstName;
  private final String lastName;

  public Architect(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @Override
  public String toString() {
    return String.format("Architect: %s %s", firstName, lastName);
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }
}
