package bg.tu_varna.sit.b1.f21621577.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This class contains static constants used throughout the application for configuration purposes.
 * <p>
 * Този клас съдържа статични константи, използвани в цялото приложение за целите на конфигурацията.
 */
public class Config {

  /**
   * The default number of rows in a new table.
   * <p>
   * Броят редове по подразбиране в нова таблица.
   */
  public static final int ROWS;

  /**
   * The default number of columns in a new table.
   * <p>
   * Броят на колоните по подразбиране в нова таблица.
   */
  public static final int COLS;

  /**
   * The separator used for outputting cell values.
   * <p>
   * Разделителят, използван за извеждане на стойностите на клетката.
   */
  public static final String CELLS_OUTPUT_SEPARATOR;

  /**
   * The separator used for reading on input cell values.
   * <p>
   * Разделителят, използван за четене при въвеждане на стойностите на клетката.
   */
  public static final String CELLS_INPUT_SEPARATOR;

  /**
   * The default directory for table resource files.
   * <p>
   * Директорията по подразбиране за файлове с ресурси на таблица.
   */
  public static final String RESOURCES_DIRECTORY;

  /**
   * The default filename for new tables.
   * <p>
   * Файловото име по подразбиране за нови таблици.
   */
  public static final String DEFAULT_TABLE_FILENAME;

  /**
   * The value indicating an error in a cell.
   * <p>
   * Стойността, показваща грешка в клетка.
   */
  public static final String CELL_ERROR_VALUE;

  static {
    Properties properties = new Properties();
    try {
      properties.load(new FileInputStream("config.properties"));
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }

    ROWS = Integer.parseInt(properties.getProperty("rows", "100"));
    COLS = Integer.parseInt(properties.getProperty("cols", "100"));
    CELLS_OUTPUT_SEPARATOR = properties.getProperty("cells.output.separator", ",");
    CELLS_INPUT_SEPARATOR = properties.getProperty("cells.input.separator", ",");
    RESOURCES_DIRECTORY = properties.getProperty("resources.directory",
            new File(Config.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent());
    DEFAULT_TABLE_FILENAME = properties.getProperty("default.table.filename", "data.csv");
    CELL_ERROR_VALUE = properties.getProperty("cell.error.value", "ERROR");
  }
}
