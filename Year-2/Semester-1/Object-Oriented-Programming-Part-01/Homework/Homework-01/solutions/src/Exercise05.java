import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Задача 5
 * Лекарски кабинет работи през понеделник, сряда и петък от 13:30 до 18:00 часа,
 * а във вторник и четвъртък – от 8:00 до 12:30 часа. Събота и неделя са почивни дни.
 * Създайте програма, при която при подаден пореден ден от седмицата да изведе дали
 * е работен и в кой часови диапазон е работното време.
 */
public class Exercise05 {
  private static final String MONDAY_WEDNESDAY_FRIDAY_SCHEDULE = "13:30 - 18:00";
  private static final String TUESDAY_THURSDAY_SCHEDULE = "08:00 - 12:30";

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Map<String, String> schedule = new HashMap<>();

    schedule.put("понеделник", MONDAY_WEDNESDAY_FRIDAY_SCHEDULE);
    schedule.put("сряда", MONDAY_WEDNESDAY_FRIDAY_SCHEDULE);
    schedule.put("петък", MONDAY_WEDNESDAY_FRIDAY_SCHEDULE);
    schedule.put("вторник", TUESDAY_THURSDAY_SCHEDULE);
    schedule.put("четвъртък", TUESDAY_THURSDAY_SCHEDULE);
    schedule.put("събота", null);
    schedule.put("неделя", null);

    String day = scanner.nextLine().toLowerCase();
    if (!schedule.containsKey(day)) {
      System.out.println("Въведен е невалиден ден от седмицата!");
      return;
    }

    String workingTime = schedule.get(day);
    if (workingTime == null) {
      System.out.println("Събота и неделя са почивни дни.");
    } else {
      System.out.printf("Работното време за %s е %s.", day, schedule.get(day));
    }
  }
}
