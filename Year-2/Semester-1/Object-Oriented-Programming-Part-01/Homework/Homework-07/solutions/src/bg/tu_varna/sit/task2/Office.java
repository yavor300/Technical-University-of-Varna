package bg.tu_varna.sit.task2;

public class Office extends Property {

  private ParkingLot parkingLot;
  private int numberOfRooms;

  public Office(double area, double price, PropertyType propertyType, ParkingLot parkingLot, int numberOfRooms) {
    super(area, price, propertyType);
    setParkingLot(parkingLot);
    setNumberOfRooms(numberOfRooms);
  }

  @Override
  public double calculateCommission() {

    if (isForRent()) {
      return 0.18 * getPrice();
    } else if (hasParkingLot() && numberOfRooms > 2) {
      return 0.15 * getPrice();
    }

    return 0.11 * getPrice();
  }

  private boolean hasParkingLot() {

    return parkingLot == ParkingLot.noParkingLot;
  }

  public void setParkingLot(ParkingLot parkingLot) {

    if (parkingLot == null) {
      throw new InvalidDataException("Missing parking lot data!");
    }
    this.parkingLot = parkingLot;
  }

  public void setNumberOfRooms(int numberOfRooms) {

    if (numberOfRooms <= 0) {
      throw new InvalidDataException("The number of rooms must be a positive number!");
    }
    this.numberOfRooms = numberOfRooms;
  }
}
