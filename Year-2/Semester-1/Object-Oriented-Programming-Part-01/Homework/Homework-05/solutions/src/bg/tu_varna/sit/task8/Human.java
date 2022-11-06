package bg.tu_varna.sit.task8;

class Human {

  private String firstName;
  private String secondName;
  private String thirdName;
  private char gender;
  private int age;

  protected Human(String firstName, String secondName, String thirdName, char gender, int age) {
    this.firstName = firstName;
    this.secondName = secondName;
    this.thirdName = thirdName;
    this.gender = gender;
    this.age = age;
  }
}
