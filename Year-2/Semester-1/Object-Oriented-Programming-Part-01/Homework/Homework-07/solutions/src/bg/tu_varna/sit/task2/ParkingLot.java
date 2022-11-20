package bg.tu_varna.sit.task2;

public enum ParkingLot {

  noParkingLot(0, PropertyType.undefined),
  onePlaceForRent(1, PropertyType.rent),
  onePlaceForSale(1, PropertyType.sale),
  twoPlacesForRent(2, PropertyType.rent),
  twoPlacesForSale(2, PropertyType.sale),
  placesForRent(PropertyType.rent),
  placesForSale(PropertyType.sale);


  private final int numberOfParkingPlaces;
  private final PropertyType propertyType;

  ParkingLot(int numberOfParkingPlaces, PropertyType propertyType) {
    this.numberOfParkingPlaces = numberOfParkingPlaces;
    this.propertyType = propertyType;
  }

  ParkingLot(PropertyType propertyType) {
    this.propertyType = propertyType;
    this.numberOfParkingPlaces = 100;
  }

  public int getNumberOfParkingPlaces() {
    return numberOfParkingPlaces;
  }

  public PropertyType getPropertyType() {
    return propertyType;
  }
}
