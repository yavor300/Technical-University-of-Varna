package tuvarna.exercise10;

public class Cone extends Circle {

  private static final double HEIGHT_DEFAULT_VALUE = 6;

  private final double height;

  public Cone() {
    super();
    this.height = HEIGHT_DEFAULT_VALUE;
  }

  public Cone(double radius, double height) {
    super(radius);
    this.height = height;
  }

  public double getVolume() {
    return super.getArea() * height / 3;
  }
}
