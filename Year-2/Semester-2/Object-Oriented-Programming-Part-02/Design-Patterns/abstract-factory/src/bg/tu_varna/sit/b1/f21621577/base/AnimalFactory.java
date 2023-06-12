package bg.tu_varna.sit.b1.f21621577.base;

/**
 * The AnimalFactory interface represents a factory for creating different types of animals.
 */
public interface AnimalFactory {

  /**
   * Creates a new instance of Tiger with the specified color.
   *
   * @param color The color of the tiger.
   * @return An instance of Tiger.
   */
  Tiger createTiger(String color);

  /**
   * Creates a new instance of Dog with the specified color.
   *
   * @param color The color of the dog.
   * @return An instance of Dog.
   */
  Dog createDog(String color);
}
