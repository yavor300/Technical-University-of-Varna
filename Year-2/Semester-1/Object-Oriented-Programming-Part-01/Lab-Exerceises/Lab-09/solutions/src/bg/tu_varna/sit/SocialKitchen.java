package bg.tu_varna.sit;

import bg.tu_varna.sit.base.Person;

public class SocialKitchen<E extends Person> {

  private final E[] items;

  public SocialKitchen(E[] items) {
    this.items = items;
  }

  public E[] getAll() {
    return items;
  }

  public void printAll() {
    for (E item : items) {
      System.out.println(item);
    }
  }
}
