package bg.tu_varna.sit.b1.f21621577.factory;

import bg.tu_varna.sit.b1.f21621577.base.Animal;
import bg.tu_varna.sit.b1.f21621577.base.AnimalFactory;

/**
 * The DogFactory class is responsible for creating Dog instances using the AnimalFactory interface.
 */
public class DogFactory implements AnimalFactory {

  /**
   * Creates a new instance of Dog.
   *
   * @return An instance of Dog.
   */
  @Override
  public Animal createAnimal() {

    return new Dog();
  }
}
