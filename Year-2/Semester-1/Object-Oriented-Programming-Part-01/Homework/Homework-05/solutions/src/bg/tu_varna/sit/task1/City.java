package bg.tu_varna.sit.task1;

class City {

  private final String name;
  private int postalCode;

  City(String name, int postalCode) {
    this.name = name;
    setPostalCode(postalCode);
  }

  String getName() {
    return name;
  }

  private void setPostalCode(int postalCode) {

    if (postalCode <= 0) {
      throw new IllegalArgumentException("Postal code must be positive number!");
    }

    this.postalCode = postalCode;
  }
}
