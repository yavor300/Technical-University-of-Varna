package bg.tu_varna.sit.b1.f21621577.factory;

import bg.tu_varna.sit.b1.f21621577.base.Dog;
import bg.tu_varna.sit.b1.f21621577.base.Tiger;

/**
 * The WildTiger class represents a wild tiger.
 */
class WildTiger implements Tiger {

  /**
   * Creates a new instance of WildTiger with the specified color.
   *
   * @param color The color of the wild tiger.
   */
  WildTiger(String color) {

    System.out.println("A wild tiger with " + color + " color is created.");
  }

  /**
   * Displays information about the wild tiger.
   */
  @Override
  public void aboutMe() {

    System.out.println("The " + this + " says: I prefer hunting in jungles.");
  }

  /**
   * Invites a dog to interact with the wild tiger.
   *
   * @param dog The dog to be invited.
   */
  @Override
  public void inviteDog(Dog dog) {

    System.out.println("The " + this + " says: I saw a " + dog + " in the jungle.");
  }

  /**
   * Returns a string representation of the wild tiger.
   *
   * @return A string representation of the wild tiger.
   */
  @Override
  public String toString() {

    return "wild tiger";
  }
}
