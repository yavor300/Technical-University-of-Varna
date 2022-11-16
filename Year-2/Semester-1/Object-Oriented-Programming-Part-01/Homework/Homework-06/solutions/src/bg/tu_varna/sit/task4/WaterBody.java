package bg.tu_varna.sit.task4;

public abstract class WaterBody implements Usage {

  private String name;
  private int depth;
  private final Fish[] fishes;

  public WaterBody(String name, int depth, Fish[] fishes) {
    this.name = name;
    this.depth = depth;
    this.fishes = fishes;
  }

  abstract boolean isFloaty();

  public int calculateProduction() {

    if (!isProductable()) {
      return 0;
    }

    int result = 0;

    for (Fish fish : fishes) {
      if (fish instanceof EdibleFish && fish.getQuantity() > 10) {
        result += Math.abs(10 - fish.getQuantity());
      }
    }

    return result;
  }

  @Override
  public boolean isProductable() {

    for (Fish fish : fishes) {
      if (fish instanceof EdibleFish && fish.getQuantity() > 10) {
        return true;
      }
    }

    return false;
  }

  public int getDepth() {
    return depth;
  }
}
