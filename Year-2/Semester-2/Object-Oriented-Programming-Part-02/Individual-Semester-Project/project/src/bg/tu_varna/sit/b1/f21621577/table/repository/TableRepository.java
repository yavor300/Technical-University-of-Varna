package bg.tu_varna.sit.b1.f21621577.table.repository;

import bg.tu_varna.sit.b1.f21621577.table.cell.CellType;
import bg.tu_varna.sit.b1.f21621577.table.cell.TableCell;

import java.util.Arrays;

import static bg.tu_varna.sit.b1.f21621577.config.Config.COLS;
import static bg.tu_varna.sit.b1.f21621577.config.Config.DEFAULT_TABLE_FILENAME;
import static bg.tu_varna.sit.b1.f21621577.config.Config.INVALID_ROW_INDEX_MESSAGE;
import static bg.tu_varna.sit.b1.f21621577.config.Config.INVALID_SIZE_ERROR_MESSAGE;
import static bg.tu_varna.sit.b1.f21621577.config.Config.ROWS;

/**
 * The TableRepository class is responsible for storing and managing the table data.
 * It is implemented as a singleton class to ensure that only one instance of the table is created.
 * <p>
 * Класът TableRepository отговаря за съхраняването и управлението на данните от таблицата.
 * Той е реализиран като единичен клас, за да се гарантира, че е създаден само една инстанция на таблицата.
 */
public class TableRepository {

  /**
   * A boolean flag indicating whether a table is currently open.
   * <p>
   * Булев флаг, показващ дали таблицата е отворена в момента.
   */
  private boolean isTableOpened = false;

  /**
   * The name of the table file. The default value is DEFAULT_TABLE_FILENAME.
   * <p>
   * Името на файла на таблицата. Стойността по подразбиране е DEFAULT_TABLE_FILENAME.
   */
  private String tableFileName = DEFAULT_TABLE_FILENAME;

  /**
   * A two-dimensional array representing the contents of the table. The array is of size ROWS x COLS, where ROWS and
   * COLS are constants.
   * <p>
   * Двуизмерен масив, представящ съдържанието на таблицата. Масивът е с размер ROWS x COLS, където ROWS и
   * COLS са константи.
   */
  private TableCell[][] table = new TableCell[ROWS][COLS];

  /**
   * The TableRepository class is a singleton and does not allow instantiation of multiple objects.
   * Therefore, its constructor is private and inaccessible to external classes.
   * <p>
   * Класът TableRepository е сингълтън и не позволява инстанциране на множество обекти.
   * Следователно неговият конструктор е частен и недостъпен за външни класове.
   */
  private TableRepository() {
  }

  /**
   * Private static inner class to implement the singleton pattern.
   * <p>
   * Частен статичен вътрешен клас за прилагане на единичния модел.
   */
  private static class Singleton {
    private static final TableRepository INSTANCE
            = new TableRepository();
  }

  /**
   * Returns the instance of the TableRepository class.
   * <p>
   * Връща инстанцията на класа TableRepository.
   *
   * @return the instance of the TableRepository class
   * <p>
   * инстанцията на класа TableRepository.
   */
  public static TableRepository getInstance() {
    return Singleton.INSTANCE;
  }

  /**
   * Saves the given data into the table.
   * <p>
   * Записва дадените данни в таблицата.
   *
   * @param data the data to be saved into the table
   *             <p>
   *             данните, които да бъдат записани в таблицата
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
   * <p>
   * Връща броя на редовете в таблицата.
   *
   * @return the number of rows in the table
   * <p>
   * броя на редовете в таблицата.
   */
  public int getNumRows() {
    return table.length;
  }

  /**
   * Gets the number of columns in the table.
   * <p>
   * Връща броя на колоните в таблицата.
   *
   * @return the number of columns in the table
   * <p>
   * броя на колоните в таблицата.
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
   * <p>
   * Извлича клетката на таблицата при посочените индекси на ред и колона.
   *
   * @param row    the row index of the cell
   *               <p>
   *               индексът на реда на клетката
   * @param column the column index of the cell
   *               <p>
   *               индексът на колоната на клетката
   * @return the TableCell object at the specified indices
   * <p>
   * обектът TableCell при посочените индекси
   * @throws IllegalArgumentException if the specified row or column index is out of range
   *                                  <p>
   *                                  ако указаният индекс на ред или колона е извън диапазона
   */
  public TableCell getCell(int row, int column) {

    if (row < 0 || row >= table.length) {
      throw new IllegalArgumentException(String.format(INVALID_ROW_INDEX_MESSAGE, row));
    }
    if (column < 0 || column >= table[row].length) {
      throw new IllegalArgumentException(String.format(INVALID_ROW_INDEX_MESSAGE, column));
    }

    return table[row][column];
  }


