package bg.tuvarna.sit.repository;

import bg.tuvarna.sit.models.entities.Animal;
import bg.tuvarna.sit.models.entities.AnimalType;

import java.util.Collection;

public interface AnimalRepository {

  boolean add(Animal animal);

  Collection<Animal> getAllByType(AnimalType type);

  Animal put(long id, String name);

  Animal delete(long id);
}
