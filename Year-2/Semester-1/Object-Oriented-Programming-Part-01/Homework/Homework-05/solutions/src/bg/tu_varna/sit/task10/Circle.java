package bg.tu_varna.sit.task10;

class Circle implements Area {

  private final double radius;

  Circle() {
    this.radius = 1.0;
  }

  Circle(double radius) {
    this.radius = radius;
  }

  @Override
  public double calculateArea() {
    return Math.PI * Math.pow(radius, 2);
  }

  double getRadius() {
    return radius;
  }
}
