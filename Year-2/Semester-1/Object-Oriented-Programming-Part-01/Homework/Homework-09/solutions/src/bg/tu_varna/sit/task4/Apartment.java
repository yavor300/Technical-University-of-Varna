package bg.tu_varna.sit.task4;

public class Apartment extends Property {

  private int floorNumber;
  private int numberOfRooms;
  private Exposition exposition;
  private boolean hasParkingPlace;

  public Apartment(double area, double basePrice, PropertyType propertyType,
                   int floorNumber, int numberOfRooms, Exposition exposition, boolean hasParkingPlace) {
    super(area, basePrice, propertyType);
    this.floorNumber = floorNumber;
    this.numberOfRooms = numberOfRooms;
    this.exposition = exposition;
    this.hasParkingPlace = hasParkingPlace;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Apartment apartment = (Apartment) o;

    if (floorNumber != apartment.floorNumber) return false;
    if (numberOfRooms != apartment.numberOfRooms) return false;
    if (hasParkingPlace != apartment.hasParkingPlace) return false;
    return exposition == apartment.exposition;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + floorNumber;
    result = 31 * result + numberOfRooms;
    result = 31 * result + exposition.hashCode();
    result = 31 * result + (hasParkingPlace ? 1 : 0);
    return result;
  }

  public int getFloorNumber() {
    return floorNumber;
  }

  public int getNumberOfRooms() {
    return numberOfRooms;
  }

  public Exposition getExposition() {
    return exposition;
  }

  public boolean isHasParkingPlace() {
    return hasParkingPlace;
  }

  @Override
  public double calculate() {

    switch (getPropertyType()) {

      case NEW:
        if ((getExposition() == Exposition.SOUTH || getExposition() == Exposition.WEST
                || exposition == Exposition.SOUTHWEST) && getArea() > 50 && hasParkingPlace) {
          return 1.25 * getBasePrice();
        }

        if ((getExposition() == Exposition.SOUTH || getExposition() == Exposition.WEST
                || exposition == Exposition.SOUTHWEST)) {
          return 1.20 * getBasePrice();
        }

        return 1.15 * getBasePrice();

      case OLD:
        if (hasParkingPlace) {
          return 1.18 * getBasePrice();
        }
        return 1.12 * getBasePrice();

      default:
        return 1.12 * getBasePrice();
    }
  }
}
