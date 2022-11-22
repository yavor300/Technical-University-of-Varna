package bg.tu_varna.sit;

public class Triangle implements Figure {

  private double a;
  private double b;
  private double c;

  public Triangle(double a, double b, double c) throws InvalidFigureDimensionsException {
    setA(a);
    setB(b);
    setC(c);
    validateSides();
  }

  @Override
  public double area() {

    double semiPerimeter = (a + b + c) / 2;

    return Math.sqrt(semiPerimeter * (semiPerimeter - a)
            * (semiPerimeter - b) * (semiPerimeter - c));
  }

  @Override
  public double perimeter() {

    return a + b + c;
  }

  public double getA() {
    return a;
  }

  public double getB() {
    return b;
  }

  public double getC() {
    return c;
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

  private void setC(double c) {

    if (c <= 0) {
      throw new InvalidFigureDimensionsException(SIDE_ERROR_MESSAGE);
    }
    this.c = c;
  }

  private void validateSides() {

    if (a + b <= c || a + c <= b || c + b <= a) {
      throw new InvalidFigureDimensionsException(INVALID_TRIANGLE_SIDES);
    }
  }
}
