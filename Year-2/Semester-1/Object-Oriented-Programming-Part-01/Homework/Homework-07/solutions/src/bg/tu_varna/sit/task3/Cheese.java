package bg.tu_varna.sit.task3;

public class Cheese extends Item {

  private final int gramsPerPackage;

  public Cheese(String name, int daysToExpire, int availableQuantity, ItemType itemType, int gramsPerPackage) {
    super(name, daysToExpire, availableQuantity, itemType);
    this.gramsPerPackage = gramsPerPackage;
  }

  @Override
  public boolean needsDelivery() {

    return getAvailableQuantity() > 10 && gramsPerPackage >= 400;
  }
}
