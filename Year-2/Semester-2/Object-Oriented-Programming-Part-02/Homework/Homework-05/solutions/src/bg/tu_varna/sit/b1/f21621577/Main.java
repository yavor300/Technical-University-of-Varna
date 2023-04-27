package bg.tu_varna.sit.b1.f21621577;

import bg.tu_varna.sit.b1.f21621577.base.ImageProcessor;
import bg.tu_varna.sit.b1.f21621577.impl.processors.JpegProcessor;

import java.io.IOException;

public class Main {

  public static void main(String[] args) {

    try {
      ImageProcessor processor = new JpegProcessor("image.jpeg.txt");
      processor.writeToFile("new content");
      processor.manipulate();
      processor.save();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
