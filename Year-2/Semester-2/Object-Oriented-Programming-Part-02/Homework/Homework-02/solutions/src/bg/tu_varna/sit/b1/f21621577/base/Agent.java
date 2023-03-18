package bg.tu_varna.sit.b1.f21621577.base;

import bg.tu_varna.sit.b1.f21621577.implementation.property.Property;

public abstract class Agent {

  private final String firstName;
  private final String lastName;
  private final String phoneNumber;

  protected Agent(String firstName, String lastName, String phoneNumber) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.phoneNumber = phoneNumber;
  }

  protected String getFirstName() {
    return firstName;
  }

  protected String getLastName() {
    return lastName;
  }

  protected String getPhoneNumber() {
    return phoneNumber;
  }

  public abstract boolean addProperty(Property property);

  public abstract boolean deleteProperty(Property property);
}
