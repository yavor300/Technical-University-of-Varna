package bg.tu_varna.sit.b1.f21621577.constants;

public class Messages {

  /**
   * The message displayed when table data is cleared.
   * <p>
   * Съобщението, което се показва, когато данните от таблицата се изчистят.
   */
  public static final String TABLE_DATA_CLEARED_MESSAGE = "Table data cleared successfully.";

  /**
   * The message displayed when no table is currently opened.
   * <p>
   * Съобщението, което се показва, когато в момента няма отворена таблица.
   */
  public static final String NO_TABLE_OPENED_MESSAGE = "No table is currently opened.";

  /**
   * The message displayed when a table is currently opened.
   * <p>
   * Съобщението, което се показва, когато в момента има отворена таблица.
   */
  public static final String TABLE_ALREADY_OPENED_MESSAGE = "Table is already opened.";

  /**
   * The error message displayed when editing a cell fails.
   * <p>
   * Съобщението за грешка, което се показва при неуспешно редактиране на клетка.
   */
  public static final String ERROR_EDITING_CELL_MESSAGE = "Error editing the cell: %s";

  /**
   * The message displayed when a cell is updated successfully.
   * <p>
   * Съобщението, което се показва при успешно актуализиране на клетка.
   */
  public static final String CELL_UPDATED_MESSAGE = "Cell (%d, %d) updated from \"%s\" to \"%s\"";

  /**
   * The error message displayed when the edit command has too few arguments.
   * <p>
   * Съобщението за грешка се показва, когато командата за редактиране има твърде малко аргументи.
   */
  public static final String EDIT_NOT_ENOUGH_ARGS_ERROR_MESSAGE = "Not enough arguments.";

  /**
   * The error message displayed when the edit command has too many arguments.
   * <p>
   * Съобщението за грешка, което се показва, когато командата за редактиране има твърде много аргументи.
   */
  public static final String EDIT_TOO_MANY_ARGS_ERROR_MESSAGE = "Too many arguments.";

  /**
   * The error message displayed when the edit command is used incorrectly.
   * <p>
   * Съобщението за грешка, което се показва, когато командата за редактиране се използва неправилно.
   */
  public static final String EDIT_USAGE_ERROR_MESSAGE = "Usage: edit <row> <col> <value>";

  /**
   * The error message displayed when an invalid row or column number is used.
   * <p>
   * Съобщението за грешка, което се показва, когато се използва невалиден номер на ред или колона.
   */
  public static final String INVALID_ROW_COL_NUMBER_MESSAGE = "Invalid row or column number.";

  /**
   * The error message displayed when dividing by zero.
   * <p>
   * Съобщението за грешка, което се показва при деление на нула.
   */
  public static final String DIVISION_BY_ZERO_ERROR_MESSAGE = "Division by zero!";

  /**
   * The error message displayed when an invalid operator is used.
   * <p>
   * Съобщението за грешка, което се показва, когато се използва невалиден оператор.
   */
  public static final String INVALID_OPERATOR_MESSAGE = "Invalid operator: %c";

  /**
   * The message displayed when a table is opened successfully.
   * <p>
   * Съобщението, което се показва при успешно отваряне на таблица.
   */
  public static final String TABLE_OPENED_SUCCESSFULLY_MESSAGE = "Table opened successfully.";

  /**
   * The error message displayed when opening a table fails.
   * <p>
   * Съобщението за грешка, показвано при отваряне на таблица, е неуспешно.
   */
  public static final String ERROR_OPENING_TABLE_MESSAGE = "Error opening table: %s";

  /**
   * The error message displayed when saving changes to a table fails.
   * <p>
   * Съобщението за грешка, което се показва при неуспешно записване на промени в таблица.
   */
  public static final String ERROR_SAVING_CHANGES_MESSAGE = "Changes were not saved: %s";

  /**
   * The error message displayed when the saveas command is used without a file argument.
   * <p>
   * Съобщението за грешка, което се показва, когато командата saveas се използва без файлов аргумент.
   */
  public static final String MISSING_FILE_ARGUMENT_ERROR_MESSAGE = "Changes were not saved due to missing command argument! Usage: saveas <file>";

  /**
   * The message displayed when a table is saved successfully.
   * <p>
   * Съобщението, което се показва, когато таблица е запазена успешно.
   */
  public static final String TABLE_SAVED_MESSAGE = "Table saved to file \"%s\"";

  /**
   * A constant string representing the error message for an invalid row index.
   * <p>
   * Константен низ, представляващ съобщението за грешка за невалиден индекс на ред.
   */
  public static final String INVALID_ROW_INDEX_MESSAGE = "Invalid row index: %d";

  /**
   * A constant string representing the error message for an invalid column index.
   * <p>
   * Константен низ, представляващ съобщението за грешка за невалиден индекс на колона.
   */
  public static final String INVALID_COL_INDEX_MESSAGE = "Invalid col index: %d";

  /**
   * A message indicating that an invalid size was provided to a method.
   * <p>
   * Съобщение, указващо, че на метод е предоставен невалиден размер.
   */
  public static final String INVALID_SIZE_ERROR_MESSAGE = "Invalid size: %d x %d";

  /**
   * A constant representing the message indicating the absence of data in the table.
   * <p>
   * Константа, представляваща съобщението, показващо липсата на данни в таблицата.
   */
  public static final String NO_DATA_MESSAGE = "No data is currently present in the table.\nTo add data, close the table using the 'close' command and modify the file,\nor use the 'edit' command to make changes directly.";

  /**
   * Error message indicating that a string has unescaped quotes.
   * <p>
   * Съобщение за грешка, показващо, че низ има необработени кавички.
   */
  public static final String UNESCAPED_QUOTES_ERROR_MESSAGE = " has unescaped quotes.";

  /**
   * Error message indicating that a string has unescaped backslash.
   * <p>
   * Съобщение за грешка, показващо, че низ има необработени наклонени черти.
   */
  public static final String UNESCAPED_BACKSLASH_ERROR_MESSAGE = " has unescaped backslash.";

  /**
   * Error message indicating that the data type is unknown.
   * <p>
   * Съобщение за грешка, показващо, че типът на данните е неизвестен.
   */
  public static final String UNKNOWN_DATA_TYPE_MESSAGE = " is unknown data type.";

  /**
   * Represents the error message for invalid input type.
   * <p>
   * Представлява съобщение за грешка за невалиден тип въвеждане.
   */
  public static final String INVALID_INPUT_TYPE_MESSAGE = "Invalid input type for value: ";

  /**
   * Error message template displayed when a command is not found.
   * <p>
   * Шаблон за съобщение за грешка, показван, когато команда не бъде намерена.
   */
  public static final String COMMAND_NOT_FOUND_MESSAGE = "'%s' is not a command. See 'help'.";
}
