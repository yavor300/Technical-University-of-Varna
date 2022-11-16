package bg.tu_varna.sit.task2;

public class Office extends Property {

  private final boolean hasParkingLot;
  private final int numberOfRooms;

  protected Office(double area, double price, boolean isForRent, boolean hasParkingLot, int numberOfRooms) {
    super(area, price, isForRent);
    this.hasParkingLot = hasParkingLot;
    this.numberOfRooms = numberOfRooms;
  }

  @Override
  public double calculateCommission() {

    if (isForRent()) {
      return 0.18 * getPrice();
    } else if (hasParkingLot && numberOfRooms > 2) {
      return 0.15 * getPrice();
    }

    return 0.11 * getPrice();
  }
}
