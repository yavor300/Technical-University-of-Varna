package bg.tu_varna.sit.b1.f21621577.table.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static bg.tu_varna.sit.b1.f21621577.config.Config.COLS;
import static bg.tu_varna.sit.b1.f21621577.config.Config.ROWS;

/**
 * Reader class that is needed to read the data from the file
 * validate it and prepare it for storing the client input
 * tot the actual table repository.
 */
public class TableReader {

  private final BufferedReader inputReader;

  public TableReader(File file) throws FileNotFoundException {
    this.inputReader = new BufferedReader(new FileReader(file));
  }

  public String[][] read() throws IOException {

    String[][] result = new String[ROWS][COLS];

    int row = 0;
    String line;

    while ((line = inputReader.readLine()) != null) {
      String[] cells = line.split(",");

      for (int col = 0; col < cells.length; col++) {
        result[row][col] = cells[col].trim();
      }
      row++;
    }

    return result;
  }
}