  /**
   * Sets the given {@link TableCell} object at the specified row and column index in the table.
   * <p>
   * Задава дадения обект {@link TableCell} на посочения индекс на ред и колона в таблицата.
   *
   * @param row  the row index of the cell to set
   *             <p>
   *             индексът на реда на клетката за задаване
   * @param col  the column index of the cell to set
   *             <p>
   *             индексът на колоната на клетката, която да зададете
   * @param cell the {@link TableCell} object to set at the specified row and column index
   *             <p>
   *             обект за задаване на посочения индекс на ред и колона
   * @throws IllegalArgumentException if the row or column index is out of bounds
   *                                  <p>
   *                                  ако индексът на реда или колоната е извън границите
   */
  public void setCell(int row, int col, TableCell cell) {

    if (row < 0 || row >= getNumColumns()) {
      throw new IllegalArgumentException(String.format(INVALID_ROW_INDEX_MESSAGE, row));
    }
    if (col < 0 || col >= getNumColumns()) {
      throw new IllegalArgumentException(String.format(INVALID_ROW_INDEX_MESSAGE, col));
    }

    table[row][col] = cell;
  }

  /**
   * Returns the current state of the table.
   * <p>
   * Връща текущото състояние на таблицата.
   *
   * @return the current state of the table
   * <p>
   * текущото състояние на таблицата
   */
  public TableCell[][] getTable() {
    return table;
  }

  /**
   * Returns a boolean indicating whether a table is currently opened.
   * <p>
   * Връща булево значение, указващо дали дадена таблица е отворена в момента.
   *
   * @return true if a table is opened, false otherwise
   * <p>
   * true, ако таблица е отворена, false в противен случай
   */
  public boolean isTableOpened() {
    return isTableOpened;
  }

  /**
   * Sets the file name of the currently opened table.
   * <p>
   * Задава името на файла на текущо отворената таблица.
   *
   * @param tableFileName the file name to set
   *                      <p>
   *                      името на файла, което да зададете
   */
  public void setTableFileName(String tableFileName) {
    this.tableFileName = tableFileName;
  }

  /**
   * Returns the file name of the currently opened table.
   * <p>
   * Връща името на файла на текущо отворената таблица.
   *
   * @return the file name of the currently opened table
   * <p>
   * името на файла на текущо отворената таблица
   */
  public String getTableFileName() {
    return tableFileName;
  }

  /**
   * Clears the entire table by setting all cells to null values.
   * This method also sets the isTableOpened flag to false.
   * <p>
   * Изчиства цялата таблица, като зададе всички клетки на празни стойности.
   * Този метод също задава флага isTableOpened на false.
   */
  public void clear() {

    for (TableCell[] tableCells : table) {
      Arrays.fill(tableCells, null);
    }

    this.isTableOpened = false;
  }

