package bg.tu_varna.sit.b1.f21621577.config;

/**
 * Config class holding all configurable properties for the application.
 */
public class Config {

  public static final int ROWS = 100;

  public static final int COLS = 100;

  public static final String CELLS_INPUT_SEPARATOR = ",";

  public static final String CELLS_OUTPUT_SEPARATOR = ",";

  public static final String DEFAULT_RESOURCES_DIRECTORY = "src/bg/tu_varna/sit/b1/f21621577/resources/";

  public static final String DEFAULT_TABLE_FILENAME = "data.csv";

  public static final String TABLE_DATA_CLEARED_MESSAGE = "Table data cleared successfully.";

  public static final String NO_TABLE_OPENED_MESSAGE = "No table is currently opened.";

  public static final String ERROR_EDITING_CELL_MESSAGE = "Error editing the cell: %s";

  public static final String CELL_UPDATED_MESSAGE = "Cell (%d, %d) updated from \"%s\" to \"%s\"";

  public static final String EDIT_NOT_ENOUGH_ARGS_ERROR_MESSAGE = "Not enough arguments.";

  public static final String EDIT_TOO_MANY_ARGS_ERROR_MESSAGE = "Too many arguments.";

  public static final String EDIT_USAGE_ERROR_MESSAGE = "Usage: edit <row> <col> <value>\"";

  public static final String INVALID_ROW_COL_NUMBER = "Invalid row or column number.";

  public static final String DIVISION_BY_ZERO_ERROR = "Division by zero!";

  public static final String INVALID_OPERATOR_MESSAGE = "Invalid operator: %c";

}
