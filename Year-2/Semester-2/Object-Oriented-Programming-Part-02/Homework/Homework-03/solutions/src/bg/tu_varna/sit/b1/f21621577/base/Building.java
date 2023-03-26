package bg.tu_varna.sit.b1.f21621577.base;

import bg.tu_varna.sit.b1.f21621577.implementation.Architect;

public abstract class Building {

  private final Architect architect;
  private final double area;
  private final double fullUnfoldArea;
  private final int floorsCount;
  private final double price;

  protected Building(Architect architect, double area, double fullUnfoldArea, int floorsCount, double price) {
    this.architect = architect;
    this.area = area;
    this.fullUnfoldArea = fullUnfoldArea;
    this.floorsCount = floorsCount;
    this.price = price;
  }

  public abstract String sketch();

  @Override
  public String toString() {
    return String.format("%s:%n\t\t%s%n\t\tArea: %.2f%n\t\tFull unfold area: %.2f%n\t\tFloors count: %d%n\t\tPrice: %.2f BGN",
            getClass().getSimpleName(), architect, area, fullUnfoldArea, floorsCount, price);
  }

  protected Architect getArchitect() {
    return architect;
  }

  protected double getArea() {
    return area;
  }

  protected double getFullUnfoldArea() {
    return fullUnfoldArea;
  }

  protected int getFloorsCount() {
    return floorsCount;
  }

  protected double getPrice() {
    return price;
  }
}
