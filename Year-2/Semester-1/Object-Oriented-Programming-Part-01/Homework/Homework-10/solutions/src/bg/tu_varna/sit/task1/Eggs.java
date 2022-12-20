package bg.tu_varna.sit.task1;

public class Eggs extends Item {

  private final int numberInPack;

  public Eggs(ItemType itemType, double itemPrice, int availableQuantity, int numberInPack) {
    super(itemType, itemPrice, availableQuantity);
    this.numberInPack = numberInPack;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    Eggs eggs = (Eggs) o;

    return numberInPack == eggs.numberInPack;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + numberInPack;
    return result;
  }

  @Override
  public String toString() {
    return String.format("%s%nNumber in pack: %d", super.toString(), numberInPack);
  }

  public int getNumberInPack() {
    return numberInPack;
  }

  @Override
  public boolean needsDelivery() {

    return getAvailableQuantity() < 15;
  }
}
