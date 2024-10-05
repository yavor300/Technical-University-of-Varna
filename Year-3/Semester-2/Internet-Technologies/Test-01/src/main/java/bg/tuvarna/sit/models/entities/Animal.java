package bg.tuvarna.sit.models.entities;

import java.util.Objects;

public class Animal {

  private long id;

  private final AnimalType type;

  private String name;

  private final int birthYear;

  public Animal(AnimalType type, String name, int birthYear) {
    this.type = type;
    this.name = name;
    this.birthYear = birthYear;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Animal animal = (Animal) o;

    return id == animal.id;
  }

  @Override
  public int hashCode() {

    return Objects.hash(id);
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getBirthYear() {
    return birthYear;
  }

  public void setId(long newId) {
    this.id = newId;
  }

  public AnimalType getType() {
    return type;
  }

  public void setName(String name) {
    this.name = name;
  }
}
