package bg.tu_varna.sit.b1.f21621577.factory;

import bg.tu_varna.sit.b1.f21621577.base.Dog;

/**
 * The PetDog class represents a pet dog.
 */
class PetDog implements Dog {

  /**
   * Creates a new instance of PetDog with the specified color.
   *
   * @param color The color of the pet dog.
   */
  PetDog(String color) {

    System.out.println("A pet dog with " + color + " color is created.");
  }

  /**
   * Displays information about the pet dog.
   */
  @Override
  public void displayMe() {

    System.out.println("The " + this + " says: " + "Bow - Wow.I prefer to stay at home.");
  }

  /**
   * Returns a string representation of the pet dog.
   *
   * @return A string representation of the pet dog.
   */
  @Override
  public String toString() {

    return "pet dog";
  }
}