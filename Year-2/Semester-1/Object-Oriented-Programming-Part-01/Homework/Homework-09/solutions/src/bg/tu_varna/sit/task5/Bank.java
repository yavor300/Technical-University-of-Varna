package bg.tu_varna.sit.task5;

import bg.tu_varna.sit.task4.Property;

import java.util.Collection;
import java.util.HashSet;

public class Bank {

  private final Collection<Account> accounts;

  public Bank() {
    this.accounts = new HashSet<>();
  }

  public boolean createAccount(Account account) {

    return accounts.add(account);
  }

  public boolean closeAccount(Account account) {

    return accounts.remove(account);
  }

  public int calculateNumberOfSalaryAccounts() {

    int result = 0;
    for (Account account : accounts) {
      if (account instanceof SalaryAccount) {
        result++;
      }
    }
    return result;
  }

  public double calculateAverageInterestByCurrency(Currency currency) {

    double result = 0;
    int count = 0;
    for (Account account : accounts) {
      if (account.getCurrency() == currency) {
        result += account.calculateInterestValue();
        count++;
      }
    }

    if (count == 0) {
      return 0;
    }

    return result / count;
  }

  public double findHighestInterest() {

    double max = 0;
    for (Account account : accounts) {
      if (account.calculateInterestValue() > max) {
        max = account.calculateInterestValue();
      }
    }

    return max;
  }

  @Override
  public String toString() {

    StringBuilder result = new StringBuilder();

    for (Account account : accounts) {
      result.append(account).append("\n");
    }

    return result.toString();
  }

  public Collection<Account> getAccounts() {
    return accounts;
  }
}
