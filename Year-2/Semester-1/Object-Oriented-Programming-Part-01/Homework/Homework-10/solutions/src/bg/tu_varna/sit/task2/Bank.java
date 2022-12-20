package bg.tu_varna.sit.task2;

import bg.tu_varna.sit.task1.Item;

import java.util.*;

public class Bank {

  private final Map<Account, Double> bankAccounts;

  public Bank() {
    this.bankAccounts = new HashMap<>();
  }

  public boolean addAccount(Account account) {

    if (bankAccounts.get(account) == null) {
      bankAccounts.put(account, account.calculateInterestValue());
      return bankAccounts.get(account) != null;
    }

    return false;
  }

  public SortedMap<Account, Double> sortAccountsByBalance() {

    SortedMap<Account, Double> result = new TreeMap<>(Comparator.comparingDouble(Account::getBalance));
    result.putAll(bankAccounts);

    return result;
  }

  public SortedMap<Account, Double> sortAccountsByInterest() {

    SortedMap<Account, Double> result = new TreeMap<>((Comparator.comparingDouble(InterestCalculator::calculateInterestValue)));
    result.putAll(bankAccounts);

    return result;
  }

  public Currency findCurrencyWithMostAccounts() {

    Map<Currency, Integer> result = new HashMap<>();

    for (Map.Entry<Account, Double> entry : bankAccounts.entrySet()) {
      result.merge(entry.getKey().getCurrency(), 1, Integer::sum);
    }

    Map.Entry<Currency, Integer> maxEntry = null;

    for (Map.Entry<Currency, Integer> entry : result.entrySet())
    {
      if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
      {
        maxEntry = entry;
      }
    }

    return maxEntry.getKey();
  }

  @Override
  public String toString() {

    StringBuilder stringBuilder = new StringBuilder();

    for (Map.Entry<Account, Double> entry : bankAccounts.entrySet()) {
      stringBuilder.append(entry.getKey()).append("\n");
    }

    return stringBuilder.toString();
  }
}
