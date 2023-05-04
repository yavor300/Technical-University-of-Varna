package bg.tu_varna.sit.b1.f21621577.table.cell;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bg.tu_varna.sit.b1.f21621577.table.util.CellTypeUtil.isFractionalNumber;
import static bg.tu_varna.sit.b1.f21621577.table.util.CellTypeUtil.isInteger;

/**
 * Represents a cell in a table.
 */
public class TableCell {

  private CellType type;
  private Object value;

  /**
   * Constructs a new TableCell object with the specified data value.
   *
   * @param data the data value for the cell
   * @throws IllegalArgumentException if the data value is not a recognized type
   */
  public TableCell(String data) {
    setValue(data);
    setType(this.value);
  }

  /**
   * Constructs a new TableCell object with an initial value of null and an initial type of EMPTY.
   */
  public TableCell() {
    this.value = null;
    this.type = CellType.EMPTY;
  }

  /**
   * Returns the string representation of the cell's value.
   * If the value is null, returns an empty string.
   * If the value is an integer, returns the integer value as a string.
   * If the value is a fractional number, returns the double value rounded to two decimal places as a string.
   * If the value is a string or a formula, returns the value as a string.
   *
   * @return the string representation of the cell's value.
   */
  public String getValueAsString() {

    if (value == null) {
      return "";
    }

    if (type == CellType.INTEGER) {
      return String.format("%d", ((Number) value).intValue());
    }

    if (type == CellType.FRACTIONAL) {
      return String.format("%.2f", ((Number) value).doubleValue());
    }

    return value.toString();
  }

  /**
   * Returns the type of the cell value.
   *
   * @return the type of the cell value
   */
  public CellType getType() {
    return type;
  }

  /**
   * Sets the value of the cell based on the provided data string. The data string is parsed
   * and converted to the appropriate data type based on the cell's type.
   *
   * @param data the data string to set as the cell's value
   * @throws IllegalArgumentException if the data string is invalid and cannot be parsed
   *                                  into the appropriate data type
   */
  private void setValue(String data) {

    if (data.trim().isEmpty()) {
      this.value = null;
    } else if (isInteger(data)) {
      this.value = Integer.parseInt(data);
    } else if (isFractionalNumber(data)) {
      this.value = Double.parseDouble(data);
    } else if (isString(data)) {
      data = removeQuotes(data).trim();
      if (data.isEmpty()) {
        this.value = null;
        return;
      }
      if (!areAllQuotesEscaped(data)) {
        throw new IllegalArgumentException("Unescaped quotes: " + data);
      }
      if (!areAllBackslashesEscaped(data)) {
        throw new IllegalArgumentException("Unescaped backslash: " + data);
      }
      this.value = parseEscapedString(data);
    } else if (isFormula(data)) {
      this.value = data;
    } else {
      if (!isStringEnclosedInQuotes(data)) {
        throw new IllegalArgumentException("Missing quote: " + data);
      } else {
        throw new IllegalArgumentException("Invalid input value: " + data);
      }
    }
  }

  /**
   * Sets the cell type based on the provided value.
   *
   * @param value the value to set the type for
   * @throws IllegalArgumentException if the provided value is not of a valid type
   */
  private void setType(Object value) {

    if (value == null) {
      this.type = CellType.EMPTY;
    } else if (value instanceof Integer) {
      this.type = CellType.INTEGER;
    } else if (value instanceof Double) {
      this.type = CellType.FRACTIONAL;
    } else if (value instanceof String) {
      String stringValue = (String) value;
      if (stringValue.startsWith("=")) {
        this.type = CellType.FORMULA;
      } else {
        this.type = CellType.STRING;
      }
    } else {
      throw new IllegalArgumentException("Invalid input type for value: " + value);
    }
  }

  /**
   * Returns true if the specified data value is a string, false otherwise.
   *
   * @param data the data value to check
   * @return true if the data value is a string, false otherwise
   */
  private boolean isString(String data) {
    Pattern stringPattern = Pattern.compile("^\".*\"$");
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

  /**
   * Parses an input string by removing the first and last occurrence of quotes and replacing any escaped characters.
   *
   * @param input the input string to be parsed
   * @return the parsed string with escaped characters replaced
   */
  private String parseEscapedString(String input) {

    input = input.replaceAll("\\\\\"", "\"").replaceAll("\\\\\\\\", "\\\\");

    return input;
  }


  /**
   * Checks whether all quotes in a given string are escaped with a backslash.
   *
   * @param input the input string to check
   * @return true if all quotes in the input string are escaped, false otherwise
   */
  private boolean areAllQuotesEscaped(String input) {

    for (int i = 0; i < input.length(); i++) {

      char c = input.charAt(i);

      if (c == '\"' && i == 0) {
        return false;
      }

      if (c == '\"' && input.charAt(i - 1) != '\\') {
        return false;
      }
    }

    return true;
  }

  /**
   * Checks whether all backslashes in a string are escaped.
   *
   * @param input the input string to check
   * @return true if all backslashes are escaped, false otherwise
   */
  private boolean areAllBackslashesEscaped(String input) {

    input = input.replaceAll("\\\\\"", "");

    for (int i = 0; i < input.length(); i++) {

      char c = input.charAt(i);

      if (c == '\\') {
        if (i == input.length() - 1) {
          return false;
        } else if (input.charAt(i + 1) != '\\') {
          return false;
        }
        i++;
      }
    }

    return true;
  }

  /**
   * Removes quotes surrounding the given string if they exist.
   *
   * @param input the input string to remove quotes from
   * @return the input string with quotes removed if they existed, otherwise the input string unchanged
   */
  private String removeQuotes(String input) {

    if (isStringEnclosedInQuotes(input)) {
      return input.substring(1, input.length() - 1);
    }

    return input;
  }

  /**
   * Checks if a given string is enclosed in double quotes.
   *
   * @param input the input string to check
   * @return true if the input string starts and ends with double quotes, false otherwise
   */
  private boolean isStringEnclosedInQuotes(String input) {

    return input.startsWith("\"") && input.endsWith("\"");
  }
}
