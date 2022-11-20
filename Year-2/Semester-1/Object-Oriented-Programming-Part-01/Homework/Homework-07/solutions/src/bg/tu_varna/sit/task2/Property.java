package bg.tu_varna.sit.task2;

public abstract class Property implements Commission {

  private double area;
  private double price;
  private PropertyType propertyType;

  protected Property(double area, double price, PropertyType propertyType) {
    setArea(area);
    setPrice(price);
    setPropertyType(propertyType);
  }

  protected boolean isForRent() {

    return propertyType == PropertyType.rent;
  }

  protected double getArea() {
    return area;
  }

  protected double getPrice() {
    return price;
  }

  private void setArea(double area) {

    if (area <= 0) {
      throw new InvalidDataException("Area must be greater than 0!");
    }
    this.area = area;
  }

  private void setPrice(double price) {

    if (price <= 0) {
      throw new InvalidDataException("Price must be greater than 0!");
    }
    this.price = price;
  }

  private void setPropertyType(PropertyType propertyType) {

    if (propertyType == null) {
      throw new InvalidDataException("Property type must be provided!");
    }
    this.propertyType = propertyType;
  }
}
