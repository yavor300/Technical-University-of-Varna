package bg.tu_varna.sit;

public class Rhombus extends Rectangle {

  private double height;

  public Rhombus(double a, double b, double height) {
    super(a, b);
    setHeight(height);
  }

  @Override
  public double area() {

    return getA() * height;
  }

  private void setHeight(double height) {

    if (height <= 0) {
      throw new InvalidFigureDimensionsException(HEIGHT_ERROR_MESSAGE);
    }

    this.height = height;
  }
}
