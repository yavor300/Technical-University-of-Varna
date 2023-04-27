package bg.tu_varna.sit.b1.f21621577.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class ImageProcessor {

  protected static final String DEFAULT_RESOURCES_DIRECTORY = "src/bg/tu_varna/sit/b1/f21621577/resources/";

  private final Path file;
  private String content;
  private final BufferedReader reader;


  public ImageProcessor(String filename) throws IOException {
    this.file = Paths.get(DEFAULT_RESOURCES_DIRECTORY, filename);
    this.reader = Files.newBufferedReader(file);
    this.content = readFromFile();

  }

  public abstract void manipulate();

  public abstract String readFromFile() throws IOException;

  public abstract void writeToFile(String content) throws IOException;

  public void save() throws IOException {
    writeToFile(getContent());
  }

  protected String getContent() {
    return content;
  }

  protected void setContent(String content) {
    this.content = content;
  }

  protected Path getFile() {
    return file;
  }

  protected BufferedReader getReader() {
    return reader;
  }
}