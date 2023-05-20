package bg.tu_varna.sit.b1.f21621577.table.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static bg.tu_varna.sit.b1.f21621577.regex.Patterns.FRACTIONAL_NUMBER_PATTERN;
import static bg.tu_varna.sit.b1.f21621577.regex.Patterns.INTEGER_NUMBER_PATTERN;

/**
 * A utility class that provides methods for working with cell data types.
 * <p>
 * Клас помощник, който предоставя методи за работа с типовете данни на клетки.
 */
public class CellTypeUtil {

  /**
   * Returns true if the specified data value is a fractional number, false otherwise.
   * <p>
   * Връща true, ако зададената стойност на данните е дробно число, false в противен случай.
   *
   * @param data the data value to check
   *             <p>
   *             стойността на данните за проверка
   * @return true if the data value is a fractional number, false otherwise
   * <p>
   * true, ако стойността на данните е дробно число, false в противен случай
   */
  public static boolean isFractionalNumber(String data) {
    Pattern fractionalPattern = Pattern.compile(FRACTIONAL_NUMBER_PATTERN);
    Matcher fractionalMatcher = fractionalPattern.matcher(data);
    return fractionalMatcher.matches();
  }

  /**
   * Returns true if the specified data value is an integer, false otherwise.
   * <p>
   * Връща true, ако зададената стойност на данните е цяло число, false в противен случай.
   *
   * @param data the data value to check
   *             <p>
   *             стойността на данните за проверка
   * @return true if the data value is an integer, false otherwise
   * <p>
   * true, ако стойността на данните е цяло число, false в противен случай
   */
  public static boolean isInteger(String data) {
    Pattern intPattern = Pattern.compile(INTEGER_NUMBER_PATTERN);
    Matcher intMatcher = intPattern.matcher(data);
    return intMatcher.matches();
  }
}
