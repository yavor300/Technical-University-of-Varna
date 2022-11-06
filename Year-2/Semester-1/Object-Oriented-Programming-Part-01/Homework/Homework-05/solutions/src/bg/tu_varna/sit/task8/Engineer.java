package bg.tu_varna.sit.task8;

class Engineer extends Employee {

  private final int numberOfMachines;

  Engineer(String firstName, String secondName, String thirdName, char gender, int age, int worksHour, double rate, int numberOfMachines) {
    super(firstName, secondName, thirdName, gender, age, worksHour, rate);
    this.numberOfMachines = numberOfMachines;
  }

  @Override
  public double calculate() {

    return super.calculate() + super.calculate() * 0.1 * numberOfMachines;
  }
}
