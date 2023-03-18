package bg.tu_varna.sit.b1.f21621577.base.eraser;

import bg.tu_varna.sit.b1.f21621577.base.generator.GeneratedElement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public abstract class FileEraser<T extends GeneratedElement> implements Eraser<T> {

  private final File file;
  private final BufferedReader reader;

  protected FileEraser(File file) throws IOException {
    this.file = file;
    this.reader = new BufferedReader(new FileReader(file));
  }

  protected BufferedReader getReader() {
    return reader;
  }

  protected File getFile() {
    return file;
  }
}
