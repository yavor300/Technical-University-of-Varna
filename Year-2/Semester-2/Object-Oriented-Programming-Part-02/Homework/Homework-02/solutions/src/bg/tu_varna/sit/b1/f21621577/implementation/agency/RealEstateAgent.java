package bg.tu_varna.sit.b1.f21621577.implementation.agency;

import bg.tu_varna.sit.b1.f21621577.base.Agent;
import bg.tu_varna.sit.b1.f21621577.implementation.property.Property;

public class RealEstateAgent extends Agent {

  private final RealEstateAgency realEstateAgency;

  public RealEstateAgent(String firstName, String lastName, String phoneNumber, RealEstateAgency realEstateAgency) {
    super(firstName, lastName, phoneNumber);
    this.realEstateAgency = realEstateAgency;
  }

  @Override
  public boolean addProperty(Property property) {

    return realEstateAgency.getProperties().add(property);
  }

  @Override
  public boolean deleteProperty(Property property) {

    return realEstateAgency.getProperties().remove(property);
  }
}
