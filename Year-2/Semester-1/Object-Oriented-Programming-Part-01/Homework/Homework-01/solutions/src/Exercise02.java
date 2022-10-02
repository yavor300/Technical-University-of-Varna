/**
 * Задача 2
 * Създайте програма, която да извежда съдържанието на масива
 * {'S', 'o', 'm', 'e', ' ', 't', 'e', 'x', 't'}, така че всеки символ да е на отделен ред.
 * Нека решението да бъде изпълнено с помощта на while или do-while цикъл.
 */
public class Exercise02 {

  public static void main(String[] args) {
    char[] array = {'S', 'o', 'm', 'e', ' ', 't', 'e', 'x', 't'};
    int index = 0;
    while (index < array.length) {
      System.out.println(array[index]);
      index++;
    }
  }
}
