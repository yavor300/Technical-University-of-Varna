package solutions;

public class Exercise04 {
  /*
   * Задача 4
   * Изведете всички комбинации на думи, където първите 2 символа да са
   * числа, вторите 2 - букви, петият - символ цифра.
   */
  public static void main(String[] args) {
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        for (int k = 65; k < 122; k++) {
          if (k >= 91 && k <= 96) {
            continue;
          }
          for (int l = 65; l < 122; l++) {
            if (l >= 91 && l <= 96) {
              continue;
            }
            for (int m = 0; m < 9; m++) {
              System.out.printf("%d%d%c%c%d%n", i, j, (char) k, (char) l, m);
            }
          }
        }
      }
    }
  }
}
