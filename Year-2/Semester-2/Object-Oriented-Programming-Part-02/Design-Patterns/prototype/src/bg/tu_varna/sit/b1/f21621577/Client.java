package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.base.BasicCar;
import bg.tu_varna.sit.b1.f21621577.implementation.Nano;

public class Client {

  public static void main(String[] args) {

    BasicCar nano = new Nano("Nano XM624 cc");
    System.out.println(nano);

    try {
      BasicCar clone = nano.clone();
      System.out.println(clone);
      printCarDetail(clone);
      System.out.println(nano);
    } catch (CloneNotSupportedException e) {
      System.out.println(e.getMessage());
    }
  }

  private static void printCarDetail(BasicCar car) {

    System.out.println("Editing a cloned model:");
    System.out.println("Model: " + car.getModelName());
    car.setOnRoadPrice(car.getOnRoadPrice() + 100);
    System.out.println("It's on-road price: $" + car.getOnRoadPrice());
  }
}
