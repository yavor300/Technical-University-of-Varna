package bg.tu_varna.sit.b1.f21621577.implementation;

import bg.tu_varna.sit.b1.f21621577.base.BasicCar;
import java.util.Random;

/**
 * The Nano class represents a specific implementation of a Nano car.
 */
public class Nano  extends BasicCar {

  /**
   * Constructs a Nano car with the specified model name.
   *
   * @param modelName The model name of the Nano car.
   */
  public Nano(String modelName) {

    setModelName(modelName);
    setBasePrice(5000);
    setOnRoadPrice(getBasePrice() + (new Random()).nextInt(1000));
  }

  /**
   * Creates and returns a copy of this Nano car instance.
   *
   * @return A clone of this Nano car instance.
   * @throws CloneNotSupportedException If cloning is not supported for this object.
   */
  @Override
  public BasicCar clone() throws CloneNotSupportedException {

    return (Nano) super.clone();
  }

  /**
   * Returns a string representation of this Nano car.
   *
   * @return A string representation of this Nano car.
   */
  @Override
  public String toString() {

    return "Model: " + getModelName() + "\nPrice: " + getOnRoadPrice();
  }
}
