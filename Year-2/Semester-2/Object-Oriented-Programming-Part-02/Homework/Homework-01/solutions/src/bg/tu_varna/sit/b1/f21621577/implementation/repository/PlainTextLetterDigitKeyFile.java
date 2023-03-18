package bg.tu_varna.sit.b1.f21621577.implementation.repository;

import bg.tu_varna.sit.b1.f21621577.base.repository.FileRepository;
import bg.tu_varna.sit.b1.f21621577.implementation.key.type.LetterDigitKey;

import java.io.File;

public class PlainTextLetterDigitKeyFile extends FileRepository<LetterDigitKey> {

  public PlainTextLetterDigitKeyFile(File file) {
    super(file);
  }
}
