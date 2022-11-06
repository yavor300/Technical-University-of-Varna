package bg.tu_varna.sit.task6;

class House extends Building {

  private String owner;
  private final int floors;

  House(String address, double width, double length, double height, String owner, int floors) {
    super(address, width, length, height);
    this.owner = owner;
    this.floors = floors;
  }

  @Override
  double heatedVolume() {

    return (calculateAreaPerFloor() * getHeight() * floors) * 0.75;
  }

  @Override
  double totalArea() {

    return calculateAreaPerFloor() * floors;
  }

  private double calculateAreaPerFloor() {

    return getWidth() * getLength();
  }
}
