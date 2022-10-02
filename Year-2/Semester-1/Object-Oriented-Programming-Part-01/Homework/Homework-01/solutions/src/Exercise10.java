/**
 * Задача 10
 * Създайте програма, която да определя получената оценка на ученици от контролно по математика.
 * Верните отговори на теста са {'a','b','a','c','a','c','c','b','a','c'}.
 * Скалата за оценяване е следната:
 * • до 2 т. вкл. – слаб 2;
 * • 3-4 – среден 3;
 * • 5-6 – добър 4;
 * • 7-8 – мн. добър 5;
 * • 9-10 – отличен 6.
 * За тестването на програмата ползвайте следните отговори,
 * дадени от ученик: {'a','a','b','c','a','c','a','b','a','b'}.
 */
public class Exercise10 {

  public static void main(String[] args) {
    char[] correctAnswers = {'a','b','a','c','a','c','c','b','a','c'};
    char[] studentAnswers = {'a','a','b','c','a','c','a','b','a','b'};

    int points = 0;
    for (int i = 0; i < correctAnswers.length; i++) {
      if (correctAnswers[i] == studentAnswers[i]) {
        points++;
      }
    }

    if (points <= 2) {
      System.out.println("Слаб 2");
    } else if (points <= 4) {
      System.out.println("Среден 3");
    } else if (points <= 6) {
      System.out.println("Добър 4");
    } else if (points <= 8) {
      System.out.println("Мн. добър 5");
    } else {
      System.out.println("Отличен 6");
    }
  }
}
