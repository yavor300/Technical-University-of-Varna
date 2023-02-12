package bg.tu_varna.sit.task5.impl;

import bg.tu_varna.sit.task5.exceptions.SemesterControlException;

import java.util.Map;
import java.util.Set;

public class Discipline {

  private final String name;
  private Map<Student, Integer> students;
  private final Set<Teacher> teachers;

  public Discipline(String name, Map<Student, Integer> students, Set<Teacher> teachers) throws SemesterControlException {
    this.name = name;
    setStudents(students);
    this.teachers = teachers;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Discipline that = (Discipline) o;

    return name.equals(that.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  public Map<Student, Integer> getStudents() {
    return students;
  }

  private void setStudents(Map<Student, Integer> students) throws SemesterControlException {

    for (Map.Entry<Student, Integer> entry : students.entrySet()) {
      if (entry.getValue() < 0 || entry.getValue() > 40) {
        throw new SemesterControlException();
      }
    }

    this.students = students;
  }

}
