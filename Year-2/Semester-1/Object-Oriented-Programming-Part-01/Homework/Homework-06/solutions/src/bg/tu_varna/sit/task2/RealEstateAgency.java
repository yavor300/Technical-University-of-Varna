package bg.tu_varna.sit.task2;

public class RealEstateAgency {

  private String name;
  private final Property[] properties;

  public RealEstateAgency(String name, Property[] properties) {
    this.name = name;
    this.properties = properties;
  }

  public double calculateTotalExpectedCommission() {

    double result = 0;

    for (Property property : properties) {
      result += property.calculateCommission();
    }

    return result;
  }

  public int getPropertiesForSale() {

    int result = 0;

    for (Property property : properties) {
      if (!property.isForRent()) {
        result++;
      }
    }

    return result;
  }
}
