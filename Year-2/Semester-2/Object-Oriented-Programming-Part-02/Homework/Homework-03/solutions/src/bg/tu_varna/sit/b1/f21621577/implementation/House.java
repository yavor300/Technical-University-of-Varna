package bg.tu_varna.sit.b1.f21621577.implementation;

import bg.tu_varna.sit.b1.f21621577.base.Building;

public class House extends Building {

  private final int bedroomsCount;
  private final int bathroomsCount;

  public House(Architect architect, double area, double fullUnfoldArea, int floorsCount, double price, int bedroomsCount, int bathroomsCount) {
    super(architect, area, fullUnfoldArea, floorsCount, price);
    this.bedroomsCount = bedroomsCount;
    this.bathroomsCount = bathroomsCount;
  }

  @Override
  public String sketch() {
    return String.format("%s%n\t\tBedrooms count: %d%n\t\tBathrooms count: %d",
            super.toString(), bedroomsCount, bathroomsCount);
  }

  public int getBedroomsCount() {
    return bedroomsCount;
  }

  public int getBathroomsCount() {
    return bathroomsCount;
  }
}
