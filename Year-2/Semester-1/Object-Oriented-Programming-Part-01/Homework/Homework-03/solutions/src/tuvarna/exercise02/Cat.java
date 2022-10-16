package tuvarna.exercise02;

public class Cat {

  private String color;
  private double furSize;

  protected Cat(String color, double furSize) {
    this.color = color;
    this.furSize = furSize;
  }

  @Override
  public String toString() {
    return String.format("Cat %s:%nColor: %s%nFur size: %.2f cm.",
            getClass().getSimpleName(),
            getColor(), getFurSize());
  }

  protected String getColor() {
    return color;
  }

  protected void setColor(String color) {
    this.color = color;
  }

  protected double getFurSize() {
    return furSize;
  }

  protected void setFurSize(double furSize) {
    this.furSize = furSize;
  }
}
