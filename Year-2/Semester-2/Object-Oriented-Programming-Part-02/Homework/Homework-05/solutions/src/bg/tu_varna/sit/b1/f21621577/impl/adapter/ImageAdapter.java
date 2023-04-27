package bg.tu_varna.sit.b1.f21621577.impl.adapter;

import bg.tu_varna.sit.b1.f21621577.base.ImageProcessor;
import bg.tu_varna.sit.b1.f21621577.impl.processors.BmpProcessor;
import bg.tu_varna.sit.b1.f21621577.impl.processors.JpegProcessor;
import bg.tu_varna.sit.b1.f21621577.impl.processors.PngProcessor;

import java.io.IOException;

public class ImageAdapter {

  private ImageProcessor processor;

  public ImageAdapter(String filename) throws IOException {

    setProcessor(filename);
  }

  public void manipulate() {

    processor.manipulate();
  }

  public String readFromFile() throws IOException {

    return processor.readFromFile();
  }

  public void writeToFile(String content) throws IOException {

    processor.writeToFile(content);
  }

  public void setProcessor(String filename) throws IOException {

    if (filename.endsWith(".jpeg.txt")) {
      processor = new JpegProcessor(filename);
    } else if (filename.endsWith(".png.txt")) {
      processor = new PngProcessor(filename);
    } else if (filename.endsWith(".bmp.txt")) {
      processor = new BmpProcessor(filename);
    } else {
      throw new IllegalArgumentException("Unsupported image format");
    }
  }
}