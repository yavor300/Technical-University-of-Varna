package bg.tuvarna.sit.context;

import bg.tuvarna.sit.service.AnimalService;
import bg.tuvarna.sit.service.AnimalServiceImpl;

public class ApplicationContext {

  private static final AnimalService ANIMAL_SERVICE = AnimalServiceImpl.getInstance();

  public static AnimalService getAnimalService() {
    return ANIMAL_SERVICE;
  }
}
