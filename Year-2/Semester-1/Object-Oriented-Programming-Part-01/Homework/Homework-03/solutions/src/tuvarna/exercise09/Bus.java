package tuvarna.exercise09;

public class Bus extends Car {

  private final int seats;
  private int courses;

  public Bus(String brand, String model, int power, String engine, String transmission, int productionYear, int seats, int courses) {
    super(brand, model, power, engine, transmission, productionYear);
    this.seats = seats;
    this.courses = courses;
  }

  public void increaseCourses() {
    this.courses++;
  }

  public boolean isCourseLimitExceeded() {
    return Math.abs(10 - courses) > 5;
  }

  public int getSeats() {
    return seats;
  }

  public int getCourses() {
    return courses;
  }
}
