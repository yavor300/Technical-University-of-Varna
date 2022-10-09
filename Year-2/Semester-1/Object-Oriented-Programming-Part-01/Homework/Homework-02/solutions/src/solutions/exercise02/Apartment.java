package exercise02;

public class Apartment {

  private int floor;
  private double squareFootage;
  private int rooms;
  private String location;

  public Apartment(Integer floor, Double squareFootage, Integer rooms, String location) {
    this.floor = floor;
    this.squareFootage = squareFootage;
    this.rooms = rooms;
    this.location = location;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Apartment other = (Apartment) o;

    return Math.abs(getSquareFootage() - other.getSquareFootage()) < 0.01;
  }

  @Override
  public String toString() {
    return String.format("Floor: %d%nSquare footage: %.2f%nRooms: %d%nLocation: %s",
            getFloor(), getSquareFootage(), getRooms(), getLocation());
  }

  public Integer getFloor() {
    return floor;
  }

  public void setFloor(Integer floor) {
    this.floor = floor;
  }

  public Double getSquareFootage() {
    return squareFootage;
  }

  public void setSquareFootage(Double squareFootage) {
    this.squareFootage = squareFootage;
  }

  public Integer getRooms() {
    return rooms;
  }

  public void setRooms(Integer rooms) {
    this.rooms = rooms;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}
