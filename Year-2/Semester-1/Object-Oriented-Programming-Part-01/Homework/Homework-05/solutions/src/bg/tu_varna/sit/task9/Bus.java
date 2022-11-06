package bg.tu_varna.sit.task9;

class Bus extends Car {

  private static final int COURSES_PER_DAY = 10;

  private int seats;
  private final int courses;

  Bus(String brand, String model, int power, String engineType, String transmission, int yearOfManufacture, int seats, int courses) {
    super(brand, model, power, engineType, transmission, yearOfManufacture);
    this.seats = seats;
    this.courses = courses;
  }

  @Override
  boolean startup() {

    return !isCoursesLimitExceeded();
  }

  private boolean isCoursesLimitExceeded() {

    return COURSES_PER_DAY - courses > 5;
  }
}
