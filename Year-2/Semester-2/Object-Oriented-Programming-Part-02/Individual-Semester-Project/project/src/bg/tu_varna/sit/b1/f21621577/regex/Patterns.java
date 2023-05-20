package bg.tu_varna.sit.b1.f21621577.regex;

import static bg.tu_varna.sit.b1.f21621577.config.Config.CELLS_INPUT_SEPARATOR;
import static bg.tu_varna.sit.b1.f21621577.config.Config.CELLS_OUTPUT_SEPARATOR;

public class Patterns {

  /**
   * The separator used for inputting cell values.
   * The regular expression, is a negative lookbehind assertion that matches a CELLS_INPUT_SEPARATOR
   * (",") character but only if it is not preceded by a backslash ("\").
   * <p>
   * Разделителят, използван за въвеждане на стойности на клетките.
   * Регулярният израз е отрицателно твърдение за ретроспектива, което съвпада със CELLS_INPUT_SEPARATOR
   * (","), но само ако не е предшестван от обратна наклонена черта ("\").
   */
  public static final String CELLS_INPUT_SEPARATOR_PATTERN = "(?<!\\\\)" + CELLS_INPUT_SEPARATOR;

  /**
   * The regular expression pattern used to match cell references.
   * The pattern matches cell references in the format [rR](\d+)[cC](\d+),
   * where 'r' or 'R' represents the row number and 'c' or 'C' represents the column number.
   * The row and column numbers are represented by one or more digits.
   * <p>
   * Образецът на регулярен израз, използван за съпоставяне на препратки към клетки.
   * Моделът съответства на препратките към клетки във формат [rR](\d+)[cC](\d+),
   * където „r“ или „R“ представлява номера на реда, а „c“ или „C“ представлява номера на колоната.
   * Номерата на редовете и колоните са представени с една или повече цифри.
   */
  public static final String CELL_REFERENCE_PATTERN = "[rR](\\d+)[cC](\\d+)";

  /**
   * Regular expression pattern for matching row or column indicators.
   * The pattern matches either 'R' or 'C' characters.
   * Example usage:
   * - R or C: to indicate a row or column respectively.
   * <p>
   * Образец на регулярен израз за съпоставяне на индикатори за ред или колона.
   * Моделът съвпада или със знаци 'R', или 'C'.
   * Примерна употреба:
   * - R или C: за обозначаване съответно на ред или колона.
   */
  public static final String ROW_OR_COLUMN_PATTERN = "R|C";

  /**
   * The regular expression pattern for matching integer or fractional numbers.
   * This pattern matches numeric values that may include an optional decimal part.
   * It can match integers (e.g., "42") as well as decimal numbers (e.g., "3.14").
   * The decimal part is optional, allowing for whole numbers to be matched as well.
   * The pattern breakdown:
   * - "^[+-]?" checks whether the number contains a sign
   * - "\\d+" matches one or more digits before the decimal point.
   * - "(\\.\\d+)?" matches an optional decimal point followed by one or more digits.
   * <p>
   * Образецът на регулярен израз за съвпадение на цели или дробни числа.
   * Този модел съответства на числови стойности, които може да включват незадължителна десетична част.
   * Може да съпоставя цели числа (напр. "42"), както и десетични числа (напр. "3,14").
   * Десетичната част не е задължителна, което позволява съпоставяне и на цели числа.
   * Разбивка на модела:
   * - "^[+-]?" проверява дали числото съдържа знак
   * - "\\d+" съвпада с една или повече цифри преди десетичната запетая.
   * - "(\\.\\d+)?" съвпада с незадължителна десетична точка, последвана от една или повече цифри.
   */
  public static final String INTEGER_OR_FRACTIONAL_NUMBER_PATTERN = "^[+-]?\\d+(\\.\\d+)?$";

  /**
   * Represents the regular expression pattern for matching a fractional number.
   * The pattern matches a string that starts with an optional plus or minus sign,
   * followed by one or more digits, a decimal point, and one or more digits after the decimal point.
   * <p>
   * Представлява шаблона на регулярен израз за съпоставяне на дробно число.
   * Моделът съответства на низ, който започва с незадължителен знак плюс или минус,
   * последвано от една или повече цифри, десетична запетая и една или повече цифри след десетичната запетая.
   */
  public static final String FRACTIONAL_NUMBER_PATTERN = "^[+-]?\\d+\\.\\d+$";

