/**
 * Задача 3
 * Напишете програма, която намира частното от: (1) сумата на всички четни
 * числа в диапазона [1;200] и (2) сумата от всички числа, кратни на 7,
 * намиращи се в същия диапазон.
 */
public class Exercise03 {

  public static void main(String[] args) {
    double evenSum = 0;
    double divisibleBySevenSum = 0;

    for (int i = 1; i <= 200; i++) {
      if (i % 2 == 0) {
        evenSum += i;
      }
      if (i % 7 == 0) {
        divisibleBySevenSum += i;
      }
    }

    System.out.printf("%.2f", evenSum / divisibleBySevenSum);
  }
}
