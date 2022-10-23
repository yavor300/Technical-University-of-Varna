package tuvarna.test;

public class Gun {

  private String name;
  private int bulletsCapacity;
  private int bulletsCount;
  private final int bulletsSingleShot;

  protected Gun(String name, int bulletsCapacity, int bulletsCount, int bulletsSingleShot) {
    this.name = name;
    this.bulletsCapacity = bulletsCapacity;
    this.bulletsCount = bulletsCount;
    this.bulletsSingleShot = bulletsSingleShot;
  }

  protected int shot() {
    if (bulletsCount >= bulletsSingleShot) {
      bulletsCount -= bulletsSingleShot;
      return bulletsCount;
    }
    return bulletsCount;
  }
}
