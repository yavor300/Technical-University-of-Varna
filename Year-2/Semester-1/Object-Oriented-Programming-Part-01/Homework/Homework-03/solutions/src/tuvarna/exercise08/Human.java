package tuvarna.exercise08;

public class Human {

  private final String firstName;
  private final String secondName;
  private final String thirdName;
  private final char gender;
  private final int age;

  protected Human(String firstName, String secondName, String thirdName, char gender, int age) {
    this.firstName = firstName;
    this.secondName = secondName;
    this.thirdName = thirdName;
    this.gender = gender;
    this.age = age;
  }

  @Override
  public String toString() {
    return String.format("Human: %s%nFirst name: %s%nSecond name: %s%n" +
            "Third name: %s%nGender: %c%nAge: %d",
            getClass().getSimpleName(), firstName, secondName, thirdName, gender, age);
  }
}
