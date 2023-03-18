package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.base.eraser.FileEraser;
import bg.tu_varna.sit.b1.f21621577.base.repository.FileRepository;
import bg.tu_varna.sit.b1.f21621577.base.validator.Validator;
import bg.tu_varna.sit.b1.f21621577.implementation.eraser.DigitKeyFileEraser;
import bg.tu_varna.sit.b1.f21621577.implementation.error.KeyError;
import bg.tu_varna.sit.b1.f21621577.implementation.key.type.DigitKey;
import bg.tu_varna.sit.b1.f21621577.base.generator.Generator;
import bg.tu_varna.sit.b1.f21621577.implementation.key.generator.DigitKeyGenerator;
import bg.tu_varna.sit.b1.f21621577.implementation.repository.PlainTextDigitKeyFile;
import bg.tu_varna.sit.b1.f21621577.implementation.repository.PlainTextErrorKeyFile;
import bg.tu_varna.sit.b1.f21621577.implementation.validators.DigitKeyValidator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

/**
 * Задача 1
 *
 * Приложете SOLID принципите при написването на програма, която създава ключове за верификация.
 * При настъпване на грешки, те се обработват и записват във файл с име "LocalErrors.txt".
 * Ключовете за верификация могат да бъдат два вида - с букви и цифри и само с цифри (6 символа).
 * Генерираните ключове се съхраняват във файлове (за кодове с букви и цифри, и за кодове с цифри)
 * Кодовете за верификация трябва да могат да се проверяват дали са били генерирани, след проверката се изтриват от файла.
 */
public class Main {

  private final static String DEFAULT_DIRECTORY = "src/bg/tu_varna/sit/b1/f21621577/resources/";

  public static void main(String[] args) {

    /*
     * Initializing repository for digit keys.
     */
    File file = new File(DEFAULT_DIRECTORY + "DigitKeys.txt");
    FileRepository<DigitKey> digitKeyFileRepository = new PlainTextDigitKeyFile(file);

    /*
     * Initializing repository for key errors (e.g invalid ones).
     */
    File keyErrorFile = new File(DEFAULT_DIRECTORY + "KeyErrors.txt");
    FileRepository<KeyError> keyErrorFileRepository = new PlainTextErrorKeyFile(keyErrorFile);

    /*
     * Initializing digit key generator.
     */
    Random random = new Random();
    StringBuilder stringBuilder = new StringBuilder();
    Generator<DigitKey> digitKeyGenerator = new DigitKeyGenerator(random, stringBuilder);

    /*
     * Initializing digit key validator.
     */
    Validator<DigitKey> digitKeyValidator;
    try {
      digitKeyValidator = new DigitKeyValidator(digitKeyFileRepository);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }

    /*
     * Initializing an eraser.
     */
    FileEraser<DigitKey> digitKeyFileEraser;
    try {
      digitKeyFileEraser = new DigitKeyFileEraser(file);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    /*
     * Generate new digit key and validate it.
     * If the validation is successful the key is removed from the repository.
     */
    DigitKey digitKey = digitKeyGenerator.generate();
    try {
      digitKeyFileRepository.save(digitKey);
      if (digitKeyValidator.isValid(digitKey)) {
        digitKeyFileEraser.erase(digitKey);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    /*
     * Generate new digit key.
     */
    try {
      digitKeyFileRepository.save(digitKeyGenerator.generate());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    /*
     * Log error for invalid key.
     */
    try {
      DigitKey invalidDigitKey = new DigitKey("invalid");
      boolean isDigitKeyValid = digitKeyValidator.isValid(invalidDigitKey);
      if (!isDigitKeyValid) {
        keyErrorFileRepository.save(new KeyError("Invalid key: " + invalidDigitKey.getData()));
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
