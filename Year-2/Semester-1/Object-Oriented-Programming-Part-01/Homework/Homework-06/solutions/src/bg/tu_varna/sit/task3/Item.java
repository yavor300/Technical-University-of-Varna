package bg.tu_varna.sit.task3;

public abstract class Item implements Delivery {

  private final String name;
  private final int daysToExpire;
  private final int availableQuantity;

  protected Item(String name, int daysToExpire, int availableQuantity) {
    this.name = name;
    this.daysToExpire = daysToExpire;
    this.availableQuantity = availableQuantity;
  }

  public String getName() {
    return name;
  }

  public int getDaysToExpire() {
    return daysToExpire;
  }

  public int getAvailableQuantity() {
    return availableQuantity;
  }
}
