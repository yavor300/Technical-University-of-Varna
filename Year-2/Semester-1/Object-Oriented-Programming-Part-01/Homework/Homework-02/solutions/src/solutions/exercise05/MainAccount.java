package exercise05;

import java.util.Scanner;

/**
 * Задача 5
 * <p>
 * Дефинирайте клас Account, който описва банкова сметка с атрибути номер,
 * валута, наличност и тип на сметката. Всички атрибути са символни низове.
 * Инициализирайте обектите с параметризиран конструктор.
 * Като поведение използвайте методи за четене, текстово описание и равенство
 * (по валута и наличност). Създайте масив от 10 обекта.
 * Намерете и изведете средните наличности по тип валута.
 */
public class MainAccount {

  private static final int ACCOUNTS_COUNT = 10;

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    Account[] accounts = initializeAccountsData(ACCOUNTS_COUNT);
    System.out.println("Accounts info:\n");
    for (Account account : accounts) {
      System.out.println(account + "\n");
    }

    System.out.print("Get average balance for currency: ");
    String currency = scanner.next();
    System.out.printf("Average balance for %s - %.2f", currency.toUpperCase(),
            getAverageBalanceForCurrency(currency, accounts));
  }

  private static Account[] initializeAccountsData(int dataCounter) {

    String[] currencies = {"BGN", "USD", "EUR"};
    String[] types = {"SAVINGS", "CARD"};

    Account[] accounts = new Account[dataCounter];
    for (int i = 0; i < dataCounter; i++) {
      accounts[i] = new Account(
              String.valueOf(getRandomIntegerNumber(1000, 10000)),
              currencies[getRandomIntegerNumber(0, currencies.length)],
              String.valueOf(getRandomDoubleNumber(10000, 17.800)),
              types[getRandomIntegerNumber(0, types.length)]
      );

      for (int j = 0; j < i; j++) {
        if (accounts[i].equals(accounts[j])) {
          i--;
          break;
        }
      }
    }

    return accounts;
  }

  private static double getAverageBalanceForCurrency(String currency, Account[] accounts) {

    int counter = 0;
    double totalBalance = 0;

    for (Account account : accounts) {
      if (account.getCurrency().equalsIgnoreCase(currency)) {
        counter++;
        totalBalance += Double.parseDouble(account.getBalance());
      }
    }

    if (counter == 0) {
      return 0;
    }

    return totalBalance / counter;
  }

  private static int getRandomIntegerNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }

  private static double getRandomDoubleNumber(double min, double max) {
    return ((Math.random() * (max - min)) + min);
  }
}
