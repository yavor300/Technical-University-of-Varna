package bg.tu_varna.sit.task3.base;

import bg.tu_varna.sit.task3.base.interfaces.Competency;
import bg.tu_varna.sit.task3.exceptions.PersonalDataException;

public abstract class Person implements Competency {

  private String egn;
  private String firstName;
  private String lastName;
  private int age;

  protected Person(String egn, String firstName, String lastName, int age) throws PersonalDataException {

    setEgn(egn);
    setFirstName(firstName);
    setLastName(lastName);
    setAge(age);
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Person person = (Person) o;

    return egn.equals(person.egn);
  }

  @Override
  public int hashCode() {

    return egn.hashCode();
  }

  @Override
  public String toString() {

    return String.format("Egn: %s%nFirst name: %s%nLast name: %s%nAge: %d",
            egn, firstName, lastName, age);
  }

  private void setEgn(String egn) throws PersonalDataException {

    this.egn = egn;

    if (egn.trim().length() != 10) {
      throw new PersonalDataException();
    }
  }

  private void setFirstName(String firstName) throws PersonalDataException {

    this.firstName = firstName;

    if (firstName == null || firstName.trim().isEmpty()) {
      throw new PersonalDataException();
    }
  }

  private void setLastName(String lastName) throws PersonalDataException {

    this.lastName = lastName;

    if (lastName == null || lastName.trim().isEmpty()) {
      throw new PersonalDataException();
    }
  }

  private void setAge(int age) throws PersonalDataException {

    this.age = age;

    if (age <= 0) {
      throw new PersonalDataException();
    }
  }

  public String getEgn() {
    return egn;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public int getAge() {
    return age;
  }

}
