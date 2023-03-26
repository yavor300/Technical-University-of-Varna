package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.base.Building;
import bg.tu_varna.sit.b1.f21621577.base.BuildingAbstractFactory;
import bg.tu_varna.sit.b1.f21621577.implementation.Architect;
import bg.tu_varna.sit.b1.f21621577.implementation.factory.ApartmentBuildingFactory;
import bg.tu_varna.sit.b1.f21621577.implementation.factory.BuildingFactory;
import bg.tu_varna.sit.b1.f21621577.implementation.factory.HouseBuildingFactory;

/**
 * Задача 1
 *
 * Приложете Factory pattern, за да съставите програма за скициране на сгради.
 * Скицирането представлява извежда на информация, за вида на сградата (къща, блок),
 * името на архитекта, площта и разгъната площ на сградата.
 * Къщата се определя от брой етажи, цена, площ, брой спални, брой бани
 * Блока се определя от брой етажи, цена, площ, брой апартаменти
 */
public class Main {

  public static void main(String[] args) {

    testAbstractFactory();
  }

  private static void testAbstractFactory() {

    Architect architect = new Architect("Tom", "Karn");

    Building apartment = BuildingFactory.getBuilding(new ApartmentBuildingFactory(architect, 75.45, 178.23, 3, 20000, 7));
    Building house = BuildingFactory.getBuilding(new HouseBuildingFactory(architect, 98.45, 323.23, 4, 40000, 7, 2));

    System.out.printf("AbstractFactory Config:%n\t" + apartment.sketch());
    System.out.println();
    System.out.printf("AbstractFactory Config:%n\t" + house.sketch());
  }
}
