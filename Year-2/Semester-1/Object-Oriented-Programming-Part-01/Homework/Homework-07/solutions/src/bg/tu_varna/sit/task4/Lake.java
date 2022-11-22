package bg.tu_varna.sit.task4;

public class Lake extends WaterBody {

  private final int width;
  private final int length;

  public Lake(String name, int depth, Fish[] fishes, int width, int length) throws WaterBodyException {
    super(name, depth, fishes);
    this.width = width;
    this.length = length;
  }

  @Override
  boolean isFloaty() {

    return getDepth() >= 5 && width >= 1000 && length >= 1000;
  }
}
