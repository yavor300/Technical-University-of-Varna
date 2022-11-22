package bg.tu_varna.sit;

public class Rectangle implements Figure {

  private double a;
  private double b;

  public Rectangle(double a, double b) {
    setA(a);
    setB(b);
  }

  @Override
  public double area() {

    return a * b;
  }

  @Override
  public double perimeter() {

    return 2 * (a + b);
  }

  public double getA() {
    return a;
  }

  public double getB() {
    return b;
  }

  private void setA(double a) {

    if (a <= 0) {
      throw new InvalidFigureDimensionsException(SIDE_ERROR_MESSAGE);
    }
    this.a = a;
  }

  private void setB(double b) {

    if (b <= 0) {
      throw new InvalidFigureDimensionsException(SIDE_ERROR_MESSAGE);
    }
    this.b = b;
  }
}
