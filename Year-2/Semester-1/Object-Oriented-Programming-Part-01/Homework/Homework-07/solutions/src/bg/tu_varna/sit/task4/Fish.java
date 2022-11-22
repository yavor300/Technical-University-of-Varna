package bg.tu_varna.sit.task4;

public class Fish {

  private final FishList name;
  private final int quantity;

  protected Fish(FishList name, int quantity) {
    this.name = name;
    this.quantity = quantity;
  }

  public int getQuantity() {
    return quantity;
  }
}
