package bg.tu_varna.sit.task1;

public class Child {

  private final String name;
  private int age;
  private final double weight;

  public Child(String name, int age, String name1, double weight) throws InvalidAgeException {
    this.name = name1;
    setAge(age);
    this.weight = weight;
  }

  @Override
  public String toString() {
    return String.format("%snВъзраст: %d", super.toString(), age);
  }

  private void setAge(int age) throws InvalidAgeException {

    if (age < 3) {
      throw new InvalidAgeException();
    }
    this.age = age;
  }

  public double getWeight() {
    return weight;
  }

}
