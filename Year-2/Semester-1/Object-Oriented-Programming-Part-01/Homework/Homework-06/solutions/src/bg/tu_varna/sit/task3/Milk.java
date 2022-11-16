package bg.tu_varna.sit.task3;

public class Milk extends Item implements Drink {

  private final int fatPercentage;

  public Milk(String name, int daysToExpire, int availableQuantity, int fatPercentage) {
    super(name, daysToExpire, availableQuantity);
    this.fatPercentage = fatPercentage;
  }

  @Override
  public boolean needsDelivery() {

    return getDaysToExpire() < 15 && getAvailableQuantity() < 10 && fatPercentage > 2;
  }
}
