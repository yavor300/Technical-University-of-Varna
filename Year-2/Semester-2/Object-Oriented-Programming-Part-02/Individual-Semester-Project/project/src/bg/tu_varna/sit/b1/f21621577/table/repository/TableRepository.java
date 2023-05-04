package bg.tu_varna.sit.b1.f21621577.table.repository;

import bg.tu_varna.sit.b1.f21621577.table.cell.CellType;
import bg.tu_varna.sit.b1.f21621577.table.cell.TableCell;

import java.util.Arrays;

import static bg.tu_varna.sit.b1.f21621577.config.Config.COLS;
import static bg.tu_varna.sit.b1.f21621577.config.Config.DEFAULT_TABLE_FILENAME;
import static bg.tu_varna.sit.b1.f21621577.config.Config.ROWS;

/**
 * The TableRepository class is responsible for storing and managing the table data.
 * It is implemented as a singleton class to ensure that only one instance of the table is created.
 */
public class TableRepository {

  private boolean isTableOpened = false;
  private String tableFileName = DEFAULT_TABLE_FILENAME;
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
  public void loadData(TableCell[][] data) {

    int maxRow = findMaxRowIndex(data);
    int maxCol = findMaxColIndex(data);

    TableCell[][] newData = createNewDataArray(maxRow, maxCol);
    copyNonNullValues(data, newData, maxRow, maxCol);

    table = newData;
    isTableOpened = true;
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


  /**
   * Sets the given {@link TableCell} object at the specified row and column index in the table.
   *
   * @param row  the row index of the cell to set
   * @param col  the column index of the cell to set
   * @param cell the {@link TableCell} object to set at the specified row and column index
   * @throws IllegalArgumentException if the row or column index is out of bounds
   */
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
   * Returns a boolean indicating whether a table is currently opened.
   *
   * @return true if a table is opened, false otherwise
   */
  public boolean isTableOpened() {
    return isTableOpened;
  }

  /**
   * Sets the file name of the currently opened table.
   *
   * @param tableFileName the file name to set
   */
  public void setTableFileName(String tableFileName) {
    this.tableFileName = tableFileName;
  }

  /**
   * Returns the file name of the currently opened table.
   *
   * @return the file name of the currently opened table
   */
  public String getTableFileName() {
    return tableFileName;
  }

  /**
   * Clears the entire table by setting all cells to null values.
   * This method also sets the isTableOpened flag to false.
   */
  public void clear() {
    for (TableCell[] tableCells : table) {
      Arrays.fill(tableCells, null);
    }
    this.isTableOpened = false;
  }

  /**
   * Scales the table to the specified size, creating new empty cells if necessary.
   *
   * @param newRowSize the new number of rows
   * @param newColSize the new number of columns
   * @throws IllegalArgumentException if either newRowSize or newColSize are negative
   */
  public void scale(int newRowSize, int newColSize) {

    if (newRowSize < 0 || newColSize < 0) {
      throw new IllegalArgumentException("Invalid size: " + newRowSize + " x " + newColSize);
    }

    TableCell[][] newTable = new TableCell[newRowSize][newColSize];

    for (int row = 0; row < newRowSize; row++) {
      for (int col = 0; col < newColSize; col++) {
        if (row < table.length && col < table[row].length) {
          newTable[row][col] = table[row][col];
        } else {
          newTable[row][col] = new TableCell();
        }
      }
    }

    table = newTable;
  }

  /**
   * Removes empty rows and columns from the bottom and right side of the table. If the table is
   * completely empty, this method does nothing.
   * This method finds the last non-empty row and column, computes the new size of the table,
   * creates a new table with the new size, and copies the non-empty cells to the new table.
   *
   * @throws IllegalArgumentException if any of the cell values are null
   */
  public void shrink() {

    int lastNonEmptyRow = findLastNonEmptyRow();
    int lastNonEmptyCol = findLastNonEmptyColumn();

    if (lastNonEmptyRow < 0) {
      return;
    }

    TableCell[][] newTable = createNewDataArray(lastNonEmptyRow, lastNonEmptyCol);

    copyFilledCells(newTable, lastNonEmptyRow, lastNonEmptyCol);

    table = newTable;
  }

  /**
   * Finds the last non-empty row in the table.
   *
   * @return the index of the last non-empty row, or -1 if all rows are empty
   */
  private int findLastNonEmptyRow() {

    for (int row = table.length - 1; row >= 0; row--) {
      for (int col = table[row].length - 1; col >= 0; col--) {
        if (!table[row][col].getType().equals(CellType.EMPTY)) {
          return row;
        }
      }
    }
    return -1;
  }

  /**
   * Finds the index of the last non-empty column in the table.
   *
   * @return The index of the last non-empty column, or -1 if all columns are empty.
   */
  private int findLastNonEmptyColumn() {

    for (int col = table[0].length - 1; col >= 0; col--) {
      for (int row = table.length - 1; row >= 0; row--) {
        if (!table[row][col].getType().equals(CellType.EMPTY)) {
          return col;
        }
      }
    }

    return -1;
  }

  /**
   * Copies the non-empty cells from the current table to a new table
   *
   * @param newTable        the new table to copy the non-empty cells to
   * @param lastNonEmptyRow the index of the last non-empty row in the current table
   * @param lastNonEmptyCol the index of the last non-empty column in the current table
   */
  private void copyFilledCells(TableCell[][] newTable, int lastNonEmptyRow, int lastNonEmptyCol) {
    for (int row = 0; row <= lastNonEmptyRow; row++) {
      for (int col = 0; col <= lastNonEmptyCol; col++) {
        newTable[row][col] = table[row][col];
      }
    }
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
   * Copies the non-null values from the source table cell array to the destination table cell array. If a
   * null value is found in the source array, a new TableCell object is created with an empty value and
   * assigned to the corresponding cell in the destination array.
   *
   * @param source      the source table cell array to copy the non-null values from
   * @param destination the destination table cell array to copy the non-null values to
   * @param maxRow      the maximum row index
   * @param maxCol      the maximum column index
   */
  private void copyNonNullValues(TableCell[][] source, TableCell[][] destination, int maxRow, int maxCol) {

    for (int row = 0; row <= maxRow; row++) {
      for (int col = 0; col <= maxCol; col++) {
        if (source[row][col] != null) {
          destination[row][col] = source[row][col];
        } else {
          destination[row][col] = new TableCell();
        }
      }
    }
  }

}
