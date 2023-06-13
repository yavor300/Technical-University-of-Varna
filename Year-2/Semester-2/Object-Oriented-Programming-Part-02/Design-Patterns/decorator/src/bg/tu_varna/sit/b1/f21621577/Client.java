package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.base.Home;
import bg.tu_varna.sit.b1.f21621577.entities.BasicHome;
import bg.tu_varna.sit.b1.f21621577.entities.Playground;
import bg.tu_varna.sit.b1.f21621577.entities.SwimmingPool;

public class Client {

  public static void main(String[] args) {

    Home home = new BasicHome();
    System.out.println("Total cost: $" + home.getPrice());

    home = new Playground(home);
    System.out.println("Total cost: $" + home.getPrice());

    home = new SwimmingPool(home);
    System.out.println("Total cost: $" + home.getPrice());
  }
}
