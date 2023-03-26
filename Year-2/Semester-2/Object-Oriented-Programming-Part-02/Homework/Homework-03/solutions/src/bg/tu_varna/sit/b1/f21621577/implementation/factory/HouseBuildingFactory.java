package bg.tu_varna.sit.b1.f21621577.implementation.factory;

import bg.tu_varna.sit.b1.f21621577.base.Building;
import bg.tu_varna.sit.b1.f21621577.base.BuildingAbstractFactory;
import bg.tu_varna.sit.b1.f21621577.implementation.Architect;
import bg.tu_varna.sit.b1.f21621577.implementation.House;

public class HouseBuildingFactory extends House implements BuildingAbstractFactory {

  public HouseBuildingFactory(Architect architect, double area, double fullUnfoldArea, int floorsCount, double price, int bedroomsCount, int bathroomsCount) {
    super(architect, area, fullUnfoldArea, floorsCount, price, bedroomsCount, bathroomsCount);
  }

  @Override
  public Building createBuilding() {
    return new House(getArchitect(), getArea(), getFullUnfoldArea(), getFloorsCount(), getPrice(), getBedroomsCount(), getBathroomsCount());
  }
}
