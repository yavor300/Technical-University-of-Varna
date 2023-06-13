package bg.tu_varna.sit.b1.f21621577.base;

/**
 * The BasicCar class represents a basic car.
 */
public abstract class BasicCar implements Cloneable {

  private String modelName;
  private int basePrice;
  private int onRoadPrice;

  /**
   * Creates and returns a copy of this BasicCar instance.
   *
   * @return A clone of this BasicCar instance.
   * @throws CloneNotSupportedException If cloning is not supported for this object.
   */
  @Override
  public BasicCar clone() throws CloneNotSupportedException {

    return (BasicCar) super.clone();
  }

  /**
   * Returns the model name of the car.
   *
   * @return The model name of the car.
   */
  public String getModelName() {

    return modelName;
  }

  /**
   * Sets the model name of the car.
   *
   * @param modelName The model name to set for the car.
   */
  protected void setModelName(String modelName) {

    this.modelName = modelName;
  }

  /**
   * Returns the base price of the car.
   *
   * @return The base price of the car.
   */
  public int getBasePrice() {

    return basePrice;
  }

  /**
   * Sets the on-road price of the car.
   *
   * @param onRoadPrice The on-road price to set for the car.
   */
  public void setOnRoadPrice(int onRoadPrice) {

    this.onRoadPrice = onRoadPrice;
  }

  /**
   * Sets the base price of the car.
   *
   * @param basePrice The base price to set for the car.
   */
  public void setBasePrice(int basePrice) {

    this.basePrice = basePrice;
  }

  /**
   * Returns the on-road price of the car.
   *
   * @return The on-road price of the car.
   */
  public int getOnRoadPrice() {

    return onRoadPrice;
  }

}
