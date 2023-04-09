package bg.tu_varna.sit.b1.f21621577.table.cell;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a cell in a table.
 */
public class TableCell {

  private final CellType type;
  private final Object value;

  /**
   * Constructs a new TableCell object with the specified data value.
   *
   * @param data the data value for the cell
   * @throws IllegalArgumentException if the data value is not a recognized type
   */
  public TableCell(String data) {
    value = parseValue(data);
    type = getType(value);
  }

  /**
   * Parses the specified data value and returns the corresponding object value.
   *
   * @param data the data value to parse
   * @return the corresponding object value
   * @throws IllegalArgumentException if the data value is not a recognized type
   */
  private Object parseValue(String data) {

    if (isInteger(data)) {
      return Integer.parseInt(data);
    } else if (isFractionalNumber(data)) {
      return Double.parseDouble(data);
    } else if (isString(data)) {
      return data;
    } else if (isFormula(data)) {
      return data;
    }

    throw new IllegalArgumentException("Invalid input value!");
  }

  /**
   * Returns the type of the specified value object.
   *
   * @param value the object value to get the type of
   * @return the type of the object value
   * @throws IllegalArgumentException if the object value is not a recognized type
   */
  private CellType getType(Object value) {

    if (value instanceof Integer) {
      return CellType.INTEGER;
    } else if (value instanceof Double) {
      return CellType.FRACTIONAL;
    } else if (value instanceof String) {
      String stringValue = (String) value;
      if (stringValue.startsWith("=")) {
        return CellType.FORMULA;
      } else {
        return CellType.STRING;
      }
    }

    throw new IllegalArgumentException("Invalid input type!");
  }

  /**
   * Returns true if the specified data value is an integer, false otherwise.
   *
   * @param data the data value to check
   * @return true if the data value is an integer, false otherwise
   */
  private boolean isInteger(String data) {
    Pattern intPattern = Pattern.compile("^[+-]?\\d+$");
    Matcher intMatcher = intPattern.matcher(data);
    return intMatcher.matches();
  }

  /**
   * Returns true if the specified data value is a fractional number, false otherwise.
   *
   * @param data the data value to check
   * @return true if the data value is a fractional number, false otherwise
   */
  private boolean isFractionalNumber(String data) {
    Pattern fractionalPattern = Pattern.compile("^[+-]?\\d+\\.\\d+$");
    Matcher fractionalMatcher = fractionalPattern.matcher(data);
    return fractionalMatcher.matches();
  }

  /**
   * Returns true if the specified data value is a string, false otherwise.
   *
   * @param data the data value to check
   * @return true if the data value is a string, false otherwise
   */
  private boolean isString(String data) {
    Pattern stringPattern = Pattern.compile("^\"?.*\"?$");
    Matcher stringMatcher = stringPattern.matcher(data);
    return stringMatcher.matches();
  }

  /**
   * Returns true if the specified data value is a formula, false otherwise.
   *
   * @param data the data value to check
   * @return true if the data value is a formula, false otherwise
   */
  private boolean isFormula(String data) {
    return data.startsWith("=");
  }
}
