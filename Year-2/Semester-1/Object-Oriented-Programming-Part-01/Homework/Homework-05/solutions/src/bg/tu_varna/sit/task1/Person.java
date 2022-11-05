package bg.tu_varna.sit.task1;

public class Person {

  private String firstName;
  private String secondName;
  private String thirdName;

  Person(String firstName, String secondName, String thirdName) {
    this.firstName = firstName;
    this.secondName = secondName;
    this.thirdName = thirdName;
  }

  @Override
  public String toString() {
    return String.format("%s %s %s", firstName, secondName, thirdName);
  }

  String getFirstName() {
    return firstName;
  }

  void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  String getSecondName() {
    return secondName;
  }

  void setSecondName(String secondName) {
    this.secondName = secondName;
  }

  String getThirdName() {
    return thirdName;
  }

  void setThirdName(String thirdName) {
    this.thirdName = thirdName;
  }
}
