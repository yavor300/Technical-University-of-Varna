package bg.tu_varna.sit.task4;

public class River extends WaterBody {

  private final int speed;

  public River(String name, int depth, Fish[] fishes, int speed) {
    super(name, depth, fishes);
    this.speed = speed;
  }

  @Override
  boolean isFloaty() {

    return getDepth() >= 3 && speed <= 30;
  }
}
