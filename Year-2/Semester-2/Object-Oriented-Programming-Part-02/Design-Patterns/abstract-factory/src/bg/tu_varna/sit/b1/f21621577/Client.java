package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.base.AnimalFactory;
import bg.tu_varna.sit.b1.f21621577.base.Dog;
import bg.tu_varna.sit.b1.f21621577.base.Tiger;
import bg.tu_varna.sit.b1.f21621577.factory.PetAnimalFactory;
import bg.tu_varna.sit.b1.f21621577.factory.WildAnimalFactory;

public class Client {

  public static void main(String[] args) {

    AnimalFactory wildAnimalFactory = new WildAnimalFactory();
    Dog wildDog = wildAnimalFactory.createDog("black");
    wildDog.displayMe();

    PetAnimalFactory petAnimalFactory = new PetAnimalFactory();
    Tiger petTiger = petAnimalFactory.createTiger("brown");
    petTiger.aboutMe();
    petTiger.inviteDog(wildDog);
  }
}
