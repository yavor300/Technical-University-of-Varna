package bg.tu_varna.sit.task1;

public class Chocolate extends Item {

  private final String name;
  private final int cocoaContent;

  public Chocolate(ItemType itemType, double itemPrice, int availableQuantity, String name, int cocoaContent) {
    super(itemType, itemPrice, availableQuantity);
    this.name = name;
    this.cocoaContent = cocoaContent;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Chocolate chocolate = (Chocolate) o;

    if (cocoaContent != chocolate.cocoaContent) return false;
    return name.equals(chocolate.name);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + name.hashCode();
    result = 31 * result + cocoaContent;
    return result;
  }

  @Override
  public boolean needsDelivery() {

    return getAvailableQuantity() < 15;
  }

  public String getName() {
    return name;
  }

  public int getCocoaContent() {
    return cocoaContent;
  }
}
