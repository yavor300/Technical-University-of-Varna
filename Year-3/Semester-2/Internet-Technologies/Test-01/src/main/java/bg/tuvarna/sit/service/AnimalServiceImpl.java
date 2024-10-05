package bg.tuvarna.sit.service;

import bg.tuvarna.sit.exceptions.AnimalNotAddedException;
import bg.tuvarna.sit.exceptions.AnimalNotFoundException;
import bg.tuvarna.sit.exceptions.AnimalValidationException;
import bg.tuvarna.sit.models.dto.AnimalRequestAddDto;
import bg.tuvarna.sit.models.dto.AnimalRequestPutDto;
import bg.tuvarna.sit.models.dto.AnimalResponseGetAllDto;
import bg.tuvarna.sit.models.dto.AnimalResponseReadDto;
import bg.tuvarna.sit.models.entities.Animal;
import bg.tuvarna.sit.models.entities.AnimalType;
import bg.tuvarna.sit.repository.AnimalRepository;
import bg.tuvarna.sit.repository.AnimalRepositoryImpl;

import java.util.Collection;
import java.util.stream.Collectors;

public class AnimalServiceImpl implements AnimalService {

  private final AnimalRepository repository;

  private AnimalServiceImpl(AnimalRepository instance) {
    this.repository = instance;
  }

  private static class SingletonHelper {
    private static final AnimalServiceImpl INSTANCE = new AnimalServiceImpl(AnimalRepositoryImpl.getInstance());
  }

  public static AnimalServiceImpl getInstance() {
    return SingletonHelper.INSTANCE;
  }

  @Override
  public AnimalResponseReadDto add(AnimalRequestAddDto dto) throws AnimalValidationException, AnimalNotAddedException {

    validateAnimalDto(dto);

    Animal animal = convertToEntity(dto);
    addAnimalToRepository(animal);

    return convertToReadDto(animal);
  }

  @Override
  public AnimalResponseGetAllDto getAllByType(String type) throws AnimalValidationException {

    AnimalType animalType = parseAndValidateType(type);
    Collection<Animal> allByType = repository.getAllByType(animalType);

    return new AnimalResponseGetAllDto(convertAllToResponseDto(allByType));
  }

  @Override
  public AnimalResponseReadDto put(AnimalRequestPutDto dto) throws AnimalValidationException, AnimalNotFoundException {

    long id = parseAndValidateId(String.valueOf(dto.getId()));

    Animal animal = repository.put(id, dto.getName());
    if (animal == null) {
      throw new AnimalNotFoundException("Animal with ID " + id + " not found.");
    }

    return convertToReadDto(animal);
  }

  @Override
  public AnimalResponseReadDto delete(String id) throws AnimalValidationException, AnimalNotFoundException {

    long animalId = parseAndValidateId(id);
    Animal animal = deleteAnimalById(animalId);

    return convertToReadDto(animal);
  }

  private Animal deleteAnimalById(long id) throws AnimalNotFoundException {

    Animal animal = repository.delete(id);
    if (animal == null) {
      throw new AnimalNotFoundException("Animal with ID " + id + " not found.");
    }

    return animal;
  }

  private long parseAndValidateId(String id) throws AnimalValidationException {

    if (id == null) {
      throw new AnimalValidationException("The animal ID cannot be null.");
    }

    try {
      return Long.parseLong(id);
    } catch (NumberFormatException e) {
      throw new AnimalValidationException("The animal ID is invalid format.");
    }
  }

  private void validateAnimalDto(AnimalRequestAddDto dto) throws AnimalValidationException {

    if (dto.getBirthYear() == null || dto.getType() == null) {
      throw new AnimalValidationException("None of the Animal fields (type, birthYear) can be null.");
    }

    if (dto.getBirthYear() <= 0) {
      throw new AnimalValidationException("The birthYear must be a positive number.");
    }
  }

  private Animal convertToEntity(AnimalRequestAddDto dto) {

    AnimalType animalType;
    try {
      animalType = AnimalType.valueOf(dto.getType().toUpperCase());
    } catch (IllegalArgumentException e) {
      animalType = AnimalType.OTHER;

    }

    return new Animal(animalType, dto.getName(), dto.getBirthYear());
  }

  private void addAnimalToRepository(Animal animal) throws AnimalNotAddedException {

    boolean addedSuccessfully = repository.add(animal);
    if (!addedSuccessfully) {
      throw new AnimalNotAddedException("Animal could not be added to the repository.");
    }
  }

  private AnimalResponseReadDto convertToReadDto(Animal animal) {

    return new AnimalResponseReadDto(
            Long.toString(animal.getId()),
            animal.getType().toString(),
            animal.getName(),
            String.valueOf(animal.getBirthYear()));
  }

  private AnimalType parseAndValidateType(String type) throws AnimalValidationException {

    try {
      return AnimalType.valueOf(type.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new AnimalValidationException("Invalid animal type. Valid types are: dog, cat, other");
    }
  }

  private Collection<AnimalResponseReadDto> convertAllToResponseDto(Collection<Animal> animals) {

    return animals.stream()
            .map(this::convertToReadDto)
            .collect(Collectors.toSet());
  }
}
