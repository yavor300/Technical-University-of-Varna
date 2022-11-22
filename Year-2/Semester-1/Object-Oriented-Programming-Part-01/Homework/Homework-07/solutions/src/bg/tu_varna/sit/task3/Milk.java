package bg.tu_varna.sit.task3;

public class Milk extends Item {

  private final int fatPercentage;

  public Milk(String name, int daysToExpire, int availableQuantity, ItemType itemType, int fatPercentage) {
    super(name, daysToExpire, availableQuantity, itemType);
    this.fatPercentage = fatPercentage;
  }

  @Override
  public boolean needsDelivery() {

    return getDaysToExpire() < 15 && getAvailableQuantity() < 10 && fatPercentage > 2;
  }
}
