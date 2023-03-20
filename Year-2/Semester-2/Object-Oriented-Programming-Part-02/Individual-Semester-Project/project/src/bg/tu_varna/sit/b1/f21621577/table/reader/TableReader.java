package bg.tu_varna.sit.b1.f21621577.table.reader;

import bg.tu_varna.sit.b1.f21621577.table.cell.TableCell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import static bg.tu_varna.sit.b1.f21621577.config.Config.*;

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

  public TableCell[][] read() throws IOException {

    TableCell[][] result = new TableCell[ROWS][COLS];

    int row = 0;
    String line;
    while ((line = inputReader.readLine()) != null) {
      line = line.trim();
      String[] cells = line.split(CELLS_INPUT_SEPARATOR);

      for (int col = 0; col < cells.length; col++) {
        try {
          int number = Integer.parseInt(cells[col].trim());
          result[row][col] = new TableCell(number);
        } catch (NumberFormatException e) {
          result[row][col] = new TableCell(cells[col].trim());
        }
      }
      row++;
    }

    return result;
  }
}
