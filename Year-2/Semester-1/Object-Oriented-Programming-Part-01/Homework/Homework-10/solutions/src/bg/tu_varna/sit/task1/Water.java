package bg.tu_varna.sit.task1;

public class Water extends Item {

  private final String name;
  private final WaterType waterType;

  public Water(ItemType itemType, double itemPrice, int availableQuantity, String name, WaterType waterType) {
    super(itemType, itemPrice, availableQuantity);
    this.name = name;
    this.waterType = waterType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Water water = (Water) o;

    if (!name.equals(water.name)) return false;
    return waterType == water.waterType;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + name.hashCode();
    result = 31 * result + waterType.hashCode();
    return result;
  }

  @Override
  public boolean needsDelivery() {

    return ((waterType == WaterType.SODA) && getAvailableQuantity() < 30) || getAvailableQuantity() < 10;
  }

  @Override
  public String toString() {
    return String.format("%s%nName: %s%nWater type: %s", super.toString(), name, waterType);
  }

  public String getName() {
    return name;
  }

  public WaterType getWaterType() {
    return waterType;
  }
}
