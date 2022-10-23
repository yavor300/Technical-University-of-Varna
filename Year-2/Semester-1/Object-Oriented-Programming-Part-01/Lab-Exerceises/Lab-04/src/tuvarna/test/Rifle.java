package tuvarna.test;

public class Rifle extends Pistol {

  private int shotCount;

  public Rifle(String name, int bulletsCapacity, int bulletsCount, int bulletsSingleShot, int shotLength, int shotCount) {
    super(name, bulletsCapacity, bulletsCount, bulletsSingleShot, shotLength);
    this.shotCount = shotCount;
  }
}
