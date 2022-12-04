package bg.tu_varna.sit.task4;

public abstract class Property implements PriceCalculator, Comparable<Property> {

  private final double area;
  private final double basePrice;
  private final PropertyType propertyType;

  protected Property(double area, double basePrice, PropertyType propertyType) {
    this.area = area;
    this.basePrice = basePrice;
    this.propertyType = propertyType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Property property = (Property) o;

    if (Double.compare(property.area, area) != 0) return false;
    if (Double.compare(property.basePrice, basePrice) != 0) return false;
    return propertyType == property.propertyType;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    temp = Double.doubleToLongBits(area);
    result = (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(basePrice);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + propertyType.hashCode();
    return result;
  }

  @Override
  public int compareTo(Property o) {

    return Double.compare(area, o.area);
  }

  @Override
  public String toString() {
    return "Property{" +
            "area=" + area +
            ", basePrice=" + basePrice +
            ", propertyType=" + propertyType +
            '}';
  }

  public double getArea() {
    return area;
  }

  public double getBasePrice() {
    return basePrice;
  }

  public PropertyType getPropertyType() {
    return propertyType;
  }
}
