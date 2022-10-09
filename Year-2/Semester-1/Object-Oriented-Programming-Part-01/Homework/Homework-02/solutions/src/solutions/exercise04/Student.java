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

  @Override
  public boolean equals(Object o) {

    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Student other = (Student) o;

    return id.equals(other.id);
  }

  @Override
  public String toString() {
    return String.format("First name: %s%nLast name: %s%nId: %s%nSpeciality: %s",
            getFirstName(), getLastName(), getId(), getSpeciality());
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
}
