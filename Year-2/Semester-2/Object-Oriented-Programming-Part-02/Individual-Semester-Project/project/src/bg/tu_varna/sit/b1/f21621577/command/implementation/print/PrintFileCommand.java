package bg.tu_varna.sit.b1.f21621577.command.implementation.print;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.table.cell.CellType;
import bg.tu_varna.sit.b1.f21621577.table.cell.TableCell;
import bg.tu_varna.sit.b1.f21621577.table.repository.TableRepository;

import java.util.List;

public class PrintFileCommand extends Command {

  public PrintFileCommand(List<String> arguments) {
    super(arguments);
  }

  /**
   * Executes the print table command.
   */
  @Override
  public boolean execute() {

    TableRepository table = TableRepository.getInstance();

    int numRows = table.getNumRows();
    int numCols = table.getNumColumns();

    // Compute the maximum length of each column
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

    // Print table data
    for (int i = 0; i < numRows; i++) {
      System.out.print("| ");
      for (int j = 0; j < numCols; j++) {
        TableCell cell = table.getCell(i, j);
        if (cell != null) {
          String cellValue = cell.getValueAsString();
          int colWidth = colWidths[j];
          if (j == 0) {
            // Float the first column of numbers to the left
            System.out.printf("%-" + colWidth + "s", cellValue);
          } else {
            // Align the rest of the columns with the numbers to the right
            if (cell.getType() == CellType.INTEGER || cell.getType() == CellType.FRACTIONAL) {
              System.out.printf("%" + colWidth + "s", cellValue);
            } else {
              System.out.printf("%-" + colWidth + "s", cellValue);
            }
          }
        }
        System.out.print(" | ");
      }
      System.out.println();
    }

    return true;
  }
}