  /**
   * Represents the regular expression pattern for matching an integer number.
   * The pattern matches a string that starts with an optional plus or minus sign,
   * followed by one or more digits.
   * <p>
   * Представлява модела на регулярен израз за съвпадение на цяло число.
   * Моделът съответства на низ, който започва с незадължителен знак плюс или минус,
   * последвано от една или повече цифри.
   */
  public static final String INTEGER_NUMBER_PATTERN = "^[+-]?\\d+$";

  /**
   * The set of allowed mathematical operators.
   * This string contains the operators that are allowed for mathematical operations.
   * It includes addition (+), subtraction (-), multiplication (*), division (/), and exponentiation (^).
   * <p>
   * Набор от разрешени математически оператори.
   * Този низ съдържа операторите, които са разрешени за математически операции.
   * Включва събиране (+), изваждане (-), умножение (*), деление (/) и степенуване (^).
   */
  public static final String MATH_ALLOWED_OPERATORS = "+-*/^";

  /**
   * Represents the regular expression pattern for matching a string enclosed in double quotes.
   * <p>
   * Представлява шаблона на регулярен израз за съвпадение на низ, ограден в двойни кавички.
   */
  public static final String STRING_IN_QUOTES_PATTERN = "^\".*\"$";

  /**
   * Represents the escaped double quote character: \".
   * <p>
   * Представлява обработеният символ двойни кавички: \".
   */
  public static final String ESCAPED_DOUBLE_QUOTE = "\\\\\"";

  /**
   * Represents the escaped backslash character: \\.
   * <p>
   * Представлява обработеният символ наклонена черта: \\".
   */
  public static final String ESCAPED_BACKSLASH = "\\\\\\\\";

  /**
   * Represents the escaped comma character: \,
   * <p>
   * Представлява обработеният символ запетая: \,
   */
  public static final String ESCAPED_COMMA = "\\\\,";

  /**
   * Represents the non escaped double quote character: ".
   * <p>
   * Представлява необработеният символ двойни кавички: ".
   */
  public static final String NON_ESCAPED_DOUBLE_QUOTE = "\"";

  /**
   * Represents the non escaped backslash character: \.
   * <p>
   * Представлява необработеният символ наклонена черта: \".
   */
  public static final String NON_ESCAPED_BACKSLASH = "\\\\";

  /**
   * The regex pattern can be used in Java's split() method to split
   * a string into words while preserving quoted sections.
   * <p>
   * Моделът на regex може да се използва в метода split() на Java за разделяне
   * низ в думи, като се запазват секциите оградени с кавички.
   */
  public static final String DO_NOT_SPLIT_IF_ENCLOSED_IN_QUOTES_PATTERN = " (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

  /**
   * Represents the non escaped comma character: ,
   * <p>
   * Представлява необработеният символ запетая: ,
   */
  public static final String NON_ESCAPED_COMMA = ",";

  /**
   * Represents the string value for escaping a double quote using a backslash.
   * <p>
   * Представлява стойността на низа за обработване на двойни кавички с помощта на обратна наклонена черта.
   */
  public static final String BACKSLASH_ESCAPING_QUOTE = "\\\\\"";

  /**
   * Represents the string value for escaping a comma using a backslash.
   * <p>
   * Представлява стойността на низа за обработване на запетая с помощта на обратна наклонена черта.
   */
  public static final String BACKSLASH_ESCAPING_COMMA = "\\\\,";

  /**
   * Represents the string constant for a single backslash character.
   * <p>
   * Представлява низовата константа за единичен обратен знак наклонена черта.
   */
  public static final String SINGLE_BACKSLASH = "\\";

  /**
   * Represents the string constant for a double backslash character.
   * <p>
   * Представлява низовата константа за двоен обратен знак наклонена черта.
   */
  public static final String DOUBLE_BACKSLASH = SINGLE_BACKSLASH + SINGLE_BACKSLASH;

  /**
   * Represents the string constant for an unescaped double quote character.
   * <p>
   * Представлява константата на низа за необработен символ двойна кавичка.
   */
  public static final String NON_UNESCAPED_QUOTE = "\"";

  /**
   * Represents the string constant for an escaped double quote character.
   * <p>
   * Представлява константата на низа за обработен символ двойна кавичка.
   */
  public static final String ESCAPED_QUOTE = "\\\"";

  /**
   * The escaped separator used for outputting cell values.
   * <p>
   * Обработеният разделител, използван за извеждане на стойностите на клетката.
   */
  public static final String ESCAPED_CELLS_OUTPUT_SEPARATOR = SINGLE_BACKSLASH + CELLS_OUTPUT_SEPARATOR;

}
