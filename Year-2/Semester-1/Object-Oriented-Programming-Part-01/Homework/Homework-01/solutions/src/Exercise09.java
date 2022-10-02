/**
 * Задача 9
 * Създайте програма, която да намира трите най-големи четни елементи
 * от предварително създаден масив, съдържащ 10 реални числа.
 */
public class Exercise09 {

  public static void main(String[] args) {
    double[] numbers = {6.43, 5.54, 8.0, 2.22, 3.45, 8.90, 2.33, 5.22, 9.12, 7.24};
    double[] sorted = sortNumbersDesc(numbers);

    int numbersToPrint = 0;
    for (double number : sorted) {
      if (isEven(number)) {
        numbersToPrint++;
        System.out.println(number);
      }
      if (numbersToPrint == 3) {
        break;
      }
    }
  }

  private static double[] sortNumbersDesc(double[] numbers) {
    double[] sorted = new double[numbers.length];
    System.arraycopy(numbers, 0, sorted, 0, numbers.length);

    for (int i = 0; i < sorted.length - 1; i++) {
      boolean swapped = false;
      for (int j = 0; j < sorted.length - 1 - i; j++) {
        double first = sorted[j];
        double second = sorted[j + 1];
        if (first < second) {
          sorted[j] = sorted[j + 1];
          sorted[j + 1] = first;
          swapped = true;
        }
      }
      if (!swapped) break;
    }

    return sorted;
  }

  private static boolean isEven(double number) {
    int numberLength = String.valueOf(number).length();
    char[] digits = String.valueOf(number).toCharArray();

    boolean dotSeen = false;
    for (int i = numberLength - 1; i >= 0; i--) {
      if (digits[i] == '0' && !dotSeen) {
        continue;
      }
      if (digits[i] == '.') {
        dotSeen = true;
        continue;
      }
      return (digits[i] - '0') % 2 == 0;
    }
    return false;
  }
}
