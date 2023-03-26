package bg.tu_varna.sit.b1.f21621577.implementation.factory;

import bg.tu_varna.sit.b1.f21621577.base.Building;
import bg.tu_varna.sit.b1.f21621577.base.BuildingAbstractFactory;

public class BuildingFactory {

  public static Building getBuilding(BuildingAbstractFactory factory){
    return factory.createBuilding();
  }
}
