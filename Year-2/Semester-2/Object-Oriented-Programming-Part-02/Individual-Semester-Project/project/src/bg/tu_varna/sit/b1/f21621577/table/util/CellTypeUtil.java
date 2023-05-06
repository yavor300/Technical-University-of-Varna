package bg.tu_varna.sit.b1.f21621577.table.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    Pattern fractionalPattern = Pattern.compile("^[+-]?\\d+\\.\\d+$");
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
    Pattern intPattern = Pattern.compile("^[+-]?\\d+$");
    Matcher intMatcher = intPattern.matcher(data);
    return intMatcher.matches();
  }
}
