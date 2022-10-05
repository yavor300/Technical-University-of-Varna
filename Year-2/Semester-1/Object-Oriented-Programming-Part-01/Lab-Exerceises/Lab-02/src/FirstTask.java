/**
 * В началото на учебната година училище се нуждае от освежаване на боята в стаите.
 * В училището има 40 стаи, четните са с дължина на стените {10, 5},
 * нечетните с ширина на стените {9, 7}и височина 3 метра.
 * От всяка стая не се боядисват отворите за врата и прозорци,
 * които са 10 квадрата за четните и 12 квадрата за нечетните стай.
 * Колко ще е стойността за боядисване на стайте в училището ако 15лв е боядисването на 1 квадрат стена.
 */
public class FirstTask {

  private static final Integer ROOMS = 40;
  private static final Double PRICE_PER_FEET = 15.00;

  public static void main(String[] args) {
    int totalSquareFeet = 0;
    for (int i = 1; i <= ROOMS; i++) {
      if (i % 2 == 0) {
        totalSquareFeet += 2 * 10 * 3 + 2 * 5 * 3 - 10;
      } else {
        totalSquareFeet += 2 * 9 * 3 + 2 * 7 * 3 - 12;
      }
    }
    System.out.printf("%.2f BGN", totalSquareFeet * PRICE_PER_FEET);
  }
}