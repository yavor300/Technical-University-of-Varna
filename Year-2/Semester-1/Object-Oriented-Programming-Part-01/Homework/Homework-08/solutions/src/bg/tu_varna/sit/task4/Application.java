package bg.tu_varna.sit.task4;

/**
 * Задача 4
 * <p>
 * Подберете подходящи модификатори за достъп в задачата
 * и поставете сорс файловете в пакет bg.tu_varna.sit.task4.
 * Да се състави програма за изчисляване на лихви върху банкови сметки.
 * За целта са необходими:
 * - Интерфейс лихвен калкулатор (InterestCalculator) с
 * метод за изчисляване на лихвата (calculateAccountInterest);
 * - Енумерация за валута (Currency) със стойности лев (BGN),
 * долар (USD) и евро (EUR);
 * - Клас клиент (AccountHolder) с атрибути име (firstName),
 * фамилия (lastName) и възраст (age). Дефинирайте конструктор
 * по всички полета и методи за достъп;
 * - Абстрактен клас сметка (Account), имплементиращ интерфейса
 * лихвен калкулатор. Класът има атрибути за клиент (accountHolder),
 * валута (currency) и наличност (balance). Дефинирайте конструктор
 * по всички полета и методи за достъп;
 * - Клас депозитна сметка (DepositAccount), който наследява клас сметка.
 * Интерфейсният метод връща 5% от наличността, ако сметката е в лева,
 * 2% от наличността, ако сметката е в долари и 1% в останалите случаи;
 * - Клас спестовна сметка (SavingsAccount), който наследява клас сметка.
 * Интерфейсният метод връща 8% от наличността, ако клиента е с възраст
 * над 62 години и сметката е в лева; 5% от наличността, ако сметката
 * е в лева, 2.5% от наличността, ако сметката е в долари и 0.5% в останалите случаи;
 * - Клас сметка за заплата (SalaryAccount), който наследява клас сметка.
 * Интерфейсният метод връща 8% от наличността, ако възрастта на клиента
 * е между 25 и 62 гоини, и 4% в останалите случаи.
 * Създайте генерик клас за лихва (Interest). Дефинирайте в него генерик
 * метод за извеждане на лихвата за дадената сметка (displayAccountInterest).
 * Дефинирайте клас Application с главна функция и тествайте описаните функционалности.
 */
public class Application {

  public static void main(String[] args) {

    AccountHolder accountHolder = new AccountHolder("firstName", "lastName", 22);

    DepositAccount depositAccount = new DepositAccount(accountHolder, Currency.BGN, 10000);
    SalaryAccount salaryAccount = new SalaryAccount(accountHolder, Currency.USD, 20000);
    SavingsAccount savingsAccount = new SavingsAccount(accountHolder, Currency.EUR, 30000);

    Interest<DepositAccount> depositAccountInterest = new Interest<>(depositAccount);
    Interest<SalaryAccount> salaryAccountInterest = new Interest<>(salaryAccount);
    Interest<SavingsAccount> savingsAccountInterest = new Interest<>(savingsAccount);

    depositAccountInterest.displayAccountInterest();
    salaryAccountInterest.displayAccountInterest();
    savingsAccountInterest.displayAccountInterest();
  }
}
