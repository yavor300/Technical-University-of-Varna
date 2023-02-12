package bg.tu_varna.sit.task5.base;

import bg.tu_varna.sit.task5.exceptions.PersonException;

import java.util.Arrays;

public class Person {

  private String name;

  protected Person(String name) throws PersonException {
    setName(name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Person person = (Person) o;

    return name.equals(person.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public String toString() {
    return String.format("%s", name);
  }

  private void setName(String name) throws PersonException {

    if (name.contains(" ")) {

      String[] names = Arrays.stream(name.split("\\s+")).map(String::trim).toArray(String[]::new);
      for (String s : names) {
        if (!Character.isUpperCase(s.charAt(0))) {
          throw new PersonException();
        }
      }
    } else {
      throw new PersonException();
    }

    this.name = name;
  }

}
