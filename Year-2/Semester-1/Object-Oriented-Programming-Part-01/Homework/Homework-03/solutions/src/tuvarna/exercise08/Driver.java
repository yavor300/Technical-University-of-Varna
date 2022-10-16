package tuvarna.exercise08;

public class Driver extends Employee {

  private final DrivingLicense drivingLicense;
  private final int drivingCoursesPerDay;

  public Driver(String firstName, String secondName, String thirdName, char gender, int age, int workingHoursPerDay, double salaryPerHour, DrivingLicense drivingLicense, int drivingCoursesPerDay) {
    super(firstName, secondName, thirdName, gender, age, workingHoursPerDay, salaryPerHour);
    this.drivingLicense = drivingLicense;
    this.drivingCoursesPerDay = drivingCoursesPerDay;
  }

  public double driverSalary() {

    if (drivingCoursesPerDay > 10) {
      return super.basicSalary() * 1.05;
    }

    return super.basicSalary();
  }

  @Override
  public String toString() {
    return String.format("%s%n%s%nDriving courses per day: %d%nFinal salary: %.2f BGN.",
            super.toString(), drivingLicense, drivingCoursesPerDay, driverSalary());
  }
}
