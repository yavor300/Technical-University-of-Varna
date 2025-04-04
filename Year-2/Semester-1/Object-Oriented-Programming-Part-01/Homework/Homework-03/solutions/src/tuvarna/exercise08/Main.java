package tuvarna.exercise08;

/**
 * Задача 8
 * <p>
 * Да се реализира клас DrivingLicense (шофьорска книжка) с
 * номер на документа и валидност в месеци.
 * <p>
 * Да се реализира клас Human, който описва човек с три имена (отделни полета),
 * пол и възраст. Класа Human се наследява от клас Employee с име, брой часове,
 * които служителят работи на ден и заплата на час. Да се реализира метод basicSalary(),
 * който връща сумата, която служителя получава за един ден.
 * Класа Employee се наследява от клас Driver, който разширява класа с шофьорска
 * книжка и брой на курсовете за превозване на пътници, които извършва за един ден.
 * <p>
 * Да се реализира метод заплата driverSalary(), която се определя като към резултата от
 * basicSalary() (заплатата му за един ден) се прибавят още 5%, ако за деня шофьорът е извършил
 * повече то 10 курса за превозване на пътници.
 * Клас Employee се наследява от клас Engineer и се определя от брой на машините, които е създал
 * за един ден. Метод еngineerSalary се определя като към резултата от basicSalary()
 * (заплатата му за един ден) се прибавят още по 0.1% от заплатата на час за всяка създадена машина за деня.
 * <p>
 * Подберете подходящи модификатори за достъп при всеки клас.
 */
public class Main {

  public static void main(String[] args) {

    DrivingLicense drivingLicense = new DrivingLicense("0000", 12);
    Driver driver = new Driver(
            "Alex", "Nicolas", "Peterson",
            'M', 25, 8,
            20.00, drivingLicense, 11);
    System.out.printf("%s%n%n", driver);

    Engineer engineer = new Engineer(
            "Mark", "Rob", "Tyson",
            'M', 45, 8, 25.00, 4);
    System.out.println(engineer);
  }
}
