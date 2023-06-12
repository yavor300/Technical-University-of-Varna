package bg.tu_varna.sit.b1.f21621577.factory;

import bg.tu_varna.sit.b1.f21621577.base.AnimalFactory;
import bg.tu_varna.sit.b1.f21621577.base.Dog;
import bg.tu_varna.sit.b1.f21621577.base.Tiger;

/**
 * The PetAnimalFactory class is responsible for creating pet animals using the AnimalFactory interface.
 */
public class PetAnimalFactory implements AnimalFactory {

  /**
   * Constructs a new instance of PetAnimalFactory.
   */
  public PetAnimalFactory() {

    System.out.println("You opt for a pet animal factory.\n");
  }

  /**
   * Creates a new instance of PetTiger with the specified color.
   *
   * @param color The color of the tiger.
   * @return An instance of PetTiger.
   */
  @Override
  public Tiger createTiger(String color) {

    return new PetTiger(color);
  }

  /**
   * Creates a new instance of PetDog with the specified color.
   *
   * @param color The color of the dog.
   * @return An instance of PetDog.
   */
  @Override
  public Dog createDog(String color) {

    return new PetDog(color);
  }
}
