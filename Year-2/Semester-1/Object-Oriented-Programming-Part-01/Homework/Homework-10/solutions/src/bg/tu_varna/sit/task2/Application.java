package bg.tu_varna.sit.task2;

import java.util.Map;

/**
 * Задача 2
 * <p>
 * Подберете подходящи модификатори за достъп в задачата и поставете сорс файловете в пакет
 * bg.tu_varna.sit.task2. Да се състави програма за банка. За целта са необходими:
 * <p>
 * • Интерфейс лихвен калкулатор (InterestCalculator) с метод, който изчислява стойността на
 * лихвата по дадена сметка (calculateInterestValue); • Енумерация за валута (Currency)
 * със стойности лев, долар, евро и паунд; • Енумерация за тип на сметката (AccountType)
 * със стойности лична (PERSONAL) и корпоративна (CORPORATE);
 * <p>
 * • Изключение за невалидни данни
 * (InvalidDataException); • Абстрактен клас сметка (Account) с атрибути за номер (id), тип (type),
 * валута (currency) и наличност (balance), който имплементира интерфейс лихвен калкулатор.
 * Дефинирайте конструктор по всички полета, методи за достъп и метод за равенство (по номер);
 * <p>
 * • Клас депозитна сметка (DepositAccount), който разширява сметката.
 * Дефинирайте конструктор по всички полета и метод за текстово описание.
 * Лихвата се изчислява като: o 9% от наличността при лична сметка; o 7% от наличността при сметка в лева;
 * o 5% от наличността при сметка в долари;
 * o 2% от наличността при сметка в евро;
 * o 1% от наличността в останалите случаи;
 * <p>
 * • Клас сметка за заплата (SalaryAccount), който разширява сметката.
 * Дефинирайте конструктор по всички полета и метод за текстово описание.
 * Лихвата се изчислява като: o 0 за корпоративна сметка;
 * o 5% от наличността за сметка в лева;
 * o 3% от наличността за сметка в долари;
 * o 2% от наличността за сметка в евро;
 * o 1% от наличността в останалите случаи;
 * <p>
 * • Клас спестовна сметка (SavingsAccount), който разширява сметката.
 * Дефинирайте конструктор по всички полета и метод за текстово описание.
 * Лихвата се изчислява като:
 * o 15% от наличността за лична сметка в лева;
 * o 5% от наличността за сметка в лева;
 * o 10% от наличността за лична сметка в долари;
 * o 1% от наличността в останалите случаи;
 * <p>
 * • Клас банка (Bank), който има като атрибут асоциация <Сметка, Лихва> (bankAccounts).
 * Методи:
 * o за добавяне на сметка (addAccount), който добавя сметката към колекцията ако тя не съществува;
 * o за сортиране на сметките по наличност (sortAccountsByBalance);
 * o за сортиране на сметките по лихва (sortAccountsByInterest);
 * o за намиране и връщане валутата с най-много сметки (findCurrencyWithMostAccounts);
 * o за текстово описание.
 * <p>
 * Дефинирайте клас Application с главна функция и тествайте описаните функционалности.
 */
public class Application {

  public static void main(String[] args) {

    try {
      
      Account salary = new SalaryAccount("1", AccountType.PERSONAL, Currency.LEV, 100);
      Account deposit = new DepositAccount("2", AccountType.CORPORATE, Currency.LEV, 1000);
      Account savings = new SavingsAccount("3", AccountType.PERSONAL, Currency.EURO, 5000);

      Bank bank = new Bank();
      bank.addAccount(salary);
      bank.addAccount(deposit);
      bank.addAccount(savings);

      for (Map.Entry<Account, Double> entry : bank.sortAccountsByBalance().entrySet()) {
        System.out.println(entry.getKey());
      }

      System.out.println();

      for (Map.Entry<Account, Double> entry : bank.sortAccountsByInterest().entrySet()) {
        System.out.println(entry.getKey());
      }

      System.out.println();

      System.out.println(bank.findCurrencyWithMostAccounts());

      System.out.println(bank);
    } catch (InvalidDataException e) {
      e.printStackTrace();
    }
  }
}
