package exercise07;

public class StudentGroup {
  private String speciality;
  private Integer course;
  private Integer groupNumber;
  private Integer studentsCount;

  public StudentGroup() {
  }

  public StudentGroup(String speciality, Integer course, Integer groupNumber, Integer studentsCount) {
    this.speciality = speciality;
    this.course = course;
    this.groupNumber = groupNumber;
    this.studentsCount = studentsCount;
  }

  public String getSpeciality() {
    return speciality;
  }

  public void setSpeciality(String speciality) {
    this.speciality = speciality;
  }

  public Integer getCourse() {
    return course;
  }

  public void setCourse(Integer course) {
    this.course = course;
  }

  public Integer getGroupNumber() {
    return groupNumber;
  }

  public void setGroupNumber(Integer groupNumber) {
    this.groupNumber = groupNumber;
  }

  public Integer getStudentsCount() {
    return studentsCount;
  }

  public void setStudentsCount(Integer studentsCount) {
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

    StudentGroup studentGroup = (StudentGroup) o;

    if (!getSpeciality().equals(studentGroup.getSpeciality())) return false;
    if (!getCourse().equals(studentGroup.getCourse())) return false;
    return getGroupNumber().equals(studentGroup.getGroupNumber());
  }
}
