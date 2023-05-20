package bg.tu_varna.sit.b1.f21621577.command.implementation.edit;

import bg.tu_varna.sit.b1.f21621577.command.base.ArgumentCommand;
import bg.tu_varna.sit.b1.f21621577.table.cell.TableCell;
import bg.tu_varna.sit.b1.f21621577.table.repository.TableRepository;

import java.util.List;

import static bg.tu_varna.sit.b1.f21621577.config.Config.CELL_UPDATED_MESSAGE;
import static bg.tu_varna.sit.b1.f21621577.config.Config.EDIT_NOT_ENOUGH_ARGS_ERROR_MESSAGE;
import static bg.tu_varna.sit.b1.f21621577.config.Config.EDIT_TOO_MANY_ARGS_ERROR_MESSAGE;
import static bg.tu_varna.sit.b1.f21621577.config.Config.EDIT_USAGE_ERROR_MESSAGE;
import static bg.tu_varna.sit.b1.f21621577.config.Config.ERROR_EDITING_CELL_MESSAGE;
import static bg.tu_varna.sit.b1.f21621577.config.Config.INVALID_ROW_COL_NUMBER;
import static bg.tu_varna.sit.b1.f21621577.config.Config.NO_TABLE_OPENED_MESSAGE;

/**
 * An implementation of the {@link ArgumentCommand} interface that represents the "edit" command.
 * This command takes three arguments: a row number, a column number, and a new cell value.
 * It updates the cell value in the specified row and column of the current table.
 * <p>
 * Реализация на интерфейса {@link ArgumentCommand}, който представлява командата „редактиране“.
 * Тази команда приема три аргумента: номер на ред, номер на колона и нова стойност на клетка.
 * Той актуализира стойността на клетката в посочения ред и колона на текущата таблица.
 */
public class EditCommand extends ArgumentCommand {

  /**
   * The number of allowed arguments for the edit command.
   * <p>
   * Броят разрешени аргументи за командата за редактиране.
   */
  private static final int ALLOWED_ARGUMENTS_COUNT = 3;

  /**
   * The row index of the cell to be edited.
   * <p>
   * Индексът на реда на клетката, която ще се редактира.
   */
  private int row;

  /**
   * The column index of the cell to be edited.
   * <p>
   * Индексът на колоната на клетката, която ще се редактира.
   */
  private int col;

  /**
   * The new value to be set for the cell.
   * <p>
   * Новата стойност, която трябва да бъде зададена за клетката.
   */
  private String newValue;

  /**
   * Constructs a new {@code EditCommand} object with the specified array of arguments.
   * <p>
   * Конструира нов обект {@code EditCommand} с посочения масив от аргументи.
   *
   * @param arguments the arguments for the command
   *                  <p>
   *                  аргументите за командата
   * @throws IllegalArgumentException if the number of arguments is less than 3, or if the row or column number
   *                                  cannot be parsed as an integer
   *                                  <p>
   *                                  ако броят на аргументите е по-малък от 3 или ако номерът на реда или колоната
   *                                  не може да се представи като цяло число
   * @throws NumberFormatException    if the given cell value cannot be parsed as a double
   *                                  <p>
   *                                  ако дадената стойност на клетката не може да бъде представена като число
   */
  EditCommand(List<String> arguments) {
    super(arguments);
    validateArguments();
    parseArguments();
  }

  /**
   * Updates a cell in the currently opened table at the specified row and column with the given value.
   * In addition it scales and shrinks the table if necessary.
   * <p>
   * Актуализира клетка в текущо отворената таблица в посочения ред и колона с дадената стойност.
   * В допълнение, командата мащабира и свива таблицата, ако е необходимо.
   *
   * @return a message indicating the result of the command execution, such as success or failure and the updated cell value
   * <p>
   * съобщение, указващо резултата от изпълнението на командата, като успех или неуспех и актуализираната стойност на клетката
   */
  @Override
  public String execute() {

    TableRepository repository = TableRepository.getInstance();

    if (!repository.isTableOpened()) {
      return NO_TABLE_OPENED_MESSAGE;
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
      return String.format(ERROR_EDITING_CELL_MESSAGE, e.getMessage());
    }

    repository.setCell(row, col, newCell);
    repository.shrink();

    return String.format(CELL_UPDATED_MESSAGE, row + 1, col + 1, oldCell.getValueAsString(), newCell.getValueAsString());
  }

  /**
   * Validates the arguments passed to the edit command.
   * <p>
   * Потвърждава аргументите, предадени на командата за редактиране.
   *
   * @throws IllegalArgumentException if there are not enough arguments or too many arguments for the edit command
   *                                  <p>
   *                                  ако няма достатъчно аргументи или твърде много аргументи за командата за редактиране
   */
  private void validateArguments() {
    if (getArguments().size() < ALLOWED_ARGUMENTS_COUNT) {
      throw new IllegalArgumentException(EDIT_NOT_ENOUGH_ARGS_ERROR_MESSAGE + " " + EDIT_USAGE_ERROR_MESSAGE);
    }
    if (getArguments().size() > ALLOWED_ARGUMENTS_COUNT) {
      throw new IllegalArgumentException(EDIT_TOO_MANY_ARGS_ERROR_MESSAGE + " " + EDIT_USAGE_ERROR_MESSAGE);
    }
  }

  /**
   * Parses the arguments for the command.
   * <p>
   * Превръща аргументите за командата към целочислени стойности.
   *
   * @throws IllegalArgumentException if the row or column number cannot be parsed as an integer
   *                                  <p>
   *                                  ако номерът на реда или колоната не може да бъде превърнат в цяло число
   */
  private void parseArguments() {

    try {
      this.row = Integer.parseInt(getArguments().get(0)) - 1;
      this.col = Integer.parseInt(getArguments().get(1)) - 1;
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(INVALID_ROW_COL_NUMBER);
    }

    this.newValue = getArguments().get(2);
  }
}
