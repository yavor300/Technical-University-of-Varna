package bg.tu_varna.sit.task5;

public class SoftDrink extends Drink {

  private boolean isSugarFree;

  public SoftDrink(String name, int quantity, int serveQuantity, boolean isSugarFree) {
    super(name, quantity, serveQuantity);
    this.isSugarFree = isSugarFree;
  }

  @Override
  public boolean needOfDelivery() {

    return getQuantity() < 10;
  }

  @Override
  public String toString() {

    return String.format("%s, %s", super.toString(), needOfDelivery());
  }
}
