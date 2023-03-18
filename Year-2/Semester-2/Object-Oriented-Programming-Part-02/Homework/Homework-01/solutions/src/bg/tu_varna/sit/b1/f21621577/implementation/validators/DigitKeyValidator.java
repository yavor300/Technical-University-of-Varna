package bg.tu_varna.sit.b1.f21621577.implementation.validators;

import bg.tu_varna.sit.b1.f21621577.base.repository.FileRepository;
import bg.tu_varna.sit.b1.f21621577.base.validator.Validator;
import bg.tu_varna.sit.b1.f21621577.implementation.key.type.DigitKey;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DigitKeyValidator implements Validator<DigitKey> {

  private final BufferedReader inputReader;

  public DigitKeyValidator(FileRepository<DigitKey> fileRepository) throws FileNotFoundException {
    this.inputReader = new BufferedReader(new FileReader(fileRepository.getFile()));
  }

  @Override
  public boolean isValid(DigitKey element) throws IOException {

    String nextLine;
    while ((nextLine = inputReader.readLine()) != null){
      if (nextLine.trim().equals(element.getData())) {
        return true;
      }
    }
    return false;
  }

}
