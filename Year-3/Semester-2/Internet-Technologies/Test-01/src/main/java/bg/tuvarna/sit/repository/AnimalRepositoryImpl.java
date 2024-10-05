package bg.tuvarna.sit.repository;

import bg.tuvarna.sit.models.entities.Animal;
import bg.tuvarna.sit.models.entities.AnimalType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class AnimalRepositoryImpl implements AnimalRepository {

  private final Collection<Animal> animals;
  private final AtomicLong idGenerator;

  private AnimalRepositoryImpl(Collection<Animal> animals) {
    this.animals = animals;
    this.idGenerator = new AtomicLong(0);
  }

  private static class SingletonHelper {
    private static final AnimalRepositoryImpl INSTANCE = new AnimalRepositoryImpl(new ArrayList<>());
  }

  public static AnimalRepositoryImpl getInstance() {
    return SingletonHelper.INSTANCE;
  }

  @Override
  public boolean add(Animal animal) {

    long newId = idGenerator.incrementAndGet();
    animal.setId(newId);
    return animals.add(animal);
  }

  @Override
  public Collection<Animal> getAllByType(AnimalType type) {

    return animals.stream()
            .filter(animal -> animal.getType().equals(type))
            .collect(Collectors.toSet());
  }

  @Override
  public Animal put(long id, String name) {

    Animal animal = getById(id);
    if (animal == null) {
      return null;
    }

    animal.setName(name);

    return animal;
  }

  @Override
  public Animal delete(long id) {

    Animal animalToRemove = getById(id);

    if (animalToRemove != null) {
      animals.remove(animalToRemove);
      return animalToRemove;
    }

    return null;
  }

  private Animal getById(long id) {

    return animals.stream()
            .filter(animal -> animal.getId() == id)
            .findFirst()
            .orElse(null);
  }
}
