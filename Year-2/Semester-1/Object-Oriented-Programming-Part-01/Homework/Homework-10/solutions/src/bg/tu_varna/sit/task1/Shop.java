package bg.tu_varna.sit.task1;

import java.util.HashSet;
import java.util.Set;

public class Shop {

  private final Set<Item> items;

  public Shop() {
    this.items = new HashSet<>();
  }

  public boolean addItem(Item item) {

    return items.add(item);
  }

  public int countItemsForDelivery() {

    int result = 0;
    for (Item item : items) {
      if (item.needsDelivery()) {
        result++;
      }
    }

    return result;
  }

  public double calculateItemsPrice() {

    double result = 0;
    for (Item item : items) {
      result += item.getItemPrice();
    }

    return result;
  }

  public Item getItemWithMostAvailableQuantity() {

    int max = Integer.MIN_VALUE;
    Item result = null;

    for (Item item : items) {
      if (item.getAvailableQuantity() > max) {
        max = item.getAvailableQuantity();
        result = item;
      }
    }

    return result;
  }

  public ItemType getItemTypeWithLeastQuantity() {

    int min = Integer.MAX_VALUE;
    ItemType result = null;

    for (Item item : items) {
      if (item.getAvailableQuantity() < min) {
        min = item.getAvailableQuantity();
        result = item.getItemType();
      }
    }

    return result;
  }

  @Override
  public String toString() {

    StringBuilder stringBuilder = new StringBuilder();
    for (Item item : items) {
      stringBuilder.append(item).append("\n");
    }

    return stringBuilder.toString();
  }
}
