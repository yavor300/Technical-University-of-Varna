package bg.tu_varna.sit.b1.f21621577.command.implementation.print;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.table.cell.TableCell;
import bg.tu_varna.sit.b1.f21621577.table.repository.TableRepository;

public class PrintFileCommand implements Command {

  /**
   * Executes the command to display the contents of the table.
   *
   * @return true if the command is executed successfully
   */
  @Override
  public boolean execute() {

    TableRepository table = TableRepository.getInstance();
    int numRows = table.getNumRows();
    int numCols = table.getNumColumns();
    int[] colWidths = computeColumnWidths(table, numRows, numCols);
    printTableData(table, numRows, numCols, colWidths);

    return true;
  }

  /**
   * Computes the maximum width for each column in the specified table.
   *
   * @param table   the table to compute the column widths for
   * @param numRows the number of rows in the table
   * @param numCols the number of columns in the table
   * @return an array containing the maximum width for each column
   */
  private int[] computeColumnWidths(TableRepository table, int numRows, int numCols) {

    int[] colWidths = new int[numCols];
    for (int j = 0; j < numCols; j++) {
      int maxColWidth = 0;
      for (int i = 0; i < numRows; i++) {
        TableCell cell = table.getCell(i, j);
        if (cell != null) {
          String cellValue = cell.getValueAsString();
          if (cellValue.length() > maxColWidth) {
            maxColWidth = cellValue.length();
          }
        }
      }
      colWidths[j] = maxColWidth;
    }

    return colWidths;
  }

  /**
   * Prints the table data to the console, using the provided table, number of rows and columns, and an array of column
   * widths.
   *
   * @param table     the table containing the data to be printed
   * @param numRows   the number of rows in the table
   * @param numCols   the number of columns in the table
   * @param colWidths an array of integers representing the maximum width of each column
   */
  private void printTableData(TableRepository table, int numRows, int numCols, int[] colWidths) {

    for (int i = 0; i < numRows; i++) {
      System.out.print("| ");
      for (int j = 0; j < numCols; j++) {
        TableCell cell = table.getCell(i, j);
        int colWidth = colWidths[j];
        if (cell != null) {
          String cellValue = cell.getValueAsString();
          if (j == 0) {
            System.out.printf("%-" + colWidth + "s", cellValue);
          } else {
            System.out.printf("%" + colWidth + "s", cellValue);
          }
        } else {
          System.out.printf("%" + colWidth + "s", "");
        }
        System.out.print(" | ");
      }
      System.out.println();
    }
  }
}