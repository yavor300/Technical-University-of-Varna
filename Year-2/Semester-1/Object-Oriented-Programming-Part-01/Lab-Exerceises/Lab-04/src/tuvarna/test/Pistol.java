package tuvarna.test;

public class Pistol extends Gun {

  private int shotLength;

  public Pistol(String name, int bulletsCapacity, int bulletsCount, int bulletsSingleShot, int shotLength) {
    super(name, bulletsCapacity, bulletsCount, bulletsSingleShot);
    this.shotLength = shotLength;
  }
}
