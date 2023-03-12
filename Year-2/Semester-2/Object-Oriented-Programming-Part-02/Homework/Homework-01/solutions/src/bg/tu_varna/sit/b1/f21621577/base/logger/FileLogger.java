package bg.tu_varna.sit.b1.f21621577.base.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class FileLogger implements Logger {

  private final static String DEFAULT_DIRECTORY = "src/bg/tu_varna/sit/b1/f21621577/resources/";
  private final File file;
  private final FileWriter fileWriter;

  protected FileLogger(String name) throws IOException {
    file = new File(DEFAULT_DIRECTORY + name);
    this.fileWriter = new FileWriter(file);
  }

  protected FileLogger(String directory, String name) throws IOException {
    this.file = new File(directory + name);
    this.fileWriter = new FileWriter(file);
  }

  public File getFile() {
    return file;
  }

  public FileWriter getFileWriter() {
    return fileWriter;
  }
}
