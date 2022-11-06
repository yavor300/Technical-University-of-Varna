package bg.tu_varna.sit.task6;

abstract class Building {

  private String address;
  private final double width;
  private final double length;
  private final double height;

  protected Building(String address, double width, double length, double height) {
    this.address = address;
    this.width = width;
    this.length = length;
    this.height = height;
  }

  abstract double heatedVolume();

  abstract double totalArea();

  protected double getWidth() {
    return width;
  }

  protected double getLength() {
    return length;
  }

  protected double getHeight() {
    return height;
  }
}
