package bg.tu_varna.sit.task10;

class Cylinder extends Cone {

  @Override
  public double calculateArea() {

    return 2 * Math.PI * getRadius() * getHeight() +
            2 * Math.PI * Math.pow(getRadius(), 2);
  }

  @Override
  public double calculateVolume() {

    return Math.PI * Math.pow(getRadius(), 2) * getHeight();
  }
}
