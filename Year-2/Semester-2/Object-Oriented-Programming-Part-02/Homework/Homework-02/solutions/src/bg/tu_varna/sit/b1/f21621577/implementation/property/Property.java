package bg.tu_varna.sit.b1.f21621577.implementation.property;

import java.util.UUID;

public class Property {

  private final String id;
  private final PropertyType type;
  private final double area;
  private final double price;
  private final int roomsCount;
  private final boolean isFurnished;
  private final boolean hasGarage;
  private final boolean hasParkingLot;
  private final boolean hasGarden;

  private Property(PropertyBuilder builder) {
    this.id = UUID.randomUUID().toString();
    this.type = builder.type;
    this.area = builder.area;
    this.price = builder.price;
    this.roomsCount = builder.roomsCount;
    this.isFurnished = builder.isFurnished;
    this.hasGarage = builder.hasGarage;
    this.hasParkingLot = builder.hasParkingLot;
    this.hasGarden = builder.hasGarden;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Property property = (Property) o;

    return id.equals(property.id);
  }

  @Override
  public int hashCode() {

    return id.hashCode();
  }


  /**
   * Used just to visualize the property properties
   * and show the current state of the present properties in the agency.
   * The method is not optimized to hide empty optional properties.
   *
   * @return String representation of the property
   */
  @Override
  public String toString() {
    return "Property{" +
            "id='" + id + '\'' +
            ", type=" + type +
            ", area=" + area +
            ", price=" + price +
            ", roomsCount=" + roomsCount +
            ", isFurnished=" + isFurnished +
            ", hasGarage=" + hasGarage +
            ", hasParkingLot=" + hasParkingLot +
            ", hasGarden=" + hasGarden +
            '}';
  }

  public static class PropertyBuilder {

    private final PropertyType type;
    private double area;
    private final double price;
    private int roomsCount;
    private boolean isFurnished;
    private boolean hasGarage;
    private boolean hasParkingLot;
    private boolean hasGarden;

    public PropertyBuilder(PropertyType propertyType, double price) {
      this.type = propertyType;
      this.price = price;
    }

    public PropertyBuilder setArea(double area) {
      this.area = area;
      return this;
    }

    public PropertyBuilder setRoomsCount(int roomsCount) {
      this.roomsCount = roomsCount;
      return this;
    }

    public PropertyBuilder setFurnished(boolean furnished) {
      isFurnished = furnished;
      return this;
    }

    public PropertyBuilder setHasGarage(boolean hasGarage) {
      this.hasGarage = hasGarage;
      return this;
    }

    public PropertyBuilder setHasParkingLot(boolean hasParkingLot) {
      this.hasParkingLot = hasParkingLot;
      return this;
    }

    public PropertyBuilder setHasGarden(boolean hasGarden) {
      this.hasGarden = hasGarden;
      return this;
    }

    public Property build() {
      return new Property(this);
    }
  }
}
