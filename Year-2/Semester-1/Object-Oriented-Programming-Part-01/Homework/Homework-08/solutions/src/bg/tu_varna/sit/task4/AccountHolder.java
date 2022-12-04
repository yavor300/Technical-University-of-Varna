package bg.tu_varna.sit.task4;

public class AccountHolder {

  private final String firstName;
  private final String lastName;
  private final int age;

  public AccountHolder(String firstName, String lastName, int age) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
  }

  public int getAge() {
    return age;
  }
}
