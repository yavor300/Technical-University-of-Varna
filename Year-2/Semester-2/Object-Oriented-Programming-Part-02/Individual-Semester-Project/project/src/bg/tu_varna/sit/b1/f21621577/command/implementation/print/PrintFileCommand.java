package bg.tu_varna.sit.b1.f21621577.command.implementation.print;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.command.implementation.formulacalculator.FormulaCalculator;
import bg.tu_varna.sit.b1.f21621577.table.cell.CellType;
import bg.tu_varna.sit.b1.f21621577.table.cell.TableCell;
import bg.tu_varna.sit.b1.f21621577.table.repository.TableRepository;

public class PrintFileCommand implements Command {

  /**
   * Executes the command to display the contents of the table in a formatted string.
   *
   * @return a string representing the contents of the table
   */
  @Override
  public String execute() {

    TableRepository table = TableRepository.getInstance();
    int numRows = table.getNumRows();
    int numCols = table.getNumColumns();
    int[] colWidths = computeColumnWidths(table, numRows, numCols);

    return getTableData(table, numRows, numCols, colWidths);
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
        if (cell.getType() != CellType.EMPTY) {
          String cellValue;
          if (cell.getType() == CellType.FORMULA) {
            cellValue = String.valueOf(
                    FormulaCalculator.getInstance().evaluate(cell.getValueAsString()));
          } else {
            cellValue = cell.getValueAsString();
          }
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
   * Returns the string representation of the table data, formatted as a table with rows and columns.
   *
   * @param table     the table repository object that holds the cell data
   * @param numRows   the number of rows in the table
   * @param numCols   the number of columns in the table
   * @param colWidths an array of integers containing the width of each column in characters
   * @return a string representation of the table data
   */
  private String getTableData(TableRepository table, int numRows, int numCols, int[] colWidths) {

    StringBuilder tableData = new StringBuilder();

    for (int i = 0; i < numRows; i++) {
      tableData.append("| ");
      for (int j = 0; j < numCols; j++) {
        TableCell cell = table.getCell(i, j);
        int colWidth = colWidths[j];
        if (cell.getType() != CellType.EMPTY) {
          String cellValue = cell.getValueAsString();
          if (cell.getType() == CellType.FORMULA) {
            cellValue = String.valueOf(FormulaCalculator.getInstance().evaluate(cellValue));
          }
          if (j == 0) {
            tableData.append(String.format("%-" + colWidth + "s", cellValue));
          } else {
            tableData.append(String.format("%" + colWidth + "s", cellValue));
          }
        } else {
          tableData.append(String.format("%" + colWidth + "s", ""));
        }
        tableData.append(" | ");
      }
      tableData.append(System.lineSeparator());
    }

    return tableData.toString().trim();
  }
}