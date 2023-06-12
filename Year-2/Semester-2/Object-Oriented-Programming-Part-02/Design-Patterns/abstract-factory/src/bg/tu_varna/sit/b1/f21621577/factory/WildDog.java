package bg.tu_varna.sit.b1.f21621577.factory;

import bg.tu_varna.sit.b1.f21621577.base.Dog;

/**
 * The WildDog class represents a wild dog.
 */
class WildDog implements Dog {

  /**
   * Creates a new instance of WildDog with the specified color.
   *
   * @param color The color of the wild dog.
   */
  WildDog(String color) {

    System.out.println("A wild dog with " + color + " color is created.");
  }


  /**
   * Displays information about the wild dog.
   */
  @Override
  public void displayMe() {

    System.out.println("The " + this + " says: I prefer to roam freely in jungles. Bow-Wow.");
  }

  /**
   * Returns a string representation of the wild dog.
   *
   * @return A string representation of the wild dog.
   */
  @Override
  public String toString() {

    return "wild dog";
  }
}
