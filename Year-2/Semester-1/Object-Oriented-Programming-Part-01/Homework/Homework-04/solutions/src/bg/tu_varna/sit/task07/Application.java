package bg.tu_varna.sit.task07;

/**
 * Анализирайте задачата и определите грешката (без да пренаписвате целия код),
 * така че да се получи изместване на битовете на числото на ляво или на дясно със 6 бита.
 * Ако бит, вдигнат в 1, излезе от значещата част на числото, той трябва да се прехвърли от другата страна:
 * <p>
 * Вход 1:
 * 32
 * "right right"
 * <p>
 * Очакван резултат:
 * 2
 * <p>
 * Вход 2:
 * 63
 * "left"
 * <p>
 * Очакван резултат:
 * -49
 * <p>
 * Вход 3:
 * 59;
 * "left left left left"
 * <p>
 * Очакван резултат:
 * 59
 * <p>
 * Вход 4:
 * 45
 * "left right left"
 * <p>
 * Очакван резултат:
 * 75
 */

public class Application {
  public static void main(String[] args) {
    byte number = Byte.parseByte(args[0]);
    BitShifter bitShifter = new BitShifter(args[1].split(" "));
    System.out.println(bitShifter.shift(number));
  }
}
