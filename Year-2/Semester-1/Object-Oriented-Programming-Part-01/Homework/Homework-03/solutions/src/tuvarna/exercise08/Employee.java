package tuvarna.exercise08;

public class Employee extends Human {

  private final int workingHoursPerDay;
  private final double salaryPerHour;

  protected Employee(String firstName, String secondName, String thirdName,
                  char gender, int age, int workingHoursPerDay, double salaryPerHour) {
    super(firstName, secondName, thirdName, gender, age);
    this.workingHoursPerDay = workingHoursPerDay;
    this.salaryPerHour = salaryPerHour;
  }

  @Override
  public String toString() {
    return String.format("%s%nWorking hours per day: %d%nSalary per hour: %.2f BGN.",
            super.toString(), workingHoursPerDay, salaryPerHour);
  }

  protected double basicSalary() {
    return workingHoursPerDay * salaryPerHour;
  }

  protected double getSalaryPerHour() {
    return salaryPerHour;
  }
}
