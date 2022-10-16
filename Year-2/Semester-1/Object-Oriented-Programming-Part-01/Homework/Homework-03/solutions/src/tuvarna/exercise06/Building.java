package tuvarna.exercise06;

public class Building {

  private final String address;
  private final double width;
  private final double length;
  private final double height;
  private final double squareFeet;

  protected Building(String address, double width, double length, double height, double squareFeet) {
    this.address = address;
    this.width = width;
    this.length = length;
    this.height = height;
    this.squareFeet = squareFeet;
  }

  @Override
  public String toString() {
    return String.format("Building: %s%nAddress: %s%n" +
            "Width: %.2f m.%nLength: %.2f m.%nHeight: %.2f m.%n" +
            "Square feet: %.2f m.", getClass().getSimpleName(),
            address, width, length, height, squareFeet);
  }

  protected String getAddress() {
    return address;
  }

  protected double getWidth() {
    return width;
  }

  protected double getLength() {
    return length;
  }

  protected double getHeight() {
    return height;
  }

  protected double getSquareFeet() {
    return squareFeet;
  }
}
