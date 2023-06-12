package bg.tu_varna.sit.b1.f21621577.factory;

import bg.tu_varna.sit.b1.f21621577.base.Animal;

/**
 * The Tiger class represents a tiger, which is a type of animal.
 */
class Tiger implements Animal {

  /**
   * Creates a new instance of Tiger.
   */
  Tiger() {

    System.out.println("\nA tiger is created.");
  }

  /**
   * Displays the behavior of the tiger.
   * It says "Halum" and loves to roam in a jungle.
   */
  @Override
  public void displayBehavior() {

    System.out.println("Tiger says: Halum.");
    System.out.println("It loves to roam in a jungle.");
  }
}
