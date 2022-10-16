package tuvarna.exercise06;

public class House extends Building {

  private final int floors;
  private String owner;

  public House(String address, double width, double length, double height, int floors, String owner) {
    super(address, width, length, height, (width * length) * floors);
    this.floors = floors;
    this.owner = owner;
  }

  @Override
  public String toString() {
    return String.format("%s%nFloors: %d%nOwner: %s",
            super.toString(), floors, owner);
  }

  public int getFloors() {
    return floors;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }
}
