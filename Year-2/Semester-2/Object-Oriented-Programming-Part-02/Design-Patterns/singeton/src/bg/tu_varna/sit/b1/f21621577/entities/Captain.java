package bg.tu_varna.sit.b1.f21621577.entities;

public class Captain {

  private Captain() {

    System.out.println("\tNew captain is elected for your team.");
  }

  private static class SingletonHelper {

    private static final Captain CAPTAIN_INSTANCE = new Captain();
  }

  public static Captain getCaptain() {

    return SingletonHelper.CAPTAIN_INSTANCE;
  }
}
