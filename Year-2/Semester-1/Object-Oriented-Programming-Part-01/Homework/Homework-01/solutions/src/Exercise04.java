/**
 * Задача 4
 * Създайте програма, която да извежда съдържанието на масива
 * {'s', 'i', 't', ' ', 't', 'u', '-', 'v', 'A', 'R', 'N', 'A'},
 * така че всички големи букви да се изобразят като малки, а всички малки – като големи.
 */
public class Exercise04 {

  public static void main(String[] args) {
    char[] array = {'s', 'i', 't', ' ', 't', 'u', '-', 'v', 'A', 'R', 'N', 'A'};
    for (char c : array) {
      if (c == ' ') {
        System.out.print(c);
        continue;
      }
      if (Character.isUpperCase(c)) {
        System.out.print(Character.toLowerCase(c));
      }
      if (Character.isLowerCase(c)) {
        System.out.print(Character.toUpperCase(c));
      }
    }
  }
}
