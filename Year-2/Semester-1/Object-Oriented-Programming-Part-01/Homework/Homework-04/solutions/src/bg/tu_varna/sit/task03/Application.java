package bg.tu_varna.sit.task03;

/**
 * Анализирайте задачата и определите грешката (без да пренаписвате целия код),
 * така че положителните числа в редицата да се отпечатат, а отрицателните да се съберат със следващото число в редицата.
 * Ако се получи положително число, да се отпечата, ако е < 0, да не се отпечата.
 * <p>
 * За вход 1:
 * Integer[][] numbers = { {3, -4, 5, 2, 123}, {-1, -1, 3, 4}, {-2, 1}, };
 * Резултат
 * 3 1 2 123
 * 3 4
 * (empty)
 * <p>
 * За вход 2:
 * Integer[][] numbers = { {0, -2, 2, -2, 3} };
 * Резултат
 * 0 0 1
 */

public class Application {
  public static void main(String[] args) {
    Integer[][] numbers = { {3, -4, 5, 2, 123}, {-1, -1, 3, 4}, {-2, 1}, };

    for (int i = 0; i < numbers.length; i++) {
      boolean found = false;
      for (int j = 0; j < numbers[i].length; j++) {
        int currentNum = numbers[i][j];

        if (currentNum >= 0) {
          System.out.printf("%d%s", currentNum, j == numbers[i].length - 1 ? "\n" : " ");
          found = true;
        } else {

          currentNum += numbers[i][j + 1];
          if (currentNum >= 0) {
            System.out.printf("%d%s", currentNum, j == numbers[i].length - 1 ? "\n" : " ");
            found = true;
          }
          j++;
        }
      }

      if (!found) {
        System.out.println("(empty)");
      }
    }
  }
}
