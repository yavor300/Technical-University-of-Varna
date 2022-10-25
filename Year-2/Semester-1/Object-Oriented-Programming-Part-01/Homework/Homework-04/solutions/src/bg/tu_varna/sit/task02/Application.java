package bg.tu_varna.sit.task02;

/**
 * Анализирайте задачата и определите грешката (без да пренаписвате целия код),
 * така че да се отпечатат на нов ред всички думи, започващи с "p", но само максимум n символа от тях.
 * <p>
 * Вход 1 през аргументите от команден ред:
 * "phahah put"
 * 3
 * Резултат 1:
 * phah
 * put
 * <p>
 * Вход 2 през аргументите от команден ред:
 * "No match"
 * 5
 * Резултат 2:
 * no
 * <p>
 * Вход 3 през аргументите от команден ред:
 * "preparation"
 * 4
 * Резултат 3:
 * prepa
 * <p>
 * Вход 4 през аргументите от команден ред:
 * "preposition"
 * 0
 * Резултат 4:
 * p
 */
public class Application {

  public static void main(String[] args) {

    String text = args[0];
    int symbolsCountToPrint = Integer.parseInt(args[1]);

    char search = 'p';
    boolean hasMatch = false;

    int wordsCount = text.split("\\s+").length;
    int matchedWords = 0;

    for (int i = 0; i < text.length(); i++) {
      if (matchedWords == wordsCount) {
        break;
      }

      if (text.charAt(i) == search) {
        hasMatch = true;
        int endIndex = symbolsCountToPrint + 1;

        if (i + endIndex > text.length()) {
          endIndex = text.length();
        }

        String matchedString = text.substring(i, endIndex);
        System.out.println(matchedString);
        i += symbolsCountToPrint;
        matchedWords++;
      }
    }

    if (!hasMatch) {
      System.out.println("no");
    }
  }
}
