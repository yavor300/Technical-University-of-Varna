package bg.tu_varna.sit.task5;

/**
 * Задача 5
 * <p>
 * Подберете подходящи модификатори за достъп в задачата
 * и поставете сорс файловете в пакет bg.tu_varna.sit.task5.
 * <p>
 * Да се състави програма за банка.
 * За целта са необходими:
 * <p>
 * - Интерфейс лихвен калкулатор (InterestCalculator) с метод, който изчислява
 * стойността на лихвата по дадена сметка (calculateInterestValue);
 * <p>
 * - Енумерация за валута (Currency) със стойности лев, долар, евро и паунд;
 * <p>
 * - Енумерация за тип на сметката (AccountType) със стойности
 * лична (PERSONAL) и корпоративна (CORPORATE);
 * <p>
 * - Изключение за невалидни данни (InvalidDataException);
 * <p>
 * - Абстрактен клас сметка (Account) с атрибути за номер (id), тип (type),
 * валута (currency) и наличност (balance), който имплементира интерфейс лихвен калкулатор.
 * Дефинирайте конструктор по всички полета, методи за достъп и метод за равенство (по номер);
 * <p>
 * - Клас депозитна сметка (DepositAccount), който разширява сметката.
 * Дефинирайте конструктор по всички полета и метод за текстово описание.
 * Лихвата се изчислява като:
 * -- 9% от наличността при лична сметка;
 * -- 7% от наличността при сметка в лева;
 * -- 5% от наличността при сметка в долари;
 * -- 2% от наличността при сметка в евро;
 * -- 1% от наличността в останалите случаи;
 * <p>
 * - Клас сметка за заплата (SalaryAccount), който разширява сметката.
 * Дефинирайте конструктор по всички полета и метод за текстово описание.
 * Лихвата се изчислява като:
 * -- 0 за корпоративна сметка;
 * -- 5% от наличността за сметка в лева;
 * -- 3% от наличността за сметка в долари;
 * -- 2% от наличността за сметка в евро;
 * -- 1% от наличността в останалите случаи;
 * <p>
 * - Клас спестовна сметка (SavingsAccount), който разширява сметката.
 * Дефинирайте конструктор по всички полета и метод за текстово описание.
 * Лихвата се изчислява като:
 * -- 15% от наличността за лична сметка в лева;
 * -- 5% от наличността за сметка в лева;
 * -- 10% от наличността за лична сметка в долари;
 * -- 1% от наличността в останалите случаи;
 * <p>
 * - Клас банка (RealEstateAgency), който има като атрибут колекция от сметки.
 * Методи:
 * -- за създаване на сметка (createAccount), който
 * добавя сметката към колекцията ако тя не съществува;
 * <p>
 * -- за затваряне на сметка (closeAccount);
 * -- за намиране и връщане на броя сметки за заплата,
 * открити в банката (calculateNumberOfSalaryAccounts);
 * <p>
 * -- за намиране и връщане на средната лихва за сметки в
 * дадена валута (calculateAverageInterestByCurrency);
 * <p>
 * -- за намиране и връщане на най-високата лихва
 * (findHighestInterest) с отчитане на валутния курс;
 * <p>
 * -- за текстово описание.
 * <p>
 * Дефинирайте клас Application с главна функция и тествайте описаните функционалности.
 */
public class Application {

  public static void main(String[] args) {

    Account deposit = new DepositAccount("01", AccountType.PERSONAL, Currency.LEV, 90320);
    Account salary = new SalaryAccount("02", AccountType.PERSONAL, Currency.DOLLAR, 190100);
    Account savings = new SavingsAccount("03", AccountType.CORPORATE, Currency.LEV, 290400);

    Account duplicate = new SavingsAccount("03", AccountType.CORPORATE, Currency.EURO, 290400);

    Bank bank = new Bank();

    bank.createAccount(deposit);
    bank.createAccount(salary);
    bank.createAccount(savings);
    bank.createAccount(duplicate);
    System.out.println(bank);

    bank.closeAccount(deposit);
    System.out.println(bank);

    System.out.println(bank.calculateAverageInterestByCurrency(Currency.LEV));
    System.out.println(bank.calculateNumberOfSalaryAccounts());
    System.out.println(bank.findHighestInterest());
  }
}
