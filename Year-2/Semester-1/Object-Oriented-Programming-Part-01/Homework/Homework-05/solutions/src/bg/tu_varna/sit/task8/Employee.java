package bg.tu_varna.sit.task8;

class Employee extends Human implements Salary {

  private final int worksHour;
  private final double rate;

  protected Employee(String firstName, String secondName, String thirdName, char gender, int age, int worksHour, double rate) {
    super(firstName, secondName, thirdName, gender, age);
    this.worksHour = worksHour;
    this.rate = rate;
  }

  @Override
  public double calculate() {

    return worksHour * rate;
  }
}
