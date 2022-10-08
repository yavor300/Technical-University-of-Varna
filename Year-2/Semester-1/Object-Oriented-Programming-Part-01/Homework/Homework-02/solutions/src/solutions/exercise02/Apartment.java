package exercise02;

import java.util.Objects;

public class Apartment {
  private Integer floor;
  private Double squareFootage;
  private Integer rooms;
  private String location;

  public Apartment(Integer floor, Double squareFootage, Integer rooms, String location) {
    this.floor = floor;
    this.squareFootage = squareFootage;
    this.rooms = rooms;
    this.location = location;
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

  @Override
  public String toString() {
    return String.format("Apartment:%n\tFloor: %d%n\tSquare footage: %.2f%n\tRooms: %d%n\tLocation: %s",
            floor, squareFootage, rooms, location);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Apartment apartment = (Apartment) o;
    return squareFootage.equals(apartment.squareFootage);
  }
}
