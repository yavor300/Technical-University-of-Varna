package bg.tu_varna.sit.task3;

public class Cheese extends Item implements Food {

  private final int gramsPerPackage;

  public Cheese(String name, int daysToExpire, int availableQuantity, int gramsPerPackage) {
    super(name, daysToExpire, availableQuantity);
    this.gramsPerPackage = gramsPerPackage;
  }

  @Override
  public boolean needsDelivery() {

    return getAvailableQuantity() > 10 && gramsPerPackage >= 400;
  }
}
