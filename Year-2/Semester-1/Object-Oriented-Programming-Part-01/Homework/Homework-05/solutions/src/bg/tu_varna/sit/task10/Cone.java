package bg.tu_varna.sit.task10;

class Cone extends Circle implements Volume {

  private final double height;

  Cone() {
    this.height = 1.0;
  }

  Cone(double height) {
    this.height = height;
  }

  Cone(double radius, double height) {
    super(radius);
    this.height = height;
  }

  @Override
  public double calculateArea() {

    return Math.PI * getRadius() *
            (getRadius() + Math.sqrt(Math.pow(height, 2) + Math.pow(getRadius(), 2)));
  }

  double getHeight() {
    return height;
  }

  @Override
  public double calculateVolume() {

    return Math.PI * Math.pow(getRadius(), 2) * (getHeight() / 3);
  }
}
