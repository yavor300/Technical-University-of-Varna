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
   * Executes the command.
   *
   * @return a message indicating the result of the command execution
   */
  @Override
  public String execute() {

    TableRepository table = TableRepository.getInstance();

    TableCell oldCell = table.getCell(row, col);
    if (oldCell == null) {
      oldCell = new TableCell("empty");
    }

    TableCell newCell;
    try {
      newCell = new TableCell(newValue);
    } catch (IllegalArgumentException e) {
      return "Invalid cell value: " + e.getMessage();
    }

    table.setCell(row, col, newCell);

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