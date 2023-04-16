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

    if (isInteger(data)) {
      this.value = Integer.parseInt(data);
    } else if (isFractionalNumber(data)) {
      this.value = Double.parseDouble(data);
    } else if (isString(data)) {
      this.value = parseEscapedString(data);
    } else if (isFormula(data)) {
      this.value = data;
    } else {
      throw new IllegalArgumentException("Invalid input value!");
    }
  }

  /**
   * Sets the cell type based on the provided value.
   *
   * @param value the value to set the type for
   * @throws IllegalArgumentException if the provided value is not of a valid type
   */
  private void setType(Object value) {

    if (value instanceof Integer) {
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
      throw new IllegalArgumentException("Invalid input type!");
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

    if (input.startsWith("\"") && input.endsWith("\"")) {
      input = input.substring(1, input.length() - 1);
    }

    input = input.replaceAll("\\\\\"", "\"").replaceAll("\\\\\\\\", "\\\\");

    return input;
  }
}
