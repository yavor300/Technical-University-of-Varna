package tuvarna.exercise08;

public class Engineer extends Employee {

  private final int createdMachinesPerDay;

  public Engineer(String firstName, String secondName, String thirdName, char gender, int age, int workingHoursPerDay, double salaryPerHour, int createdMachinesPerDay) {
    super(firstName, secondName, thirdName, gender, age, workingHoursPerDay, salaryPerHour);
    this.createdMachinesPerDay = createdMachinesPerDay;
  }

  public double engineerSalary() {
    return super.basicSalary() + (super.getSalaryPerHour() * 0.1) * createdMachinesPerDay;
  }

  @Override
  public String toString() {
    return String.format("%s%nCreated machines per day: %d%nTotal salary: %.2f BGN.",
            super.toString(), createdMachinesPerDay, engineerSalary());
  }
}
