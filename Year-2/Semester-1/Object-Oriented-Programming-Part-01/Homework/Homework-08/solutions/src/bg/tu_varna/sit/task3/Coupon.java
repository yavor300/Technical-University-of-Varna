package bg.tu_varna.sit.task3;

public class Coupon<T extends Discount> {

  private final T discount;

  public Coupon(T discount) {
    this.discount = discount;
  }

  public void displayTotalDiscount() {

    System.out.println(discount.calculateTotalDiscount());
  }

  public void displayAverageDiscount() {

    System.out.println(discount.calculateAverageDiscount());
  }
}
