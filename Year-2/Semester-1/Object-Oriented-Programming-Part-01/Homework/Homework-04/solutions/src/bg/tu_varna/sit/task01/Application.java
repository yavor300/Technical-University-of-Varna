package bg.tu_varna.sit.task01;

/**
 * Анализирайте задачата и определите грешката (без да пренаписвате целия код),
 * така че да се изведе броят на срещнатите символи 'а'
 * <p>
 * Вход през аргументите от команден ред
 * a 8 d p 0 o h a 3 o а u f a о
 * <p>
 * Очакван резултат:
 * 4
 */

public class Application {

  private static final int ENGLISH_LOWERCASE_A_CODE = 97;
  private static final int BULGARIAN_LOWERCASE_A_CODE = 1072;

  public static void main(String[] args) {

    int countA = 0;
    for (int i = 0; i < args.length; i++) {
      if ((int) args[i].charAt(0) == ENGLISH_LOWERCASE_A_CODE ||
              (int) args[i].charAt(0) == BULGARIAN_LOWERCASE_A_CODE) {
        countA++;
      }
    }
    System.out.println("The symbol a has " + countA + " repetitions in the array");
  }
}
