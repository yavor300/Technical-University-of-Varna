package bg.tu_varna.sit.b1.f21621577.factory;

import bg.tu_varna.sit.b1.f21621577.base.Dog;
import bg.tu_varna.sit.b1.f21621577.base.Tiger;

/**
 * The PetTiger class represents a pet tiger.
 */
class PetTiger implements Tiger {

  /**
   * Creates a new instance of PetTiger with the specified color.
   *
   * @param color The color of the pet tiger.
   */
  PetTiger(String color) {

    System.out.println("A pet tiger with " + color + " color is created.");
  }

  /**
   *
   * Displays information about the pet tiger.
   */
  @Override
  public void aboutMe() {

    System.out.println("The " + this + " says: Halum. I play in an animal circus.");
  }

  /**
   * Invites a dog to interact with the pet tiger.
   *
   * @param dog The dog to be invited.
   */
  @Override
  public void inviteDog(Dog dog) {

    System.out.println("The " + this + " says: I saw a " + dog + " in my town.");
  }

  /**
   * Returns a string representation of the pet tiger.
   *
   * @return A string representation of the pet tiger.
   */
  @Override
  public String toString() {

    return "pet tiger";
  }
}