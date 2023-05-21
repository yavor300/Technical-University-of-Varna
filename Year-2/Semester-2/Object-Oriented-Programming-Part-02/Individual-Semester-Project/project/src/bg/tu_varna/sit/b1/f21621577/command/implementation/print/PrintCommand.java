package bg.tu_varna.sit.b1.f21621577.command.implementation.print;

import bg.tu_varna.sit.b1.f21621577.command.base.Command;
import bg.tu_varna.sit.b1.f21621577.calculator.FormulaCalculator;
import static bg.tu_varna.sit.b1.f21621577.constants.Messages.NO_DATA_MESSAGE;
import static bg.tu_varna.sit.b1.f21621577.constants.Messages.NO_TABLE_OPENED_MESSAGE;
import static bg.tu_varna.sit.b1.f21621577.constants.StatusCodes.SUCCESSFUL_STATUS_CODE;
import bg.tu_varna.sit.b1.f21621577.table.cell.CellType;
import bg.tu_varna.sit.b1.f21621577.table.cell.TableCell;
import bg.tu_varna.sit.b1.f21621577.table.repository.TableRepository;

import static bg.tu_varna.sit.b1.f21621577.config.Config.CELL_ERROR_VALUE;


/**
 * A command that displays the contents of the table in a formatted string.
 * This command reads the cell data from the current table repository object and
 * formats it into a table with rows and columns. It then returns the formatted
 * string representation of the table data. If no table is currently opened, the
 * command returns a message indicating that there is no opened table.
 * <p>
 * Команда, която показва съдържанието на таблицата във форматиран низ.
 * Тази команда чете данни с клетки от текущия обект на хранилището на таблицата и
 * го форматира в таблица с редове и колони. След това връща форматирания
 * низово представяне на данните от таблицата. Ако в момента няма отворена маса,
 * командата връща съобщение, което показва, че няма отворена таблица.
 */
public class PrintCommand extends Command {

  /**
   * Package-private constructor for creating a {@code PrintCommand} object.
   * <p>
   * Пакетно частен конструктор за създаване на обект {@code PrintCommand}.
   */
  PrintCommand() {
  }

  /**
   * Executes the command to display the contents of the table in a formatted string.
   * <p>
   * Изпълнява командата за показване на съдържанието на таблицата във форматиран низ.
   *
   * @return a string representing the contents of the table
   * <p>
   * низ, представящ съдържанието на таблицата
   */
  @Override
  public String execute() {

    TableRepository repository = TableRepository.getInstance();

    if (!repository.isTableOpened()) {
      return NO_TABLE_OPENED_MESSAGE;
    }

    int numRows = repository.getNumRows();
    int numCols = repository.getNumColumns();
    int[] colWidths = computeColumnWidths(repository, numRows, numCols);

    setStatusCode(SUCCESSFUL_STATUS_CODE);
    return getTableData(repository, numRows, numCols, colWidths);
  }

