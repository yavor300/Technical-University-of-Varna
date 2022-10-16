package tuvarna.exercise10;

public class Cylinder extends Circle {

  private static final double HEIGHT_DEFAULT_VALUE = 6;

  private final double height;

  public Cylinder() {
    super();
    this.height = HEIGHT_DEFAULT_VALUE;
  }

  public Cylinder(double radius, double height) {
    super(radius);
    this.height = height;
  }

  public double getVolume() {
    return super.getArea() * height;
  }
}
