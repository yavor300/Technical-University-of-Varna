package bg.tuvarna.sit.service;

import bg.tuvarna.sit.exceptions.AnimalNotAddedException;
import bg.tuvarna.sit.exceptions.AnimalNotFoundException;
import bg.tuvarna.sit.exceptions.AnimalValidationException;
import bg.tuvarna.sit.models.dto.AnimalRequestAddDto;
import bg.tuvarna.sit.models.dto.AnimalRequestPutDto;
import bg.tuvarna.sit.models.dto.AnimalResponseGetAllDto;
import bg.tuvarna.sit.models.dto.AnimalResponseReadDto;

public interface AnimalService {

  AnimalResponseReadDto add(AnimalRequestAddDto dto) throws AnimalValidationException, AnimalNotAddedException;

  AnimalResponseGetAllDto getAllByType(String type) throws AnimalValidationException;

  AnimalResponseReadDto put(AnimalRequestPutDto dto) throws AnimalValidationException, AnimalNotFoundException;

  AnimalResponseReadDto delete(String id) throws AnimalValidationException, AnimalNotFoundException;
}
