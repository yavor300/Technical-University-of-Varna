package bg.tu_varna.sit.task3;

public class Shop {

  private final Item[] items;

  public Shop(Item[] items) {
    this.items = items;
  }

  public int getItemCountDelivery() {

    int result = 0;

    for (Item item : items) {
      if (item.needsDelivery()) {
        result++;
      }
    }

    return result;
  }

  public double getAverageDaysToExpire() {

    double result = 0;

    for (Item item : items) {
      result += item.getDaysToExpire();
    }

    return result / items.length;
  }

  public int getAvailableDrinksCount() {

    int result = 0;

    for (Item item : items) {
      if (item instanceof Drink) {
        result++;
      }
    }

    return result;
  }
}
