package tuvarna.exercise05;

public class City {

  private static final String INVALID_POSTAL_CODE_MESSAGE = "Invalid postal code!";

  private final String name;
  private int postalCode;

  public City(String name, int postalCode) {
    this.name = name;
    setPostalCode(postalCode);
  }

  public String getName() {
    return name;
  }

  public int getPostalCode() {
    return postalCode;
  }

  private void setPostalCode(int postalCode) {
    if (postalCode <= 0) throw new IllegalArgumentException(INVALID_POSTAL_CODE_MESSAGE);
    this.postalCode = postalCode;
  }
}
