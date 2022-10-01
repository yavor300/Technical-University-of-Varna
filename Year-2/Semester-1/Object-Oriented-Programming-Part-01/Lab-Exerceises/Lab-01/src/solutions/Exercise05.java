package solutions;

/**
 * Задача 5
 * Напишете програма, която да изведе простите числа в интервала от 1 до 300.
 */
public class Exercise05 {

  public static void main(String[] args) {
    for (int i = 1; i <= 300; i++) {
      if (isPrime(i)) {
        System.out.println(i);
      }
    }
  }

  private static boolean isPrime(int number) {
    if (number <= 1) {
      return false;
    }
    for (int i = 2; i <= number / 2; i++) {
      if (number % i == 0)
        return false;
    }
    return true;
  }
}
