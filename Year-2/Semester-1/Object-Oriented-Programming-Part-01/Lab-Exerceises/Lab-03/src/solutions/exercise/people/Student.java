package solutions.exercise.people;

import solutions.exercise.base.Person;

public class Student extends Person {

  private String number;

  public Student(String name, int age, String number) {
    super(name, age);
    this.number = number;
  }

  public String iAm() {
    return "I am " + getName();
  }
}
