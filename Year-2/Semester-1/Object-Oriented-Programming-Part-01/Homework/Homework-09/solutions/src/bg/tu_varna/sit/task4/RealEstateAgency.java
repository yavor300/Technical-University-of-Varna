package bg.tu_varna.sit.task4;

import bg.tu_varna.sit.task3.Student;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RealEstateAgency {

  private final List<Property> properties;

  public RealEstateAgency(List<Property> properties) {
    this.properties = properties;
  }

  public boolean addProperty(Property property) {

    return properties.add(property);
  }

  public double calculatePriceOfAllProperties() {

    double result = 0;
    for (Property property : properties) {
      result += property.calculate();
    }
    return result;
  }

  public List<Property> sortPropertiesByArea() {

    return properties.stream()
            .sorted(Property::compareTo)
            .collect(Collectors.toList());
  }

  public List<Property> sortPropertiesByPrice() {

    return properties.stream()
            .sorted(Comparator.comparingDouble(Property::calculate))
            .collect(Collectors.toList());
  }

  public int getNumberOfPropertiesByType(PropertyType type) {

    int result = 0;
    for (Property property : properties) {
      if (property.getPropertyType() == type) {
        result++;
      }
    }
    return result;
  }

  public int getNumberOfAvailableHouses() {

    int result = 0;
    for (Property property : properties) {
      if ("House".equals(property.getClass().getSimpleName())) {
        result++;
      }
    }
    return result;
  }

  public Property getMostExpensiveApartment() {

    double max = 0;
    Property apartment = null;

    for (Property property : properties) {
      if ("Apartment".equals(property.getClass().getSimpleName()) && property.calculate() > max) {
        max = property.calculate();
        apartment = property;
      }
    }
    return apartment;
  }

  public double calculateAverageHousePrice() {

    double result = 0;
    int count = 0;
    for (Property property : properties) {
      if ("House".equals(property.getClass().getSimpleName())) {
        count++;
        result += property.calculate();
      }
    }

    if (count == 0) {
      return 0;
    }

    return result / count;
  }

  @Override
  public String toString() {

    StringBuilder result = new StringBuilder();

    for (Property property : properties) {
      result.append(property).append("\n");
    }

    return result.toString();
  }
}
