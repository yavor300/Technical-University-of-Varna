package bg.tu_varna.sit.task4;

public class Fish {

  private String name;
  private final int quantity;

  protected Fish(String name, int quantity) {
    this.name = name;
    this.quantity = quantity;
  }

  public int getQuantity() {
    return quantity;
  }
}
