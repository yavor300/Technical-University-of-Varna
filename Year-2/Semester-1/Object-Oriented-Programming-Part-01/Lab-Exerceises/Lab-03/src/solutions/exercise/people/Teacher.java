package solutions.exercise.people;

import solutions.exercise.base.Person;

public class Teacher extends Person {

  private String scientificDegree;

  public Teacher(String name, int age, String scientificDegree) {
    super(name, age);
    this.scientificDegree = scientificDegree;
  }
}
