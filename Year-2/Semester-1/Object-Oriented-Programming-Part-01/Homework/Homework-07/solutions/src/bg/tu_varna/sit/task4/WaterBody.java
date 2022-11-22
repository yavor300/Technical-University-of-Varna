package bg.tu_varna.sit.task4;

import java.io.IOError;
import java.io.IOException;

public abstract class WaterBody implements Usage {

  private String name;
  private int depth;
  private final Fish[] fishes;

  public WaterBody(String name, int depth, Fish[] fishes) throws WaterBodyException {
    this.name = name;
    setDepth(depth);
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

  private void setDepth(int depth) throws WaterBodyException {

    if (depth <= 0) {
      throw new WaterBodyException("Дълбочината не може да бъде отрицателна величина");
    }
    this.depth = depth;
  }
}
