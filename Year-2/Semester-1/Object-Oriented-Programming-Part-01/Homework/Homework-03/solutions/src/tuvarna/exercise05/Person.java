package tuvarna.exercise05;

public class Person {

  private String firstName;
  private String secondName;
  private String thirdName;

  protected Person(String firstName, String secondName, String thirdName) {
    this.firstName = firstName;
    this.secondName = secondName;
    this.thirdName = thirdName;
  }

  protected String iAm() {
    return String.format("%s %s %s", firstName, secondName, thirdName);
  }

  protected String getFirstName() {
    return firstName;
  }

  protected void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  protected String getSecondName() {
    return secondName;
  }

  protected void setSecondName(String secondName) {
    this.secondName = secondName;
  }

  protected String getThirdName() {
    return thirdName;
  }

  protected void setThirdName(String thirdName) {
    this.thirdName = thirdName;
  }
}
