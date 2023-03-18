package bg.tu_varna.sit.b1.f21621577.implementation.repository;

import bg.tu_varna.sit.b1.f21621577.implementation.key.type.DigitKey;
import bg.tu_varna.sit.b1.f21621577.base.repository.FileRepository;

import java.io.File;

public class PlainTextDigitKeyFile extends FileRepository<DigitKey> {

  public PlainTextDigitKeyFile(File file) {
    super(file);
  }
}
