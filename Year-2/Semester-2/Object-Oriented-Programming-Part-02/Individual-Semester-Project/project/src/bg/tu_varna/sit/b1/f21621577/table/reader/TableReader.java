package bg.tu_varna.sit.b1.f21621577.table.reader;

import bg.tu_varna.sit.b1.f21621577.table.cell.TableCell;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static bg.tu_varna.sit.b1.f21621577.config.Config.*;

/**
 * Reader class that is needed to read the data from the file
 * validate it and prepare it for storing the client input
 * tot the actual table repository.
 */
public class TableReader implements AutoCloseable {

  private final BufferedReader inputReader;

  public TableReader(Path path) throws IOException {
    this.inputReader = Files.newBufferedReader(path);
  }

  public TableCell[][] read() throws IOException {

    TableCell[][] result = new TableCell[ROWS][COLS];

    int row = 0;
    String line;

    while ((line = inputReader.readLine()) != null) {
      line = line.trim();
      String[] cells = line.split(CELLS_INPUT_SEPARATOR);

      for (int col = 0; col < cells.length; col++) {
        result[row][col] = new TableCell(cells[col].trim());
      }

      row++;
    }

    return result;
  }

  @Override
  public void close() throws IOException {
    inputReader.close();
  }
}