package bg.tu_varna.sit.b1.f21621577.implementation.eraser;

import bg.tu_varna.sit.b1.f21621577.base.eraser.FileEraser;
import bg.tu_varna.sit.b1.f21621577.implementation.key.type.DigitKey;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DigitKeyFileEraser extends FileEraser<DigitKey> {

  public DigitKeyFileEraser(File file) throws IOException {
    super(file);
  }

  @Override
  public void erase(DigitKey element) throws IOException {

    String keyToRemove = element.getData();
    StringBuilder oldContent = new StringBuilder();

    String currentLine = getReader().readLine();
    while (currentLine != null) {
      oldContent.append(currentLine).append(System.lineSeparator());
      currentLine = getReader().readLine();
    }

    String newContent = oldContent.toString().replaceAll(keyToRemove, "");

    BufferedWriter writer = new BufferedWriter(new FileWriter(getFile()));
    writer.write(newContent);
    writer.close();
    getReader().close();
  }
}
