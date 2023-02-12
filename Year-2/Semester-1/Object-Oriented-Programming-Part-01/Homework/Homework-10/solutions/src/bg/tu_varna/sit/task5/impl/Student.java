package bg.tu_varna.sit.task5.impl;

import bg.tu_varna.sit.task5.base.Person;
import bg.tu_varna.sit.task5.enums.Course;
import bg.tu_varna.sit.task5.exceptions.PersonException;

public class Student extends Person {

  private final Course course;

  public Student(String name, Course course) throws PersonException {
    super(name);
    this.course = course;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Student student = (Student) o;

    return course == student.course;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + course.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return String.format("%s%nCourse: %s", super.toString(), course);
  }
}
