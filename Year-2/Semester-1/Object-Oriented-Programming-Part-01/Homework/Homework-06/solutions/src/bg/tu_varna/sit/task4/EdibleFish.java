package bg.tu_varna.sit.task4;

public class EdibleFish extends Fish {

  private int percentOfYield;

  public EdibleFish(String name, int quantity, int percentOfYield) {
    super(name, quantity);
    this.percentOfYield = percentOfYield;
  }
}
