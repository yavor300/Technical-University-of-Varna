package bg.tu_varna.sit.b1.f21621577.impl.processors;

import bg.tu_varna.sit.b1.f21621577.base.ImageProcessor;

import java.io.IOException;
import java.nio.file.Files;

public class PngProcessor extends ImageProcessor {

  public PngProcessor(String filename) throws IOException {
    super(filename);
  }

  @Override
  public void manipulate() {

    setContent(getContent().trim() + " processed");
  }

  @Override
  public String readFromFile() throws IOException {

    return readPng();
  }

  @Override
  public void writeToFile(String content) throws IOException {

    writePng(content);
  }

  private String readPng() throws IOException {

    StringBuilder builder = new StringBuilder();

    String line;

    while ((line = getReader().readLine()) != null) {
      line = line.trim();
      builder.append(line).append(System.lineSeparator());
    }

    return builder.toString();
  }

  private void writePng(String content) throws IOException {

    setContent(content);
    Files.write(getFile(), content.getBytes());
  }
}