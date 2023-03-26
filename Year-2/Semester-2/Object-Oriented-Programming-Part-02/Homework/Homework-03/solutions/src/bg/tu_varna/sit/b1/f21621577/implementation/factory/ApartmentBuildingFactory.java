package bg.tu_varna.sit.b1.f21621577.implementation.factory;

import bg.tu_varna.sit.b1.f21621577.base.Building;
import bg.tu_varna.sit.b1.f21621577.base.BuildingAbstractFactory;
import bg.tu_varna.sit.b1.f21621577.implementation.Apartment;
import bg.tu_varna.sit.b1.f21621577.implementation.Architect;

public class ApartmentBuildingFactory extends Apartment implements BuildingAbstractFactory {

  public ApartmentBuildingFactory(Architect architect, double area, double fullUnfoldArea, int floorsCount, double price, int apartmentsCount) {
    super(architect, area, fullUnfoldArea, floorsCount, price, apartmentsCount);
  }

  @Override
  public Building createBuilding() {
    return new Apartment(getArchitect(), getArea(), getFullUnfoldArea(), getFloorsCount(), getPrice(), getApartmentsCount());
  }
}