  /**
   * Scales the table to the specified size, creating new empty cells if necessary.
   * The method first checks whether the provided newRowSize and newColSize are valid.
   * If either value is negative, it throws an IllegalArgumentException
   * with a message that indicates the invalid size.
   * Then the method creates a new 2D array of TableCell objects with the dimensions of
   * newRowSize and newColSize. It then iterates over the new array, and for each cell in
   * the new array, it checks if there is a corresponding cell in the current table array
   * (by comparing row and column indices). If there is a corresponding cell, it copies the
   * contents of the cell from the current table array to the new array. If there is no corresponding
   * cell in the current table array, it creates a new TableCell object and assigns it to the new cell in the new array.
   * Finally, the method sets the table field to the new array, effectively replacing
   * the old table with the new table of the specified size.
   * <p>
   * Мащабира таблицата до посочения размер, създавайки нови празни клетки, ако е необходимо.
   * Методът първо проверява дали предоставените newRowSize и newColSize са валидни.
   * Ако някоя от стойностите е отрицателна, тя хвърля изключение IllegalArgumentException
   * със съобщение, което показва невалиден размер.
   * След това методът създава нов 2D масив от обекти TableCell с размерите на
   * newRowSize и newColSize. След това итерира новия масив и за всяка клетка в
   * новия масив, той проверява дали има съответстваща клетка в текущия табличен масив
   * (чрез сравняване на индекси на ред и колона). Ако има съответстваща клетка, тя копира
   * съдържанието на клетката от текущия табличен масив към новия масив. Ако няма съответстващо
   * клетка в текущия табличен масив, той създава нов обект TableCell и го присвоява на новата клетка в новия масив.
   * И накрая, методът настройва полето на таблицата към новия масив, като ефективно замества
   * старата таблица с новата таблица с посочения размер.
   *
   * @param newRowSize the new number of rows
   *                   <p>
   *                   новия брой редове
   * @param newColSize the new number of columns
   *                   <p>
   *                   новия брой колони
   * @throws IllegalArgumentException if either newRowSize or newColSize are negative
   *                                  <p>
   *                                  ако newRowSize или newColSize са отрицателни
   */
  public void scale(int newRowSize, int newColSize) {

    if (newRowSize < 0 || newColSize < 0) {
      throw new IllegalArgumentException(String.format(INVALID_SIZE_ERROR_MESSAGE, newRowSize, newColSize));
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
   * <p>
   * Премахва празни редове и колони от долната и дясната страна на таблицата. Ако таблицата е
   * напълно празен, този метод не прави нищо.
   * Този метод намира последния непразен ред и колона, изчислява новия размер на таблицата,
   * създава нова таблица с новия размер и копира непразните клетки в новата таблица.
   *
   * @throws IllegalArgumentException if any of the cell values are null
   *                                  <p>
   *                                  ако някоя от стойностите на клетката е null
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
   * <p>
   * Намира последния непразен ред в таблицата.
   *
   * @return the index of the last non-empty row, or -1 if all rows are empty
   * <p>
   * индексът на последния непразен ред или -1, ако всички редове са празни
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
   * <p>
   * Намира индекса на последната непразна колона в таблицата.
   *
   * @return The index of the last non-empty column, or -1 if all columns are empty.
   * <p>
   * Индексът на последната непразна колона или -1, ако всички колони са празни.
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
   * <p>
   * Копира непразните клетки от текущата таблица в нова таблица
   *
   * @param newTable        the new table to copy the non-empty cells to
   *                        новата таблица, в която да копирате непразните клетки
   * @param lastNonEmptyRow the index of the last non-empty row in the current table
   *                        индексът на последния непразен ред в текущата таблица
   * @param lastNonEmptyCol the index of the last non-empty column in the current table
   *                        индексът на последната непразна колона в текущата таблица
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
   * <p>
   * Намира максималния индекс на ред с различни от null стойности в дадените данни.
   *
   * @param data the data to be searched for the maximum row index
   *             <p>
   *             данните, които трябва да се търсят за максималния индекс на реда
   * @return the maximum row index with non-null values
   * <p>
   * максималния индекс на ред с различни от null стойности
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
   * <p>
   * Намира максималния индекс на колона с различни от null стойности в дадените данни.
   *
   * @param data the data to be searched for the maximum column index
   *             <p>
   *             данните, които трябва да се търсят за максималния индекс на колона
   * @return the maximum column index with non-null values
   * <p>
   * максималния индекс на колона с различни от null стойности
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
   * <p>
   * Създава нов масив от данни за таблица с дадените максимални индекси на редове и колони.
   *
   * @param maxRow the maximum row index
   *               <p>
   *               максималния индекс на реда
   * @param maxCol the maximum column index
   *               <p>
   *               максималния индекс на колона
   * @return a new table data array with the given maximum row and column indexes
   * <p>
   * нов масив от таблични данни с дадените максимални индекси на редове и колони
   */
  private TableCell[][] createNewDataArray(int maxRow, int maxCol) {

    return new TableCell[maxRow + 1][maxCol + 1];
  }

  /**
   * Copies the non-null values from the source table cell array to the destination table cell array. If a
   * null value is found in the source array, a new TableCell object is created with an empty value and
   * assigned to the corresponding cell in the destination array.
   * <p>
   * Копира различни от null стойности от масива от клетки на таблицата източник в масива от клетки на таблицата местоназначение. Ако
   * в изходния масив е намерена null стойност, създава се нов обект TableCell с празна стойност и
   * присвоен на съответната клетка в целевия масив.
   *
   * @param source      the source table cell array to copy the non-null values from
   *                    <p>
   *                    масивът от клетки на таблицата източник, от който да копирате различни от null стойности
   * @param destination the destination table cell array to copy the non-null values to
   *                    <p>
   *                    масива от клетки на таблицата местоназначение, в който да копирате различни от null стойности
   * @param maxRow      the maximum row index
   *                    <p>
   *                    максималния индекс на реда
   * @param maxCol      the maximum column index
   *                    <p>
   *                    максималния индекс на колона
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
