package bg.tu_varna.sit.b1.f21621577.table.repository;

import bg.tu_varna.sit.b1.f21621577.table.cell.TableCell;

import static bg.tu_varna.sit.b1.f21621577.config.Config.COLS;
import static bg.tu_varna.sit.b1.f21621577.config.Config.ROWS;

/**
 * The TableRepository class is responsible for storing and managing the table data.
 * It is implemented as a singleton class to ensure that only one instance of the table is created.
 */
public class TableRepository {

  private TableCell[][] table = new TableCell[ROWS][COLS];

  private TableRepository() {
  }

  /**
   * Private static inner class to implement the singleton pattern.
   */
  private static class Singleton {
    private static final TableRepository INSTANCE
            = new TableRepository();
  }

  /**
   * Returns the instance of the TableRepository class.
   *
   * @return the instance of the TableRepository class
   */
  public static TableRepository getInstance() {
    return Singleton.INSTANCE;
  }

  /**
   * Saves the given data into the table.
   *
   * @param data the data to be saved into the table
   */
  public void save(TableCell[][] data) {

    int maxRow = findMaxRowIndex(data);
    int maxCol = findMaxColIndex(data);

    TableCell[][] newData = createNewDataArray(maxRow, maxCol);
    copyNonNullValues(data, newData, maxRow, maxCol);

    table = newData;
  }

  /**
   * Gets the number of rows in the table.
   *
   * @return the number of rows in the table
   */
  public int getNumRows() {
    return table.length;
  }

  /**
   * Gets the number of columns in the table.
   *
   * @return the number of columns in the table
   */
  public int getNumColumns() {
    if (table.length == 0) {
      return 0;
    } else {
      return table[0].length;
    }
  }

  /**
   * Retrieves the table cell at the specified row and column indices.
   *
   * @param row    the row index of the cell
   * @param column the column index of the cell
   * @return the TableCell object at the specified indices
   * @throws IllegalArgumentException if the specified row or column index is out of range
   */
  public TableCell getCell(int row, int column) {

    if (row < 0 || row >= table.length) {
      throw new IllegalArgumentException("Invalid row index: " + row);
    }
    if (column < 0 || column >= table[row].length) {
      throw new IllegalArgumentException("Invalid column index: " + column);
    }
    return table[row][column];
  }

  public void setCell(int row, int col, TableCell cell) {

    if (row < 0 || row >= getNumColumns()) {
      throw new IllegalArgumentException("Invalid row index: " + row);
    }
    if (col < 0 || col >= getNumColumns()) {
      throw new IllegalArgumentException("Invalid column index: " + col);
    }

    table[row][col] = cell;
  }


  /**
   * Returns the current state of the table.
   *
   * @return the current state of the table
   */
  public TableCell[][] getTable() {
    return table;
  }

  /**
   * Finds the maximum row index with non-null values in the given data.
   *
   * @param data the data to be searched for the maximum row index
   * @return the maximum row index with non-null values
   */
  private int findMaxRowIndex(TableCell[][] data) {

    int maxRow = -1;

    for (int row = 0; row < data.length; row++) {
      for (int col = 0; col < data[row].length; col++) {
        if (data[row][col] != null) {
          if (row > maxRow) {
            maxRow = row;
          }
        }
      }
    }

    return maxRow;
  }

  /**
   * Finds the maximum column index with non-null values in the given data.
   *
   * @param data the data to be searched for the maximum column index
   * @return the maximum column index with non-null values
   */
  private int findMaxColIndex(TableCell[][] data) {

    int maxCol = -1;

    for (TableCell[] datum : data) {
      for (int col = 0; col < datum.length; col++) {
        if (datum[col] != null) {
          if (col > maxCol) {
            maxCol = col;
          }
        }
      }
    }

    return maxCol;
  }

  /**
   * Creates a new table data array with the given maximum row and column indexes.
   *
   * @param maxRow the maximum row index
   * @param maxCol the maximum column index
   * @return a new table data array with the given maximum row and column indexes
   */
  private TableCell[][] createNewDataArray(int maxRow, int maxCol) {

    return new TableCell[maxRow + 1][maxCol + 1];
  }

  /**
   * Copies the non-null values from the source data array to the destination data array.
   *
   * @param source      the source data array to copy the non-null values from
   * @param destination the destination data array to copy the non-null values to
   * @param maxRow      the maximum row index
   * @param maxCol      the maximum column index
   */
  private void copyNonNullValues(TableCell[][] source, TableCell[][] destination, int maxRow, int maxCol) {

    for (int row = 0; row <= maxRow; row++) {
      for (int col = 0; col <= maxCol; col++) {
        if (source[row][col] != null) {
          destination[row][col] = source[row][col];
        }
      }
    }
  }

}
