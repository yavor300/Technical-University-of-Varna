package bg.tu_varna.sit.task1;

public class Milk extends Item {

  private final String name;
  private final int daysToExpire;

  public Milk(ItemType itemType, double itemPrice, int availableQuantity, String name, int daysToExpire) {
    super(itemType, itemPrice, availableQuantity);
    this.name = name;
    this.daysToExpire = daysToExpire;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Milk milk = (Milk) o;

    if (daysToExpire != milk.daysToExpire) return false;
    return name.equals(milk.name);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + name.hashCode();
    result = 31 * result + daysToExpire;
    return result;
  }

  @Override
  public String toString() {
    return String.format("%s%nName: %s%nDays to expire: %d",
            super.toString(), name, daysToExpire);
  }

  @Override
  public boolean needsDelivery() {

    return getAvailableQuantity() < 15 && daysToExpire < 7;
  }
}
