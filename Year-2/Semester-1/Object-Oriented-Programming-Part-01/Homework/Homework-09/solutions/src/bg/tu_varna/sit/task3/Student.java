package bg.tu_varna.sit.task3;

public class Student extends Person implements Comparable<Student> {

  private final String fNumber;
  private final Speciality speciality;
  private int course;
  private double grades;

  public Student(String firstName, String lastName, int age, String fNumber,
                 Speciality speciality, int course, double grades) throws InvalidDataException {
    super(firstName, lastName, age);
    this.fNumber = fNumber;
    this.speciality = speciality;
    setCourse(course);
    setGrades(grades);
  }

  @Override
  public int compareTo(Student o) {

    return fNumber.compareTo(o.fNumber);
  }

  @Override
  public String toString() {
    return "Student{" +
            "fNumber='" + fNumber + '\'' +
            ", speciality=" + speciality +
            ", course=" + course +
            ", grades=" + grades +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Student student = (Student) o;

    return fNumber.equals(student.fNumber);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + fNumber.hashCode();
    return result;
  }

  public String getfNumber() {
    return fNumber;
  }

  public Speciality getSpeciality() {
    return speciality;
  }

  public int getCourse() {
    return course;
  }

  public double getGrades() {
    return grades;
  }

  private void setCourse(int course) throws InvalidDataException {

    if (course <= 0 || course > 5) {
      throw new InvalidDataException("Invalid course number!");
    }
    this.course = course;
  }

  private void setGrades(double grades) throws InvalidDataException {

    if (grades <= 1 || grades > 6) {
      throw new InvalidDataException("Invalid grades value!");
    }
    this.grades = grades;
  }


}
