package bg.tu_varna.sit.b1.f21621577.implementation.agency;

import bg.tu_varna.sit.b1.f21621577.implementation.property.Property;

import java.util.ArrayList;
import java.util.List;

public class RealEstateAgency {

  private final List<Property> properties = new ArrayList<>();

  private RealEstateAgency() {}

  private static class SingletonHelper {
    private static final RealEstateAgency INSTANCE
            = new RealEstateAgency();
  }

  public static RealEstateAgency getInstance() {
    return SingletonHelper.INSTANCE;
  }

  public String showAllProperties() {

    StringBuilder stringBuilder = new StringBuilder();

    for (Property property : properties) {
      stringBuilder.append(property).append(System.lineSeparator());
    }

    return stringBuilder.toString();
  }

  List<Property> getProperties() {

    return this.properties;
  }
}
