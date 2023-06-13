package bg.tu_varna.sit.b1.f21621577.implementation;

import bg.tu_varna.sit.b1.f21621577.base.BasicCar;
import java.util.Random;

/**
 * The Ford class represents a specific implementation of a Ford car.
 */
public class Ford extends BasicCar {

  /**
   * Constructs a Ford car with the specified model name.
   *
   * @param modelName The model name of the Ford car.
   */
  public Ford(String modelName) {

    setModelName(modelName);
    setBasePrice(40000);
    setOnRoadPrice(getBasePrice() + (new Random().nextInt(1000)));
  }

  /**
   * Creates and returns a copy of this Ford car instance.
   *
   * @return A clone of this Ford car instance.
   * @throws CloneNotSupportedException If cloning is not supported for this object.
   */
  @Override
  public BasicCar clone() throws CloneNotSupportedException {

    return (Ford) super.clone();
  }

  /**
   * Returns a string representation of this Ford car.
   *
   * @return A string representation of this Ford car.
   */
  @Override
  public String toString() {

    return "Model: " + getModelName() + "\nPrice: " + getOnRoadPrice();
  }
}
