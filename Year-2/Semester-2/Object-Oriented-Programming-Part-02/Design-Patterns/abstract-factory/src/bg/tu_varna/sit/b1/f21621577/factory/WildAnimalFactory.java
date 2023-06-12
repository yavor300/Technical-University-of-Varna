package bg.tu_varna.sit.b1.f21621577.factory;

import bg.tu_varna.sit.b1.f21621577.base.AnimalFactory;
import bg.tu_varna.sit.b1.f21621577.base.Dog;
import bg.tu_varna.sit.b1.f21621577.base.Tiger;

/**
 * The WildAnimalFactory class is responsible for creating wild animals using the AnimalFactory interface.
 */
public class WildAnimalFactory implements AnimalFactory {

  /**
   * Constructs a new instance of WildAnimalFactory.
   */
  public WildAnimalFactory() {

    System.out.println("You opt for a wild animal factory.\n");
  }

  /**
   * Creates a new instance of WildTiger with the specified color.
   *
   * @param color The color of the tiger.
   * @return An instance of WildTiger.
   */
  @Override
  public Tiger createTiger(String color) {

    return new WildTiger(color);
  }

  /**
   * Creates a new instance of WildDog with the specified color.
   *
   * @param color The color of the dog.
   * @return An instance of WildDog.
   */
  @Override
  public Dog createDog(String color) {

    return new WildDog(color);
  }
}
