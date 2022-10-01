package solutions;

/**
 * Задача 3
 * Изведете стойността на артикулите за почивка на стойност 10000, ако:
 *
 * нощувката е 50% от общата стойност;
 * наемът на плажни артикули е 5% от общата стойност;
 * разходите за ресторант са 30% от общата стойност;
 * допълнителни забавления са 10% от общата стойност;
 * други разходи - 5% от общата стойност.
 */
public class Exercise03 {

  private static final double TOTAL_ACCOMMODATION = 10000;

  public static void main(String[] args) {
    System.out.printf("Night price: %.2f%n", 0.5 * TOTAL_ACCOMMODATION);
    System.out.printf("Beach products: %.2f%n", 0.05 * TOTAL_ACCOMMODATION);
    System.out.printf("Restaurant price: %.2f%n", 0.3 * TOTAL_ACCOMMODATION);
    System.out.printf("Fun activities: %.2f%n", 0.1 * TOTAL_ACCOMMODATION);
    System.out.printf("Other expenses: %.2f", 0.05 * TOTAL_ACCOMMODATION);
  }
}
