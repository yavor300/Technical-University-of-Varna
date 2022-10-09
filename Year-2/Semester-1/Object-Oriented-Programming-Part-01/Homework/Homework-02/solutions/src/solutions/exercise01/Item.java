package exercise01;

public class Item {

  private String type;
  private int quantity;
  private double price;
  private int expirationDays;

  public Item(String type, int quantity, double price, int expirationDays) {
    this.type = type;
    this.quantity = quantity;
    this.price = price;
    this.expirationDays = expirationDays;
  }

  @Override
  public String toString() {
    return String.format("Type: %s%nQuantity: %d%nPrice: %.2f BGN.%nExpiration days: %d",
            getType(), getQuantity(), getPrice(), getExpirationDays());
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getExpirationDays() {
    return expirationDays;
  }

  public void setExpirationDays(int expirationDays) {
    this.expirationDays = expirationDays;
  }
}
