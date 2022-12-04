package bg.tu_varna.sit.task3;

/**
 * Задача 3
 * Подберете подходящи модификатори за достъп в задачата
 * и поставете сорс файловете в пакет bg.tu_varna.sit.task3.
 * <p>
 * Да се състави програма за книжен каталог.
 * За целта са необходими:
 * <p>
 * - Изключение за невалидни данни (InvalidDataException);
 * <p>
 * - Енумерация специалност (Speciality) със стойности СИТ (SIT),
 * КСТ (CST), КТ (CCT) и А (A).
 * <p>
 * - Клас човек (Person) с атрибути за име (firstName), фамилия (lastName)
 * и възраст (age). Дефинирайте конструктор по всички полета,
 * методи за достъп, текстово описание и равенство (по всички полета);
 * <p>
 * - Клас студент (Book), имплементиращ интерфейс Comparable, който има разширява
 * клас човек с атрибути факултетен номер (fNumber), специалност (speciality),
 * курс (course) и успех (grades). Дефинирайте методи за достъп, методи за модификация
 * с валидиране на курса и успеха, метод за равенство по факултетен номер и
 * метод за текстово описание. Интерфейсният метод е също по факултетен номер;
 * <p>
 * - Клас факултет (Faculty), който има като атрибути име
 * на факултета (facultyName) и колекция от студенти (students).
 * <p>
 * Дефинирайте конструктор по име и метод за достъп до името.
 * Методи:
 * -- за добавяне на студент (addStudent);
 * -- за изчисляване и връщане средния успех на студентите във факултета (calculateAverageGrades);
 * -- за намиране и връщане броя студенти в дадена специалност (getNumberOfStudentsBySpeciality);
 * -- за намиране и връщане на студентите с отличен успех от факултета (getStudentsWithExcellentGrades);
 * -- за намиране и връщане броя студенти в зададен курс (getNumberOfStudentsByCourse);
 * -- за текстово описание.
 * <p>
 * Дефинирайте клас Application с главна функция и тествайте описаните функционалности.
 */
public class Application {

  public static void main(String[] args) {

    try {

      Student student = new Student("first", "last", 20, "abc", Speciality.SIT, 2, 6.00);
      Student student2 = new Student("first", "last", 20, "abc", Speciality.SIT, 2, 5.00);
      Faculty faculty = new Faculty("cs");
      faculty.addStudent(student);
      faculty.addStudent(student2);
      System.out.println(faculty.calculateAverageGrades());
      System.out.println(faculty.getNumberOfStudentsByCourse(2));
      System.out.println(faculty.getNumberOfStudentsBySpeciality(Speciality.SIT));
      for (Student studentt : faculty.getStudentsWithExcellentGrades()) {
        System.out.println(studentt);
      }
    } catch (InvalidDataException e) {
      System.out.println(e.getMessage());
    }
  }
}
