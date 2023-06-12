package bg.tu_varna.sit.b1.f21621577.factory;

import bg.tu_varna.sit.b1.f21621577.base.Animal;

/**
 * The AnimalFactory class is responsible for creating instances of Animal objects.
 */
public class AnimalFactory {

  /**
   * Creates an Animal object based on the specified type.
   *
   * @param type The type of animal to create. Valid values are "dog" or "tiger".
   * @return An instance of the corresponding Animal subclass.
   * @throws IllegalArgumentException if an unknown animal type is provided.
   */
  public Animal createAnimal(String type) {

    Animal animal;
    if ("dog".equalsIgnoreCase(type)) {
      animal = new Dog();
    } else if ("tiger".equalsIgnoreCase(type)) {
      animal = new Tiger();
    } else {
      System.out.println("You can create either a 'dog' or a 'tiger'.");
      throw new IllegalArgumentException("Unknown animal cannot be instantiated.");
    }

    return animal;
  }
}
