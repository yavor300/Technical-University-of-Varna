package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.base.Animal;
import bg.tu_varna.sit.b1.f21621577.base.AnimalFactory;
import bg.tu_varna.sit.b1.f21621577.factory.DogFactory;
import bg.tu_varna.sit.b1.f21621577.factory.TigerFactory;

public class Client {

  public static void main(String[] args) {

    AnimalFactory dogFactory = new DogFactory();
    Animal dog = dogFactory.createAnimal();
    dog.displayBehavior();

    AnimalFactory tigerFactory = new TigerFactory();
    Animal tiger = tigerFactory.createAnimal();
    tiger.displayBehavior();

  }
}
