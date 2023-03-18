package bg.tu_varna.sit.b1.f21621577.base.repository;

import bg.tu_varna.sit.b1.f21621577.base.generator.GeneratedElement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class FileRepository<T extends GeneratedElement> implements Repository<T> {

  private final File file;

  protected FileRepository(File file) {
    this.file = file;
  }

  public File getFile() {
    return file;
  }

  @Override
  public void save(T element) throws IOException {

    new BufferedWriter(new FileWriter(getFile(), true))
            .append(element.getData())
            .append('\n')
            .close();
  }
}
