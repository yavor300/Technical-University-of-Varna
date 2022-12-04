package bg.tu_varna.sit.task4;

public class DepositAccount extends Account {

  public DepositAccount(AccountHolder accountHolder, Currency currency, double balance) {
    super(accountHolder, currency, balance);
  }

  @Override
  public double calculateAccountInterest() {

    if (getCurrency() == Currency.BGN) {
      return 0.05 * getBalance();
    } else if (getCurrency() == Currency.USD) {
      return 0.02 * getBalance();
    } else {
      return 0.01 * getBalance();
    }
  }
}
