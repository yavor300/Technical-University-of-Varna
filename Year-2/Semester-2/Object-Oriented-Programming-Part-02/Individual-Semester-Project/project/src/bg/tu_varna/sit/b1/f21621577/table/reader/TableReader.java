package bg.tu_varna.sit.b1.f21621577.table.reader;

import bg.tu_varna.sit.b1.f21621577.table.cell.TableCell;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static bg.tu_varna.sit.b1.f21621577.config.Config.CELLS_INPUT_SEPARATOR;
import static bg.tu_varna.sit.b1.f21621577.config.Config.COLS;
import static bg.tu_varna.sit.b1.f21621577.config.Config.ROWS;

/**
 * A reader class that is used to read data from a file, validate it,
 * and prepare it for storing the client input in the actual table repository.
 */
public class TableReader implements AutoCloseable {

  private final BufferedReader inputReader;

  /**
   * Constructs a new {@code TableReader} instance with the specified file path. If the file does not exist, a new file
   * will be created.
   *
   * @param path the path of the file to read
   * @throws IOException if an I/O error occurs while opening the file
   */
  public TableReader(Path path) throws IOException {
    if (!Files.exists(path)) {
      Files.createFile(path);
    }
    this.inputReader = Files.newBufferedReader(path);
  }

  /**
   * Reads the data from the file and returns a 2D array of {@code TableCell} objects.
   *
   * @return a 2D array of {@code TableCell} objects
   * @throws IOException if an I/O error occurs while reading the file
   */
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

  /**
   * Closes the input stream.
   *
   * @throws IOException if an I/O error occurs while closing the stream
   */
  @Override
  public void close() throws IOException {
    inputReader.close();
  }
}