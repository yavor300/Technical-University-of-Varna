package bg.tu_varna.sit.task3;

public abstract class Item implements Delivery {

  private final String name;
  private final int daysToExpire;
  private final int availableQuantity;
  private final ItemType itemType;

  protected Item(String name, int daysToExpire, int availableQuantity, ItemType itemType) {
    this.name = name;
    this.daysToExpire = daysToExpire;
    this.availableQuantity = availableQuantity;
    this.itemType = itemType;
  }

  protected ItemType getItemType() {
    return itemType;
  }

  protected int getDaysToExpire() {
    return daysToExpire;
  }

  protected int getAvailableQuantity() {
    return availableQuantity;
  }
}
