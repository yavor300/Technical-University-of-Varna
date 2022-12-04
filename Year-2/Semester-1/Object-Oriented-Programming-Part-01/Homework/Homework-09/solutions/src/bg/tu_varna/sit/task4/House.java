package bg.tu_varna.sit.task4;

public class House extends Property {

  private final int numberOfFloors;
  private final boolean hasGarden;

  public House(double area, double basePrice, PropertyType propertyType, int numberOfFloors, boolean hasGarden) {
    super(area, basePrice, propertyType);
    this.numberOfFloors = numberOfFloors;
    this.hasGarden = hasGarden;
  }

  @Override
  public double calculate() {

    if (getPropertyType() == PropertyType.NEW && hasGarden && numberOfFloors > 1) {
      return 1.20 * getBasePrice();
    } else if (hasGarden) {
      return 1.1 * getBasePrice();
    } else {
      return 1.07 * getBasePrice();
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    House house = (House) o;

    if (numberOfFloors != house.numberOfFloors) return false;
    return hasGarden == house.hasGarden;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + numberOfFloors;
    result = 31 * result + (hasGarden ? 1 : 0);
    return result;
  }

  @Override
  public String toString() {
    return "House{" +
            "numberOfFloors=" + numberOfFloors +
            ", hasGarden=" + hasGarden +
            '}';
  }

  public int getNumberOfFloors() {
    return numberOfFloors;
  }

  public boolean isHasGarden() {
    return hasGarden;
  }

}
