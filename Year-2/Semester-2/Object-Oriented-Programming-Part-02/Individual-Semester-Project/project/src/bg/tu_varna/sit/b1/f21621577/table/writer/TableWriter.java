package bg.tu_varna.sit.b1.f21621577.table.writer;

import bg.tu_varna.sit.b1.f21621577.table.cell.CellType;
import bg.tu_varna.sit.b1.f21621577.table.cell.TableCell;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static bg.tu_varna.sit.b1.f21621577.config.Config.CELLS_OUTPUT_SEPARATOR;

/**
 * Writer class that is needed to write the table data to a file.
 */
public class TableWriter implements AutoCloseable {

  private final BufferedWriter outputWriter;

  /**
   * Constructs a new TableWriter instance with the given file path.
   * If the file does not exist, it will be created.
   *
   * @param path the file path to write to
   * @throws IOException if an I/O error occurs when creating the file or creating the BufferedWriter
   */
  public TableWriter(Path path) throws IOException {

    if (!Files.exists(path)) {
      Files.createFile(path);
    }
    this.outputWriter = Files.newBufferedWriter(path);
  }

  /**
   * Writes the given table data to the output writer.
   *
   * @param tableData the table data to write
   * @throws IOException if an I/O error occurs
   */
  public void write(TableCell[][] tableData) throws IOException {

    for (TableCell[] tableDatum : tableData) {
      StringBuilder lineBuilder = new StringBuilder();

      for (int col = 0; col < tableDatum.length; col++) {
        TableCell cell = tableDatum[col];
        if (cell.getType() == CellType.STRING) {
          String value = cell.getValueAsString();
          value = value.replace("\\", "\\\\");
          value = value.replace("\"", "\\\"");
          lineBuilder.append("\"").append(value).append("\"");
        } else {
          lineBuilder.append(cell.getValueAsString());
        }
        if (col < tableDatum.length - 1) {
          lineBuilder.append(CELLS_OUTPUT_SEPARATOR);
        }
      }

      outputWriter.write(lineBuilder.toString());
      outputWriter.newLine();
    }

    outputWriter.flush();
  }

  /**
   * Closes the output writer.
   *
   * @throws IOException if an I/O error occurs
   */
  @Override
  public void close() throws IOException {
    outputWriter.close();
  }
}