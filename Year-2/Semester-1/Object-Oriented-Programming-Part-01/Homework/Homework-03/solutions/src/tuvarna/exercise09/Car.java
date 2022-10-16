package tuvarna.exercise09;

public class Car {

  private final String brand;
  private final String model;
  private String color;
  private final int power;
  private final String engine;
  private final String transmission;
  private final int productionYear;
  private int range;

  protected Car(String brand, String model, String color, int power, String engine, String transmission, int productionYear) {
    this(brand, model, power, engine, transmission, productionYear);
    this.color = color;
  }

  protected Car(String brand, String model, int power, String engine, String transmission, int productionYear) {
    this.brand = brand;
    this.model = model;
    this.power = power;
    this.engine = engine;
    this.transmission = transmission;
    this.productionYear = productionYear;
  }

  protected String getBrand() {
    return brand;
  }

  protected String getModel() {
    return model;
  }

  protected String getColor() {
    return color;
  }

  protected int getPower() {
    return power;
  }

  protected String getEngine() {
    return engine;
  }

  protected String getTransmission() {
    return transmission;
  }

  protected int getProductionYear() {
    return productionYear;
  }

  protected int getRange() {
    return range;
  }

  protected void setColor(String color) {
    this.color = color;
  }

  protected void setRange(int range) {
    this.range = range;
  }
}