  /**
   * Computes the maximum width for each column in the specified table.
   * It takes as input a TableRepository object, which holds the cell data, as well as the number
   * of rows and columns in the table. It returns an array containing the maximum width for each column.
   * The algorithm first creates an array of integers called colWidths with length equal to the number
   * of columns in the table. Each element in colWidths represents the maximum width of the corresponding column.
   * Then, for each column in the table, the algorithm iterates through each row of that column and checks
   * the length of the cell value. If the cell is not empty, it determines the length of the cell value by
   * checking if the cell contains a formula or not. If it does, it evaluates the formula using a
   * FormulaCalculator object and gets the resulting value. If there is an error during the evaluation
   * of the formula, the cell value is set to "ERROR". Otherwise, if the cell does not contain a formula,
   * it simply gets the cell value as a string.
   * The algorithm updates the maxColWidth variable for each cell in the column, by checking
   * if the length of the cell value is greater than the current maxColWidth. Once all the cells
   * in the column have been checked, the algorithm sets the corresponding element in colWidths to maxColWidth.
   * After all columns have been processed, the algorithm returns the colWidths array containing
   * the maximum width for each column. This array is used by the {@link #getTableData(TableRepository, int, int, int[])} method to format
   * the output of the table data in a readable way.
   * <p>
   * Изчислява максималната ширина за всяка колона в указаната таблица.
   * Той приема като вход обект TableRepository, който съдържа данните от клетката, както и числото
   * от редове и колони в таблицата. Връща масив, съдържащ максималната ширина за всяка колона.
   * Алгоритъмът първо създава масив от цели числа, наречени colWidths с дължина, равна на числото
   * на колоните в таблицата. Всеки елемент в colWidths представлява максималната ширина на съответната колона.
   * След това, за всяка колона в таблицата, алгоритъмът преминава през всеки ред от тази колона и проверява
   * дължината на стойността на клетката. Ако клетката не е празна, тя определя дължината на стойността на клетката от
   * проверка дали клетката съдържа формула или не. Ако да, той оценява формулата с помощта на
   * FormulaCalculator обект и получава получената стойност. Ако има грешка по време на изчисляване
   * на формулата, стойността на клетката е зададена на "ГРЕШКА". В противен случай, ако клетката не съдържа формула,
   * просто получава стойността на клетката като низ.
   * Алгоритъмът актуализира променливата maxColWidth за всяка клетка в колоната, като постави отметка
   * ако дължината на стойността на клетката е по-голяма от текущата maxColWidth. След като всички клетки
   * в колоната са проверени, алгоритъмът задава съответния елемент в colWidths на maxColWidth.
   * След като всички колони бъдат обработени, алгоритъмът връща масива colWidths, съдържащ
   * максималната ширина за всяка колона. Този масив се използва от
   * метода {@link #getTableData(TableRepository, int, int, int[])} за форматиране и
   * извеждане на данните от таблицата по четим начин.
   *
   * @param table   the table to compute the column widths for
   *                <p>
   *                таблицата за изчисляване на ширините на колоните
   * @param numRows the number of rows in the table
   *                <p>
   *                броя на редовете в таблицата
   * @param numCols the number of columns in the table
   *                <p>
   *                броя на колоните в таблицата
   * @return an array containing the maximum width for each column
   * <p>
   * масив, съдържащ максималната ширина за всяка колона
   */
  private int[] computeColumnWidths(TableRepository table, int numRows, int numCols) {

    int[] colWidths = new int[numCols];
    for (int j = 0; j < numCols; j++) {
      int maxColWidth = 1;
      for (int i = 0; i < numRows; i++) {
        TableCell cell = table.getCell(i, j);
        if (cell.getType() != CellType.EMPTY) {
          String cellValue;
          if (cell.getType() == CellType.FORMULA) {
            try {
              cellValue = String.valueOf(
                      FormulaCalculator.getInstance().evaluate(cell.getValueAsString()));
            } catch (ArithmeticException e) {
              cellValue = CELL_ERROR_VALUE;
            }
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
   * It creates a StringBuilder object called tableData and iterates through each row of the table,
   * appending a "|" character to the start of the row to indicate the beginning of the row.
   * For each column in the row, the algorithm gets the corresponding TableCell object from the
   * TableRepository and retrieves the maximum width for that column from the colWidths array.
   * If the cell is not empty, it retrieves the string value of the cell using getValueAsString().
   * If the cell type is CellType.FORMULA, it attempts to evaluate the formula using FormulaCalculator.getInstance().evaluate()
   * and updates the cellValue variable with the result. If an ArithmeticException is caught during evaluation,
   * the cell value is set to "ERROR".
   * The algorithm then uses String.format() to pad the cell value with spaces to match the column width.
   * If the current column is the first column, it uses a left-justified format specifier
   * (%-) to align the text to the left edge of the column. Otherwise, it uses a right-justified format specifier (%)
   * to align the text to the right edge of the column.
   * Finally, the algorithm appends a "|" character to the end of the cell value to indicate the
   * end of the column, and adds the resulting row string to tableData. At the end of the iteration
   * through each row, the algorithm checks if tableData is empty and, if so, returns an error message.
   * Otherwise, it returns the string representation of the table, with each row separated by a newline character.
   * <p>
   * Връща низовото представяне на данните от таблицата, форматирани като таблица с редове и колони.
   * Той създава StringBuilder обект, наречен tableData и преминава през всеки ред на таблицата,
   * добавя "|" символ в началото на реда, за да укажете началото на реда.
   * За всяка колона в реда алгоритъмът получава съответния обект TableCell от
   * TableRepository и извлича максималната ширина за тази колона от масива colWidths.
   * Ако клетката не е празна, тя извлича стойността на низа на клетката с помощта на getValueAsString().
   * Ако типът клетка е CellType.FORMULA, той се опитва да изчисли формулата с помощта на FormulaCalculator.getInstance().evaluate()
   * и актуализира променливата cellValue с резултата. Ако ArithmeticException бъде уловен по време на изчисление,
   * стойността на клетката е зададена на "ГРЕШКА".
   * След това алгоритъмът използва String.format(), за да подпълни стойността на клетката с интервали,
   * за да съответства на ширината на колоната.
   * Ако текущата колона е първата колона, тя използва спецификатор на формат, подравнен вляво
   * (%-), за да подравните текста към левия край на колоната.
   * В противен случай той използва спецификатор на формат, подравнен вдясно (%)
   * за подравняване на текста към десния край на колоната.
   * Накрая алгоритъмът добавя "|" символ до края на стойността на клетката, за да посочи
   * край на колоната и добавя резултантния низ от ред към tableData. В края на итерацията
   * през всеки ред алгоритъмът проверява дали tableData е празен и ако е така, връща съобщение за грешка.
   * В противен случай връща низовото представяне на таблицата, като всеки ред е разделен със знак за нов ред.
   *
   * @param table     the table repository object that holds the cell data
   *                  <p>
   *                  обектът хранилище на таблицата, който съдържа данните от клетката
   * @param numRows   the number of rows in the table
   *                  <p>
   *                  броя на редовете в таблицата
   * @param numCols   the number of columns in the table
   *                  <p>
   *                  броя на колоните в таблицата
   * @param colWidths an array of integers containing the width of each column in characters
   *                  <p>
   *                  масив от цели числа, съдържащ ширината на всяка колона в символи
   * @return a string representation of the table data
   * <p>
   * низово представяне на данните от таблицата
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
            try {
              cellValue = String.valueOf(FormulaCalculator.getInstance().evaluate(cellValue));
            } catch (ArithmeticException e) {
              cellValue = CELL_ERROR_VALUE;
            }
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

    if (tableData.toString().trim().isEmpty()) {
      return NO_DATA_MESSAGE;
    }

    return tableData.toString().trim();
  }
}