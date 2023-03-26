package bg.tu_varna.sit.b1.f21621577.implementation;

import bg.tu_varna.sit.b1.f21621577.base.Building;

public class Apartment extends Building {

  private final int apartmentsCount;

  public Apartment(Architect architect, double area, double fullUnfoldArea, int floorsCount, double price, int apartmentsCount) {
    super(architect, area, fullUnfoldArea, floorsCount, price);
    this.apartmentsCount = apartmentsCount;
  }

  @Override
  public String toString() {
    return String.format("%s%n\t\tCount: %d", super.toString(), apartmentsCount);
  }

  public int getApartmentsCount() {
    return apartmentsCount;
  }
}
