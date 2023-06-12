package bg.tu_varna.sit.b1.f21621577.factory;

import bg.tu_varna.sit.b1.f21621577.base.Animal;

/**
 * The Dog class represents a dog, which is a type of animal.
 */
class Dog implements Animal {

  /**
   * Creates a new instance of Dog.
   */
  Dog() {

    System.out.println("\nA dog is created.");
  }

  /**
   * Displays the behavior of the dog.
   * It says "Bow-Wow" and prefers barking.
   */
  @Override
  public void displayBehavior() {

    System.out.println("It says: Bow-Wow.");
    System.out.println("It prefers barking.");
  }
}
