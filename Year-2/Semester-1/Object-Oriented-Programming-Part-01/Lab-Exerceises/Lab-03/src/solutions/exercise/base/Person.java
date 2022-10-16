package solutions.exercise.base;

public class Person {

  private String name;
  private int age;

  protected Person(String name, int age) {
    this.name = name;
    this.age = age;
  }

  protected Person(String name) {
    this(name, 0);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}
