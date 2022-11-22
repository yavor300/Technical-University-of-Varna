package bg.tu_varna.sit.task5;

public abstract class Drink implements Delivery, Serving {

  private final String name;
  private int quantity;
  private final int serveQuantity;

  protected Drink(String name, int quantity, int serveQuantity) {
    this.name = name;
    this.quantity = quantity;
    this.serveQuantity = serveQuantity;
  }

  @Override
  public void deliver(int quantity) {

    this.quantity += quantity;
  }

  @Override
  public void serve(int amount) {

    int availableServings = quantity / serveQuantity;

    if (availableServings >= amount) {
      quantity -= serveQuantity * amount;
    } else {
      if (availableServings > 0) {
        quantity -= serveQuantity * availableServings;
      }
    }
  }

  @Override
  public String toString() {
    return String.format("%s, %d л.", name, quantity);
  }

  public int getQuantity() {
    return quantity;
  }
}
