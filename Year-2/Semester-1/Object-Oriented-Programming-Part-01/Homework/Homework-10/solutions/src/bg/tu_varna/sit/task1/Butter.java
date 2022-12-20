package bg.tu_varna.sit.task1;

public class Butter extends Item {

  private final int packing;
  private final int butterContent;

  public Butter(ItemType itemType, double itemPrice, int availableQuantity, int packing, int butterContent) {
    super(itemType, itemPrice, availableQuantity);
    this.packing = packing;
    this.butterContent = butterContent;
  }

  @Override
  public boolean needsDelivery() {

    return packing < 250 || getAvailableQuantity() < 30;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Butter butter = (Butter) o;

    if (packing != butter.packing) return false;
    return butterContent == butter.butterContent;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + packing;
    result = 31 * result + butterContent;
    return result;
  }

  @Override
  public String toString() {
    return String.format("%s%nPacking: %d%nButter content: %d%%",
            super.toString(), packing, butterContent);
  }

  public int getPacking() {
    return packing;
  }

  public int getButterContent() {
    return butterContent;
  }
}
