package bg.tu_varna.sit.task1;

public abstract class Item implements Delivery, Comparable<Item> {

  private final ItemType itemType;
  private final double itemPrice;
  private final int availableQuantity;

  public Item(ItemType itemType, double itemPrice, int availableQuantity) {
    this.itemType = itemType;
    this.itemPrice = itemPrice;
    this.availableQuantity = availableQuantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Item item = (Item) o;

    if (Double.compare(item.itemPrice, itemPrice) != 0) return false;
    if (availableQuantity != item.availableQuantity) return false;
    return itemType == item.itemType;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    result = itemType.hashCode();
    temp = Double.doubleToLongBits(itemPrice);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + availableQuantity;
    return result;
  }

  @Override
  public int compareTo(Item o) {

    return Integer.compare(availableQuantity, o.getAvailableQuantity());
  }

  @Override
  public String toString() {

    return String.format("Item: %s%nItem type: %s%nPrice: %.2f lv.%nQuantity: %d",
            getClass().getSimpleName(), itemType, itemPrice, availableQuantity);
  }

  public ItemType getItemType() {
    return itemType;
  }

  public double getItemPrice() {
    return itemPrice;
  }

  public int getAvailableQuantity() {
    return availableQuantity;
  }
}
