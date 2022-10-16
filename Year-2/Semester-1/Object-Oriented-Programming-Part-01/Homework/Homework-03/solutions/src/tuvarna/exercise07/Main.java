package tuvarna.exercise07;

/**
 * Задача 7
 * <p>
 * Да се реализира клас Билет, който поддържа информация за
 * името на представлението и цена. Промяната на цената може
 * става само от наследниците
 * Билета се надгражда от стандартен билет, важащ за
 * един зрител, допълвайки билета с поле за използван
 * или неизползван.
 * Стандартният билет се разширява от билет с намалени
 * за студенти и пенсионери, чиято цена е 50% от ценат
 * на стандартния билет. Той притежава като свойство името
 * на ползвателя. При създаването на билет с намаление,
 * подадената цена се намалява на 50% и така се подава като нова цена.
 * Съществува и Групов билет, който важи максимум за 20
 * човека и цената му се намалява с 2лв за всеки човек.
 * При създаването на обект от клас групов билет, подадената
 * единична цена се умножава по 20 и се подава като нова цена.
 * Класът дефинира метод добавяне на нов участник в групата, който
 * намалява цената на билета с 2лв, ако не е достигнат лимита от 20
 * човека, ако е достигнат този лимит нищо не се променя.
 * Всяко свойство на класовете трябва да може да се чете.
 * Подберете подходящи модификатори за достъп при всеки клас.
 */
public class Main {

  public static void main(String[] args) {

    StandardTicket standardTicket = new StandardTicket("Shrek", 25.00, false);
    System.out.println(standardTicket);

    SeniorCitizenTicket seniorCitizenTicket = new SeniorCitizenTicket("Aladin", 24.00, true, "Nick");
    System.out.printf("%n%s", seniorCitizenTicket);

    StudentTicket studentTicket = new StudentTicket("Aladin", 24.00, true, "Nick");
    System.out.printf("%n%n%s", studentTicket);

    GroupTicket groupTicket = new GroupTicket("Cars", 4.00);
    for (int i = 0; i < 18; i++) {
      groupTicket.addViewer();
    }
    System.out.printf("%n%n%s", groupTicket);
  }
}
