/**
 * В края на лятото, магазин за сезонни стоки, има нужда от бърза инвентаризация,
 * за целта е необходимо да се генерират кодове за всички стоки в магазина.
 * Вашата задача е да генерирате всички кодове, които не съдържат в цифрите си цифра,
 * кратна на последната цифра от факултетния ви номер (ако тя е нула, ползвайте пред последната):
 *
 * Баркодовете са в диапазона [1000;9999]
 */
public class ThirdTask {
  private static final Integer LAST_DIGIT = 7;
  public static void main(String[] args) {
    for (int i = 1; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        for (int k = 0; k < 9; k++) {
          for (int l = 0; l < 9; l++) {
            if (i % LAST_DIGIT != 0 && j % LAST_DIGIT != 0
                    && k % LAST_DIGIT != 0 && l % LAST_DIGIT != 0) {
              System.out.printf("%s%s%s%s%n", i, j, k, l);
            }
          }
        }
      }
    }
  }
}
