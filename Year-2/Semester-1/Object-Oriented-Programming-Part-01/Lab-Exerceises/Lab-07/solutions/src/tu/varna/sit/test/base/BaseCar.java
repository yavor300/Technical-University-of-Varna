package tu.varna.sit.test.base;

public class BaseCar implements Car {

  private final String model;
  private final String color;
  private final int horsePower;
  private final String countryProduces;

  protected BaseCar(String model, String color, int horsePower, String countryProduces) {
    this.model = model;
    this.color = color;
    this.horsePower = horsePower;
    this.countryProduces = countryProduces;
  }

  @Override
  public String toString() {
    return String.format("Това е %s, произведен в %s и има %d гуми",
            model, countryProduces, TIRES);
  }

  @Override
  public String getModel() {
    return model;
  }

  @Override
  public String getColor() {
    return color;
  }

  @Override
  public int getHorsePower() {
    return horsePower;
  }

  @Override
  public String getCountryProduced() {
    return countryProduces;
  }
}
