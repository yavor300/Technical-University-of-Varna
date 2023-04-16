package bg.tu_varna.sit.b1.f21621577.table.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CellTypeUtil {

  /**
   * Returns true if the specified data value is a fractional number, false otherwise.
   *
   * @param data the data value to check
   * @return true if the data value is a fractional number, false otherwise
   */
  public static boolean isFractionalNumber(String data) {
    Pattern fractionalPattern = Pattern.compile("^[+-]?\\d+\\.\\d+$");
    Matcher fractionalMatcher = fractionalPattern.matcher(data);
    return fractionalMatcher.matches();
  }

  /**
   * Returns true if the specified data value is an integer, false otherwise.
   *
   * @param data the data value to check
   * @return true if the data value is an integer, false otherwise
   */
  public static boolean isInteger(String data) {
    Pattern intPattern = Pattern.compile("^[+-]?\\d+$");
    Matcher intMatcher = intPattern.matcher(data);
    return intMatcher.matches();
  }
}
