import java.util.Scanner;

/**
 * Задача 1
 * Напишете програма, която да проверява дали три реални числа са страни
 * на триъгълник и ако да, дали той е правоъгълен.
 */
public class Exercise01 {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    int a = Integer.parseInt(scanner.nextLine());
    int b = Integer.parseInt(scanner.nextLine());
    int c = Integer.parseInt(scanner.nextLine());

    if (isTriangle(a, b, c)) {
      System.out.println("Въведените стойности са валидни за страни на триъгълник.");
      if (isRightTriangle(a, b, c)) {
        System.out.println("Триъгълникът е правоъгълен.");
      }
    }
  }

  private static boolean isTriangle(int a, int b, int c) {
    return a + b > c || a + c > b || b + c > a;
  }

  private static boolean isRightTriangle(int a, int b, int c) {
    return Math.pow(a, 2) + Math.pow(b, 2) == Math.pow(c, 2) ||
            Math.pow(a, 2) + Math.pow(c, 2) == Math.pow(b, 2) ||
            Math.pow(b, 2) + Math.pow(c, 2) == Math.pow(a, 2);
  }
}
