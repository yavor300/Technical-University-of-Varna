package tuvarna.exercise10;

public class Circle {

  private static final double RADIUS_DEFAULT_VALUE = 3;

  private final double radius;

  public Circle() {
    this.radius = RADIUS_DEFAULT_VALUE;
  }

  public Circle(double radius) {
    this.radius = radius;
  }

  public double getArea() {
    return Math.PI * Math.pow(radius, 2);
  }
}
