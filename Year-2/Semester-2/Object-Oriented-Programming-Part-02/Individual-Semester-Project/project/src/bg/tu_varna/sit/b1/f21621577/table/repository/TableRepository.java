package bg.tu_varna.sit.b1.f21621577.table.repository;

import static bg.tu_varna.sit.b1.f21621577.config.Config.CELLS_SEPARATOR;
import static bg.tu_varna.sit.b1.f21621577.config.Config.COLS;
import static bg.tu_varna.sit.b1.f21621577.config.Config.ROWS;

/**
 * Singleton repository class that holds the table data
 * as well as methods for saving and visualizing the existing
 * records.
 */
public class TableRepository {

  private final String[][] table = new String[ROWS][COLS];

  private TableRepository() {}

  private static class Singleton {
    private static final TableRepository INSTANCE
            = new TableRepository();
  }

  public static TableRepository getInstance() {
    return Singleton.INSTANCE;
  }

  public void save(String[][] data) {

    for (int row = 0; row < 100; row++) {
      if (data[row][0] == null) {
        break;
      }
      for (int col = 0; col < 100; col++) {
        if (data[row][col] == null) {
          break;
        }
        table[row][col] = data[row][col];
      }
    }
  }

  public String visualizeData() {

    StringBuilder stringBuilder = new StringBuilder();

    for (int row = 0; row < 100; row++) {
      if (table[row][0] == null) {
        break;
      }
      for (int col = 0; col < 100; col++) {
        if (table[row][col] == null) {
          break;
        }
        stringBuilder.append(table[row][col]).append(CELLS_SEPARATOR);
      }
      stringBuilder.append(System.lineSeparator());
    }

    return stringBuilder.toString();
  }

  public String[][] getTable() {
    return table;
  }
}
