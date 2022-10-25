package bg.tu_varna.sit.task04;

public class Person {

  private final String firstName;
  private final String lastName;
  private final String id;
  private final int age;

  public Person(String firstName, String lastName, String id, int age) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.id = id;
    this.age = age;
  }

  public String getId() {
    return id;
  }

  public int getAge() {
    return age;
  }

  public boolean equals(Object o) {
    if (this == o) return true;
    Person person = (Person) o;
    return this.id == person.id;
  }

  public String toString() {
    return "First name: " + firstName +
            ", Last name: " + lastName +
            ", Id: " + id +
            ", Age: " + age;
  }
}
