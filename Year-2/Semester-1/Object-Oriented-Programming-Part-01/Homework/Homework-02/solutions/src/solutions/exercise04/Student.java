package exercise04;

public class Student {
  private String firstName;
  private String lastName;
  private String id;
  private String speciality;

  public Student(String firstName, String lastName, String id, String speciality) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.id = id;
    this.speciality = speciality;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSpeciality() {
    return speciality;
  }

  public void setSpeciality(String speciality) {
    this.speciality = speciality;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Student student = (Student) o;

    return id.equals(student.id);
  }

  @Override
  public String toString() {
    return String.format("Student:%n\tFirst name: %s%n\tLast name: %s%n\tId: %s%n\tSpeciality: %s",
            firstName, lastName, id, speciality);
  }
}
