package solutions;

/**
 * Задача 2
 * Създайте масив {5, 6, 7, 9} с количеството на касети в дрогерия и масив
 * {2.5, 3.6, 8.9, 7.5} с цените на една 1 касета.
 * Изведете общата сума на всички касети.
 */
public class Exercise02 {

  public static void main(String[] args) {

    int[] count = {5, 6, 7, 9};
    double[] prices = {2.5, 3.6, 8.9, 7.5};
    double total = 0;

    for (int i = 0; i < 4; i++) {
      total += count[i] * prices[i];
    }

    System.out.printf("Total: %.2f", total);
  }
}
