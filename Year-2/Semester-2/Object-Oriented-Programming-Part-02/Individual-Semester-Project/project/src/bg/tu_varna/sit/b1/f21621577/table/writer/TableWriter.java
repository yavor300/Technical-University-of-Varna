package bg.tu_varna.sit.b1.f21621577.table.writer;

import static bg.tu_varna.sit.b1.f21621577.regex.Patterns.DOUBLE_BACKSLASH;
import static bg.tu_varna.sit.b1.f21621577.regex.Patterns.ESCAPED_CELLS_OUTPUT_SEPARATOR;
import static bg.tu_varna.sit.b1.f21621577.regex.Patterns.ESCAPED_QUOTE;
import static bg.tu_varna.sit.b1.f21621577.regex.Patterns.NON_UNESCAPED_QUOTE;
import static bg.tu_varna.sit.b1.f21621577.regex.Patterns.SINGLE_BACKSLASH;
import bg.tu_varna.sit.b1.f21621577.table.cell.CellType;
import bg.tu_varna.sit.b1.f21621577.table.cell.TableCell;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static bg.tu_varna.sit.b1.f21621577.config.Config.CELLS_OUTPUT_SEPARATOR;

/**
 * Writer class that is needed to write the table data to a file.
 * <p>
 * Клас Writer, който е необходим за запис на данните от таблицата във файл.
 */
public class TableWriter implements AutoCloseable {

  /**
   * The buffered writer used to write to the output file.
   * <p>
   * Буферираният писател, използван за запис в изходния файл.
   */
  private final BufferedWriter outputWriter;

  /**
   * Constructs a new TableWriter instance with the given file path.
   * If the file does not exist, it will be created.
   * <p>
   * Конструира нова инстанция на TableWriter с дадения файлов път.
   * Ако файлът не съществува, той ще бъде създаден.
   *
   * @param path the file path to write to
   *             <p>
   *             пътя на файла за запис
   * @throws IOException if an I/O error occurs when creating the file or creating the BufferedWriter
   *                     <p>
   *                     ако възникне I/O грешка при създаване на файла или създаване на BufferedWriter
   */
  public TableWriter(Path path) throws IOException {

    if (!Files.exists(path)) {
      Files.createFile(path);
    }

    this.outputWriter = Files.newBufferedWriter(path);
  }

  /**
   * Writes the given table data to the output writer.
   * <p>
   * Записва дадените таблични данни в изходния файл.
   *
   * @param tableData the table data to write
   *                  <p>
   *                  данните от таблицата за запис
   * @throws IOException if an I/O error occurs
   *                     <p>
   *                     ако възникне I/O грешка
   */
  public void write(TableCell[][] tableData) throws IOException {

    for (TableCell[] tableDatum : tableData) {
      StringBuilder lineBuilder = new StringBuilder();

      for (int col = 0; col < tableDatum.length; col++) {
        TableCell cell = tableDatum[col];

        if (cell.getType() == CellType.STRING) {
          String value = cell.getValueAsString();
          value = value.replace(SINGLE_BACKSLASH, DOUBLE_BACKSLASH);
          value = value.replace(NON_UNESCAPED_QUOTE, ESCAPED_QUOTE);
          value = value.replace(CELLS_OUTPUT_SEPARATOR, ESCAPED_CELLS_OUTPUT_SEPARATOR);
          lineBuilder.append(NON_UNESCAPED_QUOTE).append(value).append(NON_UNESCAPED_QUOTE);
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
   * <p>
   * Затваря изходния запис.
   *
   * @throws IOException if an I/O error occurs
   *                     <p>
   *                     ако възникне I/O грешка
   */
  @Override
  public void close() throws IOException {
    outputWriter.close();
  }
}