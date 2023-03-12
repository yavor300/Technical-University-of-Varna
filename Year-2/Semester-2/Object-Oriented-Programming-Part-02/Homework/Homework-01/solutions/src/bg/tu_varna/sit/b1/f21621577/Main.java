package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.base.generator.Generator;
import bg.tu_varna.sit.b1.f21621577.base.logger.FileLogger;
import bg.tu_varna.sit.b1.f21621577.base.validator.Validator;
import bg.tu_varna.sit.b1.f21621577.implementation.keys.DigitKeyGenerator;
import bg.tu_varna.sit.b1.f21621577.implementation.keys.DigitLetterKeyGenerator;
import bg.tu_varna.sit.b1.f21621577.implementation.logs.LocalFileErrorLogger;
import bg.tu_varna.sit.b1.f21621577.implementation.logs.LocalFileKeyLogger;
import bg.tu_varna.sit.b1.f21621577.implementation.validators.CreatedKeyFileValidator;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

  public static void main(String[] args) {

    Generator digitKeyGenerator = new DigitKeyGenerator();
    Generator digitLetterKeyGenerator = new DigitLetterKeyGenerator();

    FileLogger localFileKeyLogger = null;
    try {
      localFileKeyLogger = new LocalFileKeyLogger("LocalKeys.txt");
    } catch (IOException e) {
      e.printStackTrace();
    }
    localFileKeyLogger.log(digitKeyGenerator.generate());
    localFileKeyLogger.log(digitKeyGenerator.generate());


    Validator validator = null;
    try {
      validator = new CreatedKeyFileValidator(localFileKeyLogger);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    try {
      System.out.println(validator.isValid("703104"));
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {


    } catch (IOException ex) {
      FileLogger localFileErrorLogger = new LocalFileErrorLogger("LocalErrors.txt");
      try {
        localFileErrorLogger.log(ex.getMessage());
      } catch (IOException ex) {
        ioException.printStackTrace();
      }
    }

    try {
      System.out.println(digitLetterKeyGenerator.generate());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
