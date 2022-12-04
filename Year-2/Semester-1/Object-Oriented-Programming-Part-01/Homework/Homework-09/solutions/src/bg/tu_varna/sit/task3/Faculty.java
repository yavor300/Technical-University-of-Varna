package bg.tu_varna.sit.task3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Faculty {

  private String facultyName;
  private final List<Student> students;

  public Faculty(String facultyName) {
    this.facultyName = facultyName;
    this.students = new ArrayList<>();
  }

  public boolean addStudent(Student student) {

    return students.add(student);
  }

  public double calculateAverageGrades() {

    if (students.size() == 0) {
      return 0;
    }

    double result = 0;
    for (Student student : students) {
      result += student.getGrades();
    }
    return result / students.size();
  }

  public int getNumberOfStudentsBySpeciality(Speciality speciality) {

    int result = 0;
    for (Student student : students) {
      if (student.getSpeciality() == speciality) {
        result++;
      }
    }
    return result;
  }

  public List<Student> getStudentsWithExcellentGrades() {

    return students.stream()
            .filter(student -> student.getGrades() >= 5.50)
            .collect(Collectors.toList());
  }

  public int getNumberOfStudentsByCourse(int course) {

    int result = 0;
    for (Student student : students) {
      if (student.getCourse() == course) {
        result++;
      }
    }
    return result;
  }

  @Override
  public String toString() {

    StringBuilder result = new StringBuilder();

    for (Student student : students) {
      result.append(student).append("\n");
    }

    return result.toString();
  }
}
