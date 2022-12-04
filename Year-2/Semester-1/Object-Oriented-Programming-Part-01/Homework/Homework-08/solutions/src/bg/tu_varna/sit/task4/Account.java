package bg.tu_varna.sit.task4;

public abstract class Account implements InterestCalculator {

  private final AccountHolder accountHolder;
  private final Currency currency;
  private final double balance;

  protected Account(AccountHolder accountHolder, Currency currency, double balance) {
    this.accountHolder = accountHolder;
    this.currency = currency;
    this.balance = balance;
  }

  protected AccountHolder getAccountHolder() {
    return accountHolder;
  }

  protected Currency getCurrency() {
    return currency;
  }

  protected double getBalance() {
    return balance;
  }
}
