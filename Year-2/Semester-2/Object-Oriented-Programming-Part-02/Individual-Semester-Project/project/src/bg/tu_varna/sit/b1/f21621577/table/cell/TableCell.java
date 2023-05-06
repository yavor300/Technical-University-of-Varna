package bg.tu_varna.sit.b1.f21621577.table.cell;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bg.tu_varna.sit.b1.f21621577.table.util.CellTypeUtil.isFractionalNumber;
import static bg.tu_varna.sit.b1.f21621577.table.util.CellTypeUtil.isInteger;

/**
 * Represents a cell in a table.
 * <p>
 * Представлява клетка в таблица.
 */
public class TableCell {

  private CellType type;
  private Object value;

  /**
   * Constructs a new TableCell object with the specified data value.
   * <p>
   * Конструира нов обект TableCell със зададената стойност на данните.
   *
   * @param data the data value for the cell
   *             <p>
   *             стойността на данните за клетката
   * @throws IllegalArgumentException if the data value is not a recognized type
   *                                  <p>
   *                                  ако стойността на данните не е разпознат тип
   */
  public TableCell(String data) {
    setValue(data);
    setType(this.value);
  }

  /**
   * Constructs a new TableCell object with an initial value of null and an initial type of EMPTY.
   * <p>
   * Конструира нов обект TableCell с начална стойност null и начален тип EMPTY.
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
   * <p>
   * Връща низовото представяне на стойността на клетката.
   * Ако стойността е празна, връща празен низ.
   * Ако стойността е цяло число, връща целочислената стойност като низ.
   * Ако стойността е дробно число, връща двойната стойност, закръглена до два знака след десетичната запетая, като низ.
   * Ако стойността е низ или формула, връща стойността като низ.
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
   * <p>
   * Връща типа на стойността на клетката.
   *
   * @return the type of the cell value
   * <p>
   * типа на стойността на клетката.
   */
  public CellType getType() {
    return type;
  }

  /**
   * Sets the value of the cell based on the provided data string. The data string is parsed
   * and converted to the appropriate data type based on the cell's type.
   * <p>
   * Задава стойността на клетката въз основа на предоставения низ от данни. Низът с данни се анализира
   * и се преобразува в подходящия тип данни въз основа на типа на клетката.
   *
   * @param data the data string to set as the cell's value
   *             <p>
   *             низът от данни, който да зададете като стойност на клетката
   * @throws IllegalArgumentException if the data string is invalid and cannot be parsed into the appropriate data type
   *                                  <p>
   *                                  ако низът от данни е невалиден и не може да бъде превърнат в подходящия тип данни
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
        throw new IllegalArgumentException(data + " has unescaped quotes.");
      }
      if (!areAllBackslashesEscaped(data)) {
        throw new IllegalArgumentException(data + " has unescaped backslash.");
      }
      this.value = parseEscapedString(data);
    } else if (isFormula(data)) {
      this.value = data;
    } else {
      throw new IllegalArgumentException(data + " is unknown data type.");
    }
  }

  /**
   * Sets the cell type based on the provided value.
   * <p>
   * Задава типа клетка въз основа на предоставената стойност.
   *
   * @param value the value to set the type for
   *              <p>
   *              стойността, за която да зададете типа
   * @throws IllegalArgumentException if the provided value is not of a valid type
   *                                  <p>
   *                                  ако предоставената стойност не е от валиден тип
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
   * <p>
   * Връща true, ако зададената стойност на данните е низ, false в противен случай.
   *
   * @param data the data value to check
   *             <p>
   *             стойността на данните за проверка
   * @return true if the data value is a string, false otherwise
   * <p>
   * true, ако стойността на данните е низ, false в противен случай
   */
  private boolean isString(String data) {

    Pattern stringPattern = Pattern.compile("^\".*\"$");
    Matcher stringMatcher = stringPattern.matcher(data);

    return stringMatcher.matches();
  }

  /**
   * Returns true if the specified data value is a formula, false otherwise.
   * <p>
   * Връща true, ако указаната стойност на данните е формула, false в противен случай.
   *
   * @param data the data value to check
   *             <p>
   *             стойността на данните за проверка
   * @return true if the data value is a formula, false otherwise
   * <p>
   * true, ако стойността на данните е формула, false в противен случай
   */
  private boolean isFormula(String data) {
    return data.startsWith("=");
  }

  /**
   * Parses an input string by removing the first and last occurrence of quotes and replacing any escaped characters.
   * <p>
   * Анализира въведен низ, като премахва първото и последното появяване на кавички и заменя всички специални знаци.
   *
   * @param input the input string to be parsed
   *              <p>
   *              входния низ за анализиране
   * @return the parsed string with escaped characters replaced
   * <p>
   * анализираният низ с обработени специални знаци
   */
  private String parseEscapedString(String input) {

    input = input.replaceAll("\\\\\"", "\"").replaceAll("\\\\\\\\", "\\\\");

    return input;
  }


  /**
   * Checks whether all quotes in a given string are escaped with a backslash.
   * <p>
   * Проверява дали всички кавички в даден низ са обработени с обратна наклонена черта.
   *
   * @param input the input string to check
   *              <p>
   *              входния низ за проверка
   * @return true if all quotes in the input string are escaped, false otherwise
   * <p>
   * true, ако всички кавички във входния низ са обработени, false в противен случай
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
   * <p>
   * Проверява дали всички обратни наклонени черти в низ са обработени.
   *
   * @param input the input string to check
   *              <p>
   *              входния низ за проверка
   * @return true if all backslashes are escaped, false otherwise
   * <p>
   * true, ако всички обратни наклонени черти са обработени, false в противен случай
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
   * <p>
   * Премахва кавички около дадения низ, ако съществуват.
   *
   * @param input the input string to remove quotes from
   *              <p>
   *              входния низ, от който да премахнете кавички
   * @return the input string with quotes removed if they existed, otherwise the input string unchanged
   * <p>
   * входният низ с премахнати кавички, ако съществуват, в противен случай входният низ остава непроменен
   */
  private String removeQuotes(String input) {

    if (isStringEnclosedInQuotes(input)) {
      return input.substring(1, input.length() - 1);
    }

    return input;
  }

  /**
   * Checks if a given string is enclosed in double quotes.
   * <p>
   * Проверява дали даден низ е ограден в двойни кавички.
   *
   * @param input the input string to check
   *              <p>
   *              входния низ за проверка
   * @return true if the input string starts and ends with double quotes, false otherwise
   * <p>
   * true, ако входният низ започва и завършва с двойни кавички, false в противен случай
   */
  private boolean isStringEnclosedInQuotes(String input) {

    return input.startsWith("\"") && input.endsWith("\"");
  }
}
