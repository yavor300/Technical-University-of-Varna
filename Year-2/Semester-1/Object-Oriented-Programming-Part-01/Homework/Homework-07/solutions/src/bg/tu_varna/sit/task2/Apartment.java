package bg.tu_varna.sit.task2;

public class Apartment extends Property {

  private int numberOfRooms;
  private int floor;
  private ParkingLot parkingLot;

  public Apartment(double area, double price, PropertyType propertyType, int numberOfRooms, int floor, ParkingLot parkingLot) {
    super(area, price, propertyType);
    setNumberOfRooms(numberOfRooms);
    setFloor(floor);
    setParkingLot(parkingLot);
  }

  @Override
  public double calculateCommission() {

    if (isForRent() && (parkingLot == ParkingLot.onePlaceForRent ||
            parkingLot == ParkingLot.twoPlacesForRent)) {
      return 0.15 * getPrice();
    } else if (getArea() < 60) {
      return 0.10 * getPrice();
    }

    return 0.07 * getPrice();
  }

  private void setNumberOfRooms(int numberOfRooms) {

    if (numberOfRooms <= 0) {
      throw new InvalidDataException("Number of rooms must be greater than 0!");
    }
    this.numberOfRooms = numberOfRooms;
  }

  private void setFloor(int floor) {

    if (numberOfRooms < 0) {
      throw new InvalidDataException("Floor must be positive number or 0 for initial floor!");
    }
    this.floor = floor;
  }

  private void setParkingLot(ParkingLot parkingLot) {

    if (parkingLot == null) {
      throw new InvalidDataException("Missing parking lot data!");
    }
    this.parkingLot = parkingLot;
  }
}
