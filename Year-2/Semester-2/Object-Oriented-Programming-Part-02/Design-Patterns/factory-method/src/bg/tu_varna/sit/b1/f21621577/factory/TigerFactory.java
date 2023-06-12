package bg.tu_varna.sit.b1.f21621577.factory;

import bg.tu_varna.sit.b1.f21621577.base.Animal;
import bg.tu_varna.sit.b1.f21621577.base.AnimalFactory;

/**
 * The TigerFactory class is responsible for creating Tiger instances using the AnimalFactory interface.
 */
public class TigerFactory implements AnimalFactory {

  /**
   * Creates a new instance of Tiger.
   *
   * @return An instance of Tiger.
   */
  @Override
  public Animal createAnimal() {

    return new Tiger();
  }
}
