package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.implementation.keys.types.DigitKey;
import bg.tu_varna.sit.b1.f21621577.base.generator.Generator;
import bg.tu_varna.sit.b1.f21621577.base.logger.Repository;
import bg.tu_varna.sit.b1.f21621577.implementation.keys.generators.DigitKeyGenerator;
import bg.tu_varna.sit.b1.f21621577.implementation.keys.generators.DigitLetterKeyGenerator;
import bg.tu_varna.sit.b1.f21621577.implementation.keys.types.LetterDigitKey;
import bg.tu_varna.sit.b1.f21621577.implementation.logs.PlainTextDigitKeyFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Main {

  private final static String DEFAULT_DIRECTORY = "src/bg/tu_varna/sit/b1/f21621577/resources/";

  public static void main(String[] args) {

    Random random = new Random();
    StringBuilder stringBuilder = new StringBuilder();

    File file = new File(DEFAULT_DIRECTORY + "LocalKeys.txt");
    BufferedWriter writer;
    try {
      writer = new BufferedWriter(new FileWriter(file, true));
    } catch (IOException e) {

      throw new RuntimeException(e);
    }

    Repository<DigitKey> plainTextFileRepository = new PlainTextDigitKeyFile(writer);

    Generator<DigitKey> digitKeyGenerator = new DigitKeyGenerator(random, stringBuilder);
    Generator<LetterDigitKey> digitLetterKeyGenerator = new DigitLetterKeyGenerator(random, stringBuilder);

    try {
      plainTextFileRepository.save(digitKeyGenerator.generate());

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
