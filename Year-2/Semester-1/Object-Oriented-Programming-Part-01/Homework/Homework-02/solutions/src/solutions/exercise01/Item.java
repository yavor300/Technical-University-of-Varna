package exercise01;

public class Item {
  private String type;
  private Integer quantity;
  private Double price;
  private Integer expirationDays;

  public Item(String type, Integer quantity, Double price, Integer expirationDays) {
    this.type = type;
    this.quantity = quantity;
    this.price = price;
    this.expirationDays = expirationDays;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Integer getExpirationDays() {
    return expirationDays;
  }

  public void setExpirationDays(Integer expirationDays) {
    this.expirationDays = expirationDays;
  }

  @Override
  public String toString() {
    return "exercise01.Item{" +
            "type='" + type + '\'' +
            ", quantity=" + quantity +
            ", price=" + price +
            ", expirationDays=" + expirationDays +
            '}';
  }
}
