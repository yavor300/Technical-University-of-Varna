package bg.tu_varna.sit.b1.f21621577.base;

public abstract class Home {

  private final double basePrice;
  private double additionalCost;

  protected Home() {

    this.basePrice = 100000.0;
    this.additionalCost = 0.0;
  }

  public abstract double getPrice();

  protected double getBasePrice() {

    return basePrice;
  }

  protected double getAdditionalCost() {
    return additionalCost;
  }

  protected void setAdditionalCost(double additionalCost) {

    this.additionalCost = additionalCost;
  }
}
