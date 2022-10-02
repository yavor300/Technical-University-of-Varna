import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Задача 7
 * Предвидете масив, в който да се включват факултетните номера (7-цифрено число)
 * на всички 10 студенти, заявили участие в университетска олимпиада по програмиране.
 * Разпишете алгоритъм, който да изброи и изведе колко от студентите в кой курс се обучават.
 * Приемаме, че ФН на първокурсниците започват с 22, втори курс – 21, трети – 20 и четвърти - 19.
 * Ако ФН започва с други цифри, да се добави към група „неидентифицирани“.
 */
public class Exercise07 {

  private static final int STUDENTS_COUNT = 1;
  private static final String FIRST_COURSE = "първи";
  private static final String SECOND_COURSE = "втори";
  private static final String THIRD_COURSE = "трети";
  private static final String FOURTH_COURSE = "четвърти";
  private static final String UNDEFINED_COURSE = "неидентифицирани";

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String[] studentIDs = new String[STUDENTS_COUNT];

    Map<String, String> courses = new HashMap<>();
    courses.put("22", FIRST_COURSE);
    courses.put("21", SECOND_COURSE);
    courses.put("20", THIRD_COURSE);
    courses.put("19", FOURTH_COURSE);

    for (int i = 0; i < STUDENTS_COUNT; i++) {
      String studentId;
      do {
        System.out.print("Въведете студентски номер: ");
        studentId = scanner.nextLine();
      } while (!isIdValid(studentId));
      studentIDs[i] = studentId;
    }

    Map<String, Integer> groups = new HashMap<>();
    groups.put(FIRST_COURSE, 0);
    groups.put(SECOND_COURSE, 0);
    groups.put(THIRD_COURSE, 0);
    groups.put(FOURTH_COURSE, 0);
    groups.put(UNDEFINED_COURSE, 0);

    for (String id : studentIDs) {
      String courseId = id.substring(0, 2);
      String courseName = courses.get(courseId);
      if (courseName == null) {
        groups.put(UNDEFINED_COURSE, groups.get(UNDEFINED_COURSE) + 1);
      } else {
        groups.put(courseName, groups.get(courseName) + 1);
      }
    }

    for (Map.Entry<String, Integer> entry : groups.entrySet()) {
      System.out.printf("%s курс - %d%n", entry.getKey(), entry.getValue());
    }

  }

  private static boolean isIdValid(String id) {
    if (id.length() != 7) {
      return false;
    }

    for (char c : id.toCharArray()) {
      if (!Character.isDigit(c))
      return false;
    }

    return true;
  }
}
