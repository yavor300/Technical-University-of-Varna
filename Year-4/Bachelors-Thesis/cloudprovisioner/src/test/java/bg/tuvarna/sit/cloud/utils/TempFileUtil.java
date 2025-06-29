package bg.tuvarna.sit.cloud.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TempFileUtil {

  public static File writeJsonToTempFile(String json) throws IOException {

    File tempFile = File.createTempFile("stored-state", ".json");
    tempFile.deleteOnExit();
    try (FileWriter fw = new FileWriter(tempFile)) {
      fw.write(json);
    }
    return tempFile;
  }
}
