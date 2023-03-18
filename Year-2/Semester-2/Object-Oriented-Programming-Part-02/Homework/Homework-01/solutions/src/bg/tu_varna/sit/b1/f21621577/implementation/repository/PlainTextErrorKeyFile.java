package bg.tu_varna.sit.b1.f21621577.implementation.repository;

import bg.tu_varna.sit.b1.f21621577.base.repository.FileRepository;
import bg.tu_varna.sit.b1.f21621577.implementation.error.KeyError;

import java.io.File;

public class PlainTextErrorKeyFile extends FileRepository<KeyError> {

  public PlainTextErrorKeyFile(File file) {
    super(file);
  }
}
