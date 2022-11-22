package bg.tu_varna.sit.task3;

public class Water extends Item {

  private final int mineralContent;

  public Water(String name, int daysToExpire, int availableQuantity, ItemType itemType, int mineralContent) {
    super(name, daysToExpire, availableQuantity, itemType);
    this.mineralContent = mineralContent;
  }

  @Override
  public boolean needsDelivery() {

    return getAvailableQuantity() < 30 || getDaysToExpire() < 20;
  }
}
