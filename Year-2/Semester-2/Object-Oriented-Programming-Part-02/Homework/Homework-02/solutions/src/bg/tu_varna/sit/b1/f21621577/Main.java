package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.base.Agent;
import bg.tu_varna.sit.b1.f21621577.implementation.agency.RealEstateAgency;
import bg.tu_varna.sit.b1.f21621577.implementation.agency.RealEstateAgent;
import bg.tu_varna.sit.b1.f21621577.implementation.property.Property;
import bg.tu_varna.sit.b1.f21621577.implementation.property.PropertyType;

public class Main {

  public static void main(String[] args) {

    RealEstateAgency realEstateAgency = RealEstateAgency.getInstance();

    Agent firstAgent = new RealEstateAgent("Terry", "Wheeler", "+1 209-397-1118", realEstateAgency);
    Agent secondAgent = new RealEstateAgent("Elena", "Cisneros", "+1 505-646-2627", realEstateAgency);
    Agent thirdAgent = new RealEstateAgent("Sidney", "Leon", "+1 505-646-7171", realEstateAgency);

    Property house = new Property.PropertyBuilder(PropertyType.HOUSE, 34785.00)
            .setArea(76)
            .setRoomsCount(2)
            .build();
    Property office = new Property.PropertyBuilder(PropertyType.OFFICE, 75988.00)
            .setRoomsCount(15)
            .setFurnished(true)
            .build();
    Property apartment = new Property.PropertyBuilder(PropertyType.APARTMENT, 124678.00)
            .setArea(99.73)
            .setFurnished(true)
            .setHasGarage(true)
            .setHasGarden(false)
            .setHasParkingLot(true)
            .setRoomsCount(4)
            .build();

    firstAgent.addProperty(house);
    secondAgent.addProperty(office);
    thirdAgent.addProperty(apartment);

    System.out.println(realEstateAgency.showAllProperties());

    if (firstAgent.deleteProperty(house)) {
      System.out.println("Property is deleted successfully!");
    } else {
      System.out.println("Property deletion failed!");
    }

    System.out.println(realEstateAgency.showAllProperties());
  }
}
