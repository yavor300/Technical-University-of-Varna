package bg.tu_varna.sit.b1.f21621577.command.implementation.edit;

import bg.tu_varna.sit.b1.f21621577.command.base.ArgumentCommand;
import bg.tu_varna.sit.b1.f21621577.table.cell.TableCell;
import bg.tu_varna.sit.b1.f21621577.table.repository.TableRepository;

import java.util.List;

/**
 * An implementation of the {@link ArgumentCommand} interface that represents the "edit" command.
 * This command takes three arguments: a row number, a column number, and a new cell value.
 * It updates the cell value in the specified row and column of the current table.
 */
public class EditCommand extends ArgumentCommand {

  private int row;
  private int col;
  private String newValue;

  /**
   * Constructs a new {@code EditCommand} object with the specified array of arguments.
   *
   * @param arguments the arguments for the command
   * @throws IllegalArgumentException if the number of arguments is less than 3, or if the row or column number
   *                                  cannot be parsed as an integer
   */
  public EditCommand(List<String> arguments) {
    super(arguments);
    validateArguments(arguments);
    parseArguments(arguments);
  }

  /**
   * Updates a cell in the currently opened table at the specified row and column with the given value.
   *
   * @return a message indicating the result of the command execution, such as success or failure and the updated cell value
   * @throws IndexOutOfBoundsException if the given row or column is out of bounds of the current table
   * @throws NumberFormatException     if the given cell value cannot be parsed as a double
   */
  @Override
  public String execute() {

    TableRepository repository = TableRepository.getInstance();

    if (!repository.isTableOpened()) {
      return "No table is currently opened.";
    }

    int numRows = repository.getNumRows();
    int numCols = repository.getNumColumns();

    if (row >= numRows || col >= numCols) {
      int newNumRows = Math.max(row + 1, numRows);
      int newNumCols = Math.max(col + 1, numCols);
      repository.scale(newNumRows, newNumCols);
    }

    TableCell oldCell = repository.getCell(row, col);

    TableCell newCell;
    try {
      newCell = new TableCell(newValue);
    } catch (IllegalArgumentException e) {
      return "Invalid cell value: " + e.getMessage();
    }

    repository.setCell(row, col, newCell);
    repository.shrink();

    return "Cell (" + row + ", " + col + ") updated from \"" + oldCell.getValueAsString() + "\" to \"" + newCell.getValueAsString() + "\"";
  }

  /**
   * Validates the arguments for the command.
   *
   * @param arguments the arguments for the command
   * @throws IllegalArgumentException if the number of arguments is less than 3
   */
  private void validateArguments(List<String> arguments) {
    if (arguments.size() < 3) {
      throw new IllegalArgumentException("Not enough arguments. Usage: edit <row> <col> <value>");
    }
  }

  /**
   * Parses the arguments for the command.
   *
   * @param arguments the arguments for the command
   * @throws IllegalArgumentException if the row or column number cannot be parsed as an integer
   */
  private void parseArguments(List<String> arguments) {
    try {
      this.row = Integer.parseInt(arguments.get(0));
      this.col = Integer.parseInt(arguments.get(1));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid row or column number.");
    }
    this.newValue = arguments.get(2);
  }


}
