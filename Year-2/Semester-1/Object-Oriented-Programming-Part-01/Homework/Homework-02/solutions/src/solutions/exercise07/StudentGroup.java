package exercise07;

public class StudentGroup {

  private String speciality;
  private int course;
  private int groupNumber;
  private int studentsCount;

  public StudentGroup() {
  }

  public StudentGroup(String speciality, int course, int groupNumber, int studentsCount) {
    this.speciality = speciality;
    this.course = course;
    this.groupNumber = groupNumber;
    this.studentsCount = studentsCount;
  }

  @Override
  public String toString() {
    return String.format("Speciality: %s%nCourse: %d%nGroup: %d%nStudents: %d",
            getSpeciality(), getCourse(), getGroupNumber(), getStudentsCount());
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    StudentGroup other = (StudentGroup) o;

    if (!getSpeciality().equals(other.getSpeciality())) return false;
    if (getCourse() == (other.getCourse())) return false;
    return getGroupNumber() == (other.getGroupNumber());
  }

  public String getSpeciality() {
    return speciality;
  }

  public void setSpeciality(String speciality) {
    this.speciality = speciality;
  }

  public int getCourse() {
    return course;
  }

  public void setCourse(int course) {
    this.course = course;
  }

  public int getGroupNumber() {
    return groupNumber;
  }

  public void setGroupNumber(int groupNumber) {
    this.groupNumber = groupNumber;
  }

  public int getStudentsCount() {
    return studentsCount;
  }

  public void setStudentsCount(int studentsCount) {
    this.studentsCount = studentsCount;
  }
}
