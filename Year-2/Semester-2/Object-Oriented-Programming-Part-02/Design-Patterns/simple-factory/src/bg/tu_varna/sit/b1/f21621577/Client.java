package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.factory.AnimalFactory;
import bg.tu_varna.sit.b1.f21621577.base.Animal;

public class Client {

  public static void main(String[] args) {

    AnimalFactory factory = new AnimalFactory();
    Animal dog = factory.createAnimal("dog");
    dog.displayBehavior();
    Animal tiger = factory.createAnimal("tiger");
    tiger.displayBehavior();
    try {
      Animal unknown = factory.createAnimal("unknown");
    } catch (IllegalArgumentException ex) {
      System.out.println(ex.getMessage());
    }
  }
}
