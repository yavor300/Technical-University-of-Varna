package bg.tu_varna.sit.task2;

class Paper implements Cloneable {

  private final double fillRate;

  Paper(double fillRate) {
    this.fillRate = fillRate;
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
