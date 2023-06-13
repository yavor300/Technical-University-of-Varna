package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.entities.Captain;

public class Client {

  public static void main(String[] args) {

      Captain captain = Captain.getCaptain();
      System.out.println(captain);

      captain = Captain.getCaptain();
      System.out.println(captain);
  }
}
