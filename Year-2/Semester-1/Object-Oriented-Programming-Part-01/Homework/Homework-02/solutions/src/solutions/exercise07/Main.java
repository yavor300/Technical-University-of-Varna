package exercise07;

/**
 * Задача 7
 * <p>
 * Дефинирайте клас StudentGroup, който описва студентска група с атрибути
 * специалност, курс, група и брой студенти. Инициализирайте обектите с
 * параметризиран конструктор. Като поведение използвайте методи за четене,
 * текстово описание и равенство (по всички атрибути). Създайте масив от 10 обекта.
 * Намерете и изведете групите с най-много студенти по курсове.
 */
public class Main {

  private static final Integer STUDENTS_GROUP_COUNT = 10;
  private static final Integer MIN_COURSE_INCLUSIVE = 1;
  private static final Integer MAX_COURSE_EXCLUSIVE = 5;

  public static void main(String[] args) {

    StudentGroup[] studentGroups = initializeStudentGroupsData(STUDENTS_GROUP_COUNT);
    System.out.println("Groups info:\n");
    for (StudentGroup group : studentGroups) {
      System.out.println(group + "\n");
    }

    for (int i = MIN_COURSE_INCLUSIVE; i < MAX_COURSE_EXCLUSIVE; i++) {
      System.out.printf("Group with most students in course %d:%n%s%n%n",
              i, getGroupWithMostStudentsInCourse(i, studentGroups));
    }
  }

  private static StudentGroup[] initializeStudentGroupsData(int dataCounter) {

    String[] specialities = {"SIT", "KST", "AI"};
    StudentGroup[] studentGroups = new StudentGroup[dataCounter];

    for (int i = 0; i < dataCounter; i++) {
      studentGroups[i] = new StudentGroup(
              specialities[getRandomIntegerNumber(0, specialities.length)],
              getRandomIntegerNumber(MIN_COURSE_INCLUSIVE, MAX_COURSE_EXCLUSIVE),
              getRandomIntegerNumber(1, 5),
              getRandomIntegerNumber(20, 30)
      );

      for (int j = 0; j < i; j++) {
        if (studentGroups[i].equals(studentGroups[j])) {
          i--;
          break;
        }
      }
    }

    return studentGroups;
  }

  private static StudentGroup getGroupWithMostStudentsInCourse(int course, StudentGroup[] studentGroups) {

    StudentGroup result = new StudentGroup();
    result.setStudentsCount(0);

    for (StudentGroup studentGroup : studentGroups) {
      if (studentGroup.getCourse() == course &&
              studentGroup.getStudentsCount() > result.getStudentsCount()) {
        result = studentGroup;
      }
    }

    return result;
  }

  private static int getRandomIntegerNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }
}
