package bg.tu_varna.sit.task6;

class Block extends Building {

  private final int entrances;
  private final int apartments;

  Block(String address, double width, double length, double height, int entrances, int apartments) {
    super(address, width, length, height);
    this.entrances = entrances;
    this.apartments = apartments;
  }

  @Override
  double heatedVolume() {

    return (2.45 * totalArea()) * 0.9;
  }

  @Override
  double totalArea() {

    return calculateAreaPerApartment() * apartments * entrances;
  }

  private double calculateAreaPerApartment() {

    return getWidth() * getHeight();
  }
}
