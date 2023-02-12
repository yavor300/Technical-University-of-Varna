package bg.tu_varna.sit.task5.impl;

import bg.tu_varna.sit.task5.enums.AcademicPositions;
import bg.tu_varna.sit.task5.exceptions.PersonException;
import bg.tu_varna.sit.task5.base.Person;

public class Teacher extends Person {

  private final AcademicPositions academicPositions;

  public Teacher(String name, AcademicPositions academicPositions) throws PersonException {
    super(name);
    this.academicPositions = academicPositions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Teacher teacher = (Teacher) o;

    return academicPositions == teacher.academicPositions;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + academicPositions.hashCode();
    return result;
  }
}
