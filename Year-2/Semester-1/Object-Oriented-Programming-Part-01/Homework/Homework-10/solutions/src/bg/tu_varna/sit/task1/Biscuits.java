package bg.tu_varna.sit.task1;

public class Biscuits extends Item {

  private final String name;

  public Biscuits(ItemType itemType, double itemPrice, int availableQuantity, String name) {
    super(itemType, itemPrice, availableQuantity);
    this.name = name;
  }

  @Override
  public boolean needsDelivery() {

    return getAvailableQuantity() < 5;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Biscuits biscuits = (Biscuits) o;

    return name.equals(biscuits.name);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + name.hashCode();
    return result;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return String.format("%s%nName: %s", super.toString(), name);
  }
}
