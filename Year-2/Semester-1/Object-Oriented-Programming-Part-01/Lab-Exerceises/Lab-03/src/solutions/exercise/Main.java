package solutions.exercise;

import solutions.exercise.people.Student;

public class Main {

  public static void main(String[] args) {
    Student person = new Student("Steve", 17, "1234");
    System.out.println(person.iAm());
  }
}
