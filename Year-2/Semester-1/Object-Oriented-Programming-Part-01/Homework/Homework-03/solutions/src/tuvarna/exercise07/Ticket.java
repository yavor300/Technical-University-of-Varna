package tuvarna.exercise07;

public class Ticket {

  private final String performanceName;
  private double price;

  protected Ticket(String performanceName, double price) {
    this.performanceName = performanceName;
    this.price = price;
  }

  @Override
  public String toString() {
    return String.format("Ticket: %s%nPerformance name: %s%nPrice: %.2f BGN.",
            getClass().getSimpleName(), performanceName, price);
  }

  protected String getPerformanceName() {
    return performanceName;
  }

  protected double getPrice() {
    return price;
  }

  protected void setPrice(double price) {
    this.price = price;
  }
}
