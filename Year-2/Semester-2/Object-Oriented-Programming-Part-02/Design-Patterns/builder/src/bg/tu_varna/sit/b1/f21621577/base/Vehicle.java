package bg.tu_varna.sit.b1.f21621577.base;

import java.util.LinkedList;

public abstract class Vehicle {

  private final LinkedList<String> parts;

  protected Vehicle() {

    this.parts = new LinkedList<>();
  }

  protected abstract String getBrandName();

  public void add(String part) {

    parts.addLast(part);
  }

  public void showProduct() {

    System.out.println("These are the construction sequences:");
    for (String part : parts) {
      System.out.println(part);
    }
  }

}
