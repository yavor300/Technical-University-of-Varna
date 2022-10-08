package exercise04;

import java.util.Scanner;

/**
 * Задача 4
 * <p>
 * Дефинирайте клас Student с атрибути име, фамилия, факултетен номер и специалност.
 * Като поведение използвайте методи за четене/запис, текстово описание и равенство
 * (по факултетен номер).Създайте масив от 10 обекта.
 * Намерете и изведете броя студенти със зададена фамилия.
 */
public class Main {

  private static final Integer STUDENTS_COUNT = 10;

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    Student[] students = initializeStudentsData(STUDENTS_COUNT);
    for (Student student : students) {
      System.out.println(student);
    }

    System.out.print("Get count for students with last name: ");
    String lastName = scanner.next();
    System.out.printf("Count: %d", getCountForStudentsWithLastName(lastName, students));
  }

  private static Student[] initializeStudentsData(int dataCounter) {

    String[] firstNames = {"Georgi", "Milen", "Marian", "Ivan", "Petar", "Mitko"};
    String[] lastNames = {"Petkov", "Georgiev", "Iliev"};
    Student[] students = new Student[dataCounter];

    for (int i = 0; i < dataCounter; i++) {
      students[i] = new Student(
              firstNames[getRandomIntegerNumber(0, firstNames.length)],
              lastNames[getRandomIntegerNumber(0, lastNames.length)],
              "2162" + getRandomIntegerNumber(1000, 10000),
              "SIT"
      );

      for (int j = 0; j < i; j++) {
        if (students[i].equals(students[j])) {
          i--;
          break;
        }
      }
    }

    return students;
  }

  private static int getRandomIntegerNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }

  private static int getCountForStudentsWithLastName(String lastName, Student[] students) {

    int result = 0;

    for (Student student : students) {
      if (student.getLastName().equalsIgnoreCase(lastName)) {
        result++;
      }
    }

    return result;
  }
}
