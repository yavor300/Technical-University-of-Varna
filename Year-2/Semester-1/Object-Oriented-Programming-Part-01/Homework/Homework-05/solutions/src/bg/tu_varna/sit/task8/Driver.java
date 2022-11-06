package bg.tu_varna.sit.task8;

class Driver extends Employee {

  private DrivingLicense drivingLicense;
  private final int courses;

  Driver(String firstName, String secondName, String thirdName, char gender, int age, int worksHour, double rate, DrivingLicense drivingLicense, int courses) {
    super(firstName, secondName, thirdName, gender, age, worksHour, rate);
    this.drivingLicense = drivingLicense;
    this.courses = courses;
  }

  @Override
  public double calculate() {

    if (courses > 10) {
      return super.calculate() * 1.05;
    }

    return super.calculate();
  }
}
