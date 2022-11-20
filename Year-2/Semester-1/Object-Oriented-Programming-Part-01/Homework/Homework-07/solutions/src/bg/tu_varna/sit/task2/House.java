package bg.tu_varna.sit.task2;

public class House extends Property {

  private int numberOfFloors;
  private ParkingLot parkingLot;
  private final boolean hasGarden;

  public House(double area, double price, PropertyType propertyType, int numberOfFloors, ParkingLot parkingLot, boolean hasGarden) {
    super(area, price, propertyType);
    setNumberOfFloors(numberOfFloors);
    setParkingLot(parkingLot);
    this.hasGarden = hasGarden;
  }

  @Override
  public double calculateCommission() {

    if (isForRent() && hasGarden) {
      return 0.08 * getPrice();
    } else if (!isForRent() && getArea() < 100) {
      return 0.05 * getPrice();
    }

    return 0.03 * getPrice();
  }

  private void setNumberOfFloors(int numberOfFloors) {

    if (numberOfFloors <= 0) {
      throw new InvalidDataException("The number of floors must be a positive number!");
    }
    this.numberOfFloors = numberOfFloors;
  }

  private void setParkingLot(ParkingLot parkingLot) {

    if (parkingLot == null) {
      throw new InvalidDataException("Missing parking lot data!");
    }
    this.parkingLot = parkingLot;
  }
}